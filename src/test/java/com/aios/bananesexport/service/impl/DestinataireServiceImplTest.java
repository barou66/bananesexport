package com.aios.bananesexport.service.impl;

import com.aios.bananesexport.exception.BusinessException;
import com.aios.bananesexport.exception.ResourceNotFoundException;
import com.aios.bananesexport.repository.InMemoryDestinataire;
import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.repository.model.Destinataire;
import com.aios.bananesexport.service.impl.DestinataireServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class DestinataireServiceImplTest {
    @MockBean
    private InMemoryDestinataire inMemoryDestinataire;
    @Autowired
    private DestinataireServiceImpl destinataireService;


    @Test
    void test_createDestinataire() {
        //Arrange
        when(inMemoryDestinataire.createDestinataire(any(Destinataire.class))).thenReturn(getDestinataire());
        //Act
        Destinataire destinataire = destinataireService.createDestinataire(getDestinataire());

        //Assert
        assertNotNull(destinataire);
        assertNotNull(destinataire.getId());
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
    void test_createDestinataireExist() {
        //Arrange
        when(inMemoryDestinataire.isDestinataireExist(any(Destinataire.class))).thenReturn(true);

        //Act
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> destinataireService.createDestinataire(getDestinataire()));
        //Assert
        assertEquals("destinataire.already.exist",businessException.getMessage());
    }

    @Test
    void test_updateDestinataire() {
        Destinataire destinataire = getDestinataire();
        destinataire.setNom("Morreti");
        //Arrange
        when(inMemoryDestinataire.findDestinataireById(any(String.class))).thenReturn(destinataire);

        ArgumentCaptor<Destinataire> destinataireArgumentCaptor= ArgumentCaptor.forClass(Destinataire.class);
        //Act
        destinataireService.updateDestinataire("36c9ffc8-00fa-4339-87c3-e74013b87aec", destinataire);
        //Assert
        verify(inMemoryDestinataire,times(1)).updateDestinataire(anyString(),destinataireArgumentCaptor.capture());
        destinataire = destinataireArgumentCaptor.getValue();
        assertNotNull(destinataire);
        assertNotNull(destinataire.getId());
        assertEquals("Morreti", destinataire.getNom());
        Adresse adresse = destinataire.getAdresse();
        assertNotNull(adresse);
        assertEquals("7 rue av", adresse.getAdresse());
        assertEquals("Paris", adresse.getVille());
        assertEquals("France", adresse.getPays());
        assertEquals(75008, adresse.getCodePostal());
    }

    @Test
    void test_updateDestinataireNotUnique() {
        Destinataire destinataire = getDestinataire();

        //Arrange
        when(inMemoryDestinataire.findDestinataireById(any(String.class))).thenReturn(destinataire);
        when(inMemoryDestinataire.findIdByDestinataire(any(Destinataire.class))).thenReturn(Optional.of("36c9ffc8-00fa-4339-87c3-e74013b87a88"));
        when(inMemoryDestinataire.updateDestinataire(any(String.class), any(Destinataire.class))).thenReturn(
                destinataire);
        //Act

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> destinataireService.updateDestinataire("36c9ffc8-00fa-4339-87c3-e74013b87a99", destinataire));
        //Assert
        assertEquals("destinaire.must.be.unique",businessException.getMessage());
    }

    @Test
    void test_updateDestinataireNotFound() {
        Destinataire destinataire = getDestinataire();
        destinataire.setNom("Morreti");
        //Act
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> destinataireService.updateDestinataire("36c9ffc8-00fa-4339-87c3-e74013b87a88", destinataire));
        //Assert
        assertEquals("destinataire.not.found",resourceNotFoundException.getMessage());
    }

    @Test
    void findDestinataireById() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        //Act
        Destinataire destinataire = destinataireService.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec");

        //Assert
        assertNotNull(destinataire);
        assertNotNull(destinataire.getId());
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
    void test_findDestinataireById_Ko() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> destinataireService.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87a88"));
        //Assert
        assertEquals("destinataire.not.found",resourceNotFoundException.getMessage());
    }

    @Test
    void findDestinataires() {
        //Arrange
        when(inMemoryDestinataire.findDestinataires()).thenReturn(Collections.singletonList(getDestinataire()));
        //Act
        List<Destinataire> destinataires = destinataireService.findDestinataires();
        //Assert
        assertEquals(1, destinataires.size());
        Destinataire destinataire = destinataires.get(0);
        assertNotNull(destinataire);
        assertNotNull(destinataire.getId());
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
    void deleteDestinataireById() {
        //Arrange
        when(inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec")).thenReturn(
                getDestinataire());
        //Act
        destinataireService.deleteDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec");
        //assert
        verify(inMemoryDestinataire, times(1)).deleteDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec");
    }

    @Test
    void test_deleteDestinataireById_Ko() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> destinataireService.deleteDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87a88"));
        //Assert
        assertEquals("destinataire.not.found",resourceNotFoundException.getMessage());
        verify(inMemoryDestinataire, times(0)).deleteDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87a88");
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