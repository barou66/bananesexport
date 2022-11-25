package com.aios.bananesexport.service.impl;

import com.aios.bananesexport.exception.BusinessException;
import com.aios.bananesexport.exception.ResourceNotFoundException;
import com.aios.bananesexport.repository.InMemoryCommande;
import com.aios.bananesexport.repository.InMemoryDestinataire;
import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.repository.model.Destinataire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CommandeServiceImplTest {


    @MockBean
    private InMemoryCommande inMemoryCommande;
    @MockBean
    private InMemoryDestinataire inMemoryDestinataire;
    @Autowired
    private CommandeServiceImpl commandeService;

    @Test
    void createCommande() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        ArgumentCaptor<Commande> commandeArgumentCaptor = ArgumentCaptor.forClass(Commande.class);
        Commande input = getCommande();
        input.setQuantite(300);
        //Act 
        commandeService.createCommande(input);
        //Assert
        verify(inMemoryCommande,times(1)).createCommande(commandeArgumentCaptor.capture());
        Commande commande = commandeArgumentCaptor.getValue();
        assertNotNull(commande);
        assertNotNull(commande.getId());
        assertEquals(750.0, commande.getPrix());
        assertEquals(300,commande.getQuantite());
        assertTrue(LocalDate.now().isBefore(commande.getDateDeLivraison()));
        Destinataire destinataire = commande.getDestinataire();
        assertNotNull(destinataire);
        assertEquals("dupont", destinataire.getNom());
        Adresse adresse = destinataire.getAdresse();
        assertNotNull(adresse);
        assertEquals("7 rue av", adresse.getAdresse());
        assertEquals("Paris", adresse.getVille());
        assertEquals("France", adresse.getPays());
        assertEquals(75008, adresse.getCodePostal()
                .intValue());
    }

    @Test
    void createCommande_DateNotValid() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        Commande input = getCommande();
        input.setDateDeLivraison(LocalDate.now());
        //Act
        BusinessException businessException = assertThrows(BusinessException.class,
                () ->  commandeService.createCommande(input));
        //Assert
        assertEquals("livraison.date.not.valid", businessException.getMessage());
    }

    @Test
    void createCommande_QuantiteNotValid() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        Commande input = getCommande();
        input.setQuantite(-1);
        //Act
        BusinessException businessException = assertThrows(BusinessException.class,
                () ->  commandeService.createCommande(input));
        //Assert
        assertEquals("quantite.isnot.allow", businessException.getMessage());
        input.setQuantite(10001);
        businessException = assertThrows(BusinessException.class,
                () ->  commandeService.createCommande(input));
        assertEquals("quantite.isnot.allow", businessException.getMessage());
    }

    @Test
    void createCommande_QuantiteNotMultipleOf25() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        Commande input = getCommande();
        input.setQuantite(35);
        //Act
        BusinessException businessException = assertThrows(BusinessException.class,
                () ->  commandeService.createCommande(input));
        //Assert
        assertEquals("quantite.not25.multiple", businessException.getMessage());
    }

    @Test
    void createCommande_DestinataireNotFound() {
        //Arrange
        Commande input = getCommande();
        //Act
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () ->  commandeService.createCommande(input));
        //Assert
        assertEquals("destinataire.not.found", resourceNotFoundException.getMessage());
    }

    @Test
    void updateCommande() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        when(inMemoryCommande.findCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc")).thenReturn(getCommande());
        ArgumentCaptor<Commande> commandeArgumentCaptor = ArgumentCaptor.forClass(Commande.class);
        Commande input = getCommande();
        input.setQuantite(400);
        //Act
        commandeService.updateCommande("36c9ffc8-00fa-4339-87c3-e74013b87ccc",input);
        //Assert
        verify(inMemoryCommande,times(1)).updateCommande(anyString(),commandeArgumentCaptor.capture());
        Commande commande = commandeArgumentCaptor.getValue();
        assertNotNull(commande);
        assertNotNull(commande.getId());
        assertEquals(1000.0, commande.getPrix());
        assertEquals(400,commande.getQuantite());
        assertTrue(LocalDate.now().isBefore(commande.getDateDeLivraison()));
        Destinataire destinataire = commande.getDestinataire();
        assertNotNull(destinataire);
        assertEquals("dupont", destinataire.getNom());
        Adresse adresse = destinataire.getAdresse();
        assertNotNull(adresse);
        assertEquals("7 rue av", adresse.getAdresse());
        assertEquals("Paris", adresse.getVille());
        assertEquals("France", adresse.getPays());
        assertEquals(75008, adresse.getCodePostal()
                .intValue());
    }

    @Test
    void updateCommande_NotFound() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());

        ArgumentCaptor<Commande> commandeArgumentCaptor = ArgumentCaptor.forClass(Commande.class);
        Commande input = getCommande();
        //Act;
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () ->  commandeService.updateCommande("36c9ffc8-00fa-4339-87c3-e74013b87ccc",input));
        //Assert
        assertEquals("commande.not.found", resourceNotFoundException.getMessage());
    }

    @Test
    void findCommandeById() {
        //Arrange
        when(inMemoryCommande.findCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc")).thenReturn(getCommande());
        //Act
        Commande commande = commandeService.findCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc");
        //Assert
        assertNotNull(commande);
        assertNotNull(commande.getId());
        assertEquals(312.5, commande.getPrix());
        Destinataire destinataire = commande.getDestinataire();
        assertNotNull(destinataire);
        assertEquals("dupont", destinataire.getNom());
        Adresse adresse = destinataire.getAdresse();
        assertNotNull(adresse);
        assertEquals("7 rue av", adresse.getAdresse());
        assertEquals("Paris", adresse.getVille());
        assertEquals("France", adresse.getPays());
        assertEquals(75008, adresse.getCodePostal()
                .intValue());
    }

    @Test
    void test_findCommandeById_Ko() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> commandeService.findCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc"));
        //Assert
        assertEquals("commande.not.found", resourceNotFoundException.getMessage());
    }

    @Test
    void findCommandes() {
        //Arrange
        when(inMemoryCommande.findCommandes()).thenReturn(Collections.singletonList(getCommande()));
        //Act
        List<Commande> commandes = commandeService.findCommandes();
        //Assert
        Commande commande = commandes.get(0);
        assertNotNull(commande);
        assertNotNull(commande.getId());
        assertEquals(312.5, commande.getPrix());
        Destinataire destinataire = commande.getDestinataire();
        assertNotNull(destinataire);
        assertEquals("dupont", destinataire.getNom());
        Adresse adresse = destinataire.getAdresse();
        assertNotNull(adresse);
        assertEquals("7 rue av", adresse.getAdresse());
        assertEquals("Paris", adresse.getVille());
        assertEquals("France", adresse.getPays());
        assertEquals(75008, adresse.getCodePostal()
                .intValue());
    }

    @Test
    void deleteCommandeById() {
        //Arrange
        when(inMemoryCommande.findCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc")).thenReturn(getCommande());
        //Act
        commandeService.deleteCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc");
        //assert
        verify(inMemoryCommande, times(1)).deleteCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc");
    }

    @Test
    void test_deleteCommandeById_Ko() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> commandeService.deleteCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc"));
        //Assert
        assertEquals("commande.not.found", resourceNotFoundException.getMessage());
        verify(inMemoryCommande, times(0)).deleteCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc");
    }

    private Commande getCommande() {
        return Commande.builder()
                .id("36c9ffc8-00fa-4339-87c3-e74013b87ccc")
                .destinataire(getDestinataire())
                .dateDeLivraison(LocalDate.now()
                        .plusDays(7))
                .prix(312.5)
                .quantite(125)
                .build();
    }

    private Destinataire getDestinataire() {
        return Destinataire.builder()
                .id("36c9ffc8-00fa-4339-87c3-e74013b87aec")
                .nom("dupont")
                .adresse(Adresse.builder()
                        .adresse("7 rue av")
                        .ville("Paris")
                        .codePostal(75008)
                        .pays("France")
                        .build())
                .build();
    }
}