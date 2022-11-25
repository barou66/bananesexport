package com.aios.bananesexport.controller.v1;

import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.repository.model.Destinataire;
import com.aios.bananesexport.service.CommandeService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CommandeController.class)
@ActiveProfiles("test")
class CommandeControllerTest {
    private static final String URL = "/api/v1/commandes";
    @MockBean
    private CommandeService commandeService;
    @Autowired
    private MockMvc mvc;

    @Test
    void createCommande() throws Exception {
        when(this.commandeService.createCommande(any(Commande.class))).thenReturn(getCommande());
        JSONObject request = new JSONObject();
        request.put("destinataireId", "D1");
        request.put("dateDeLivraison", LocalDate.now());
        request.put("quantite", "300");

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("C1"))
                .andExpect(jsonPath("$.quantite").value(300))
                .andExpect(jsonPath("$.destinataire.nom").value("Dupont"));
    }

    @Test
    void updateCommande() throws Exception {
        when(this.commandeService.updateCommande(anyString(), any(Commande.class))).thenReturn(getCommande());
        ArgumentCaptor<Commande> commandeArgumentCaptor = ArgumentCaptor.forClass(Commande.class);
        JSONObject request = new JSONObject();
        request.put("destinataireId", "D2");
        request.put("dateDeLivraison", LocalDate.now());
        request.put("quantite", "400");

        mvc.perform(put(URL + "/C1").contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("C1"));
        verify(this.commandeService, times(1)).updateCommande(anyString(), commandeArgumentCaptor.capture());
        Commande commande = commandeArgumentCaptor.getValue();
        assertEquals(400, commande.getQuantite());
        assertEquals("D2", commande.getDestinataire()
                .getId());
    }

    @Test
    void deleteCommande() throws Exception {
        mvc.perform(delete(URL + "/C1"))
                .andExpect(status().isNoContent());

        verify(commandeService, times(1)).deleteCommandeById("C1");
    }

    @Test
    void getCommandeById() throws Exception {
        when(this.commandeService.findCommandeById("C1")).thenReturn(getCommande());

        mvc.perform(get(URL + "/C1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("C1"))
                .andExpect(jsonPath("$.quantite").value(300))
                .andExpect(jsonPath("$.destinataire.nom").value("Dupont"));
    }

    @Test
    void findCommandes() throws Exception {
        when(this.commandeService.findCommandes()).thenReturn(Collections.singletonList(getCommande()));

        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commandes.length()").value(1));
    }

    private Commande getCommande() {
        return Commande.builder()
                .id("C1")
                .prix(750)
                .quantite(300)
                .dateDeLivraison(LocalDate.now())
                .destinataire(getDestinataire())
                .build();
    }

    private Destinataire getDestinataire() {
        return Destinataire.builder()
                .id("D1")
                .nom("Dupont")
                .adresse(getAdresse())
                .build();
    }

    private Adresse getAdresse() {
        return Adresse.builder()
                .adresse("71 rue de la boétie 75008")
                .codePostal(75008)
                .ville("Paris")
                .pays("France")
                .build();
    }
}