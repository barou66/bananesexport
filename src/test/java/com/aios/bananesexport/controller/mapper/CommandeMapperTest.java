package com.aios.bananesexport.controller.mapper;

import com.aios.bananesexport.controller.dto.*;
import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.repository.model.Destinataire;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandeMapperTest {

    @Test
    void inputToModel() {
        assertNull(CommandeMapper.inputToModel(null));
        Commande commande = CommandeMapper.inputToModel(CommandeInput.builder()
                .destinataireId("D1")
                .dateDeLivraison(LocalDate.now())
                .quantite(10)
                .build());
        assertNotNull(commande);
        assertEquals("D1",commande.getDestinataire().getId());
        assertEquals(10,commande.getQuantite());
        assertEquals(0.0,commande.getPrix());
        assertNotNull(commande.getDateDeLivraison());
    }

    @Test
    void modelToDto() {
        assertNull(CommandeMapper.modelToDto(null));
        CommandeDto commande = CommandeMapper.modelToDto(getCommande());
        assertNotNull(commande);
        assertEquals("C1",commande.getId());
        assertEquals(750,commande.getPrix());
        assertEquals(300,commande.getQuantite());
        assertNotNull(commande.getDateDeLivraison());
    }

    @Test
    void commandesToResponse() {
        CommandeResponse commandeResponse = CommandeMapper.commandesToResponse(Collections.emptyList());
        assertNotNull(commandeResponse);
        assertTrue(CollectionUtils.isEmpty(commandeResponse.getCommandes()));
        commandeResponse = CommandeMapper.commandesToResponse(Collections.singletonList(getCommande()));
        assertNotNull(commandeResponse);
        List<CommandeDto> commandes = commandeResponse.getCommandes();
        assertEquals(1,commandes.size());
        CommandeDto commande = commandes.get(0);
        assertNotNull(commande);
        assertEquals("C1",commande.getId());
        assertEquals(750,commande.getPrix());
        assertEquals(300,commande.getQuantite());
        assertNotNull(commande.getDateDeLivraison());
    }
    private Commande getCommande(){
        return Commande.builder()
                .id("C1")
                .prix(750)
                .quantite(300)
                .dateDeLivraison(LocalDate.now())
                .destinataire(getDestinataire())
                .build();
    }
    private Destinataire getDestinataire(){
        return Destinataire.builder()
                .id("D1")
                .nom("Dupont")
                .adresse(getAdresse())
                .build();
    }

    private Adresse getAdresse(){
        return Adresse.builder()
                .adresse("71 rue de la boétie 75008")
                .codePostal(75008)
                .ville("Paris")
                .pays("France")
                .build();
    }
    private AdresseDto getAdresseDto(){
        return AdresseDto.builder()
                .adresse("71 rue de la boétie 75008")
                .codePostal(75008)
                .ville("Paris")
                .pays("France")
                .build();
    }
}