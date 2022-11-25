package com.aios.bananesexport.controller.v1;

import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Destinataire;
import com.aios.bananesexport.service.DestinataireService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DestinataireController.class)
@ActiveProfiles("test")
class DestinataireControllerTest {

    private static final String URL = "/api/v1/destinataires";
    @MockBean
    private DestinataireService destinataireService;
    @Autowired
    private MockMvc mvc;

    @Test
    void createDestinataire() throws Exception {
        when(this.destinataireService.createDestinataire(any(Destinataire.class))).thenReturn(getDestinataire());
        JSONObject request = new JSONObject();
        request.put("nom", "Dupont");
        JSONObject adresse = new JSONObject();
        adresse.put("adresse", "71 rue de la boétie 75008");
        adresse.put("codePostal", "75008");
        adresse.put("ville", "Paris");
        adresse.put("pays", "France");
        request.put("adresse", adresse);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("D1"))
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.adresse.adresse").value("71 rue de la boétie 75008"));
    }

    @Test
    void updateDestinataire() throws Exception {
        when(this.destinataireService.updateDestinataire(anyString(), any(Destinataire.class))).thenReturn(
                getDestinataire());
        ArgumentCaptor<Destinataire> destinataireArgumentCaptor = ArgumentCaptor.forClass(Destinataire.class);
        JSONObject request = new JSONObject();
        request.put("nom", "Moretti");
        JSONObject adresse = new JSONObject();
        adresse.put("adresse", "71 rue lounes Matoub");
        adresse.put("codePostal", "75008");
        adresse.put("ville", "Paris");
        adresse.put("pays", "France");
        request.put("adresse", adresse);

        mvc.perform(put(URL + "/D1").contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("D1"));
        verify(this.destinataireService, times(1)).updateDestinataire(anyString(),
                destinataireArgumentCaptor.capture());
        Destinataire destinataire = destinataireArgumentCaptor.getValue();
        assertEquals("Moretti", destinataire.getNom());
        assertEquals("71 rue lounes Matoub", destinataire.getAdresse()
                .getAdresse());
    }

    @Test
    void deleteDestinataire() throws Exception {
        mvc.perform(delete(URL + "/D1"))
                .andExpect(status().isNoContent());
        verify(destinataireService, times(1)).deleteDestinataireById("D1");
    }

    @Test
    void getDestinataireById() throws Exception {
        when(this.destinataireService.findDestinataireById("D1")).thenReturn(getDestinataire());

        mvc.perform(get(URL + "/D1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("D1"))
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.adresse.adresse").value("71 rue de la boétie 75008"));
    }

    @Test
    void findDestinataires() throws Exception {
        when(this.destinataireService.findDestinataires()).thenReturn(Collections.singletonList(getDestinataire()));

        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destinataires.length()").value(1));
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