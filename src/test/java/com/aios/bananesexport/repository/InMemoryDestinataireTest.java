package com.aios.bananesexport.repository;

import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Destinataire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class InMemoryDestinataireTest {
    @Autowired
    private InMemoryDestinataire inMemoryDestinataire;

    @BeforeEach
    public void initMap() throws NoSuchFieldException, IllegalAccessException {
        Field destinatairesField = InMemoryDestinataire.class.getDeclaredField("destinataires");
        destinatairesField.setAccessible(true);

        Map<String, Destinataire> destinataireMap = (Map<String, Destinataire>) destinatairesField.get(
                inMemoryDestinataire);
        destinataireMap.clear();
        destinataireMap.put("36c9ffc8-00fa-4339-87c3-e74013b87aec", getDestinataire());
    }

    @Test
    void test_createDestinataire() {
        //Arrange
        Destinataire input = getDestinataire();
        //ACt
        Destinataire destinataire = inMemoryDestinataire.createDestinataire(input);

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
    void test_isDestinataireExist() {
        Destinataire destinataire = getDestinataire();
        //Assert
        assertTrue(inMemoryDestinataire.isDestinataireExist(destinataire));
        destinataire.setNom("Morreti");
        assertFalse(inMemoryDestinataire.isDestinataireExist(destinataire));
    }

    @Test
    void test_findIdByDestinataire() {
        //Arrange
        Destinataire destinataire = getDestinataire();
        //Act
        Optional<String> idByDestinataire = inMemoryDestinataire.findIdByDestinataire(destinataire);
        //Assert
        assertTrue(idByDestinataire.isPresent());
    }

    @Test
    void test_findDestinataireById() {
        Destinataire destinataire = inMemoryDestinataire.findDestinataireById(
                "36c9ffc8-00fa-4339-87c3-e74013b87aec");
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
    void test_findDestinataires() {
        List<Destinataire> destinataires = inMemoryDestinataire.findDestinataires();
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
    void test_deleteDestinataireById() {
        inMemoryDestinataire.deleteDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec");
        Destinataire destinataire = inMemoryDestinataire.findDestinataireById("36c9ffc8-00fa-4339-87c3-e74013b87aec");
        assertNull(destinataire);
    }

    @Test
    void test_updateDestinataire() {
        //Arrange
        Destinataire input = Destinataire.builder()
                .nom("dupont update")
                .adresse(Adresse.builder()
                        .adresse("7 rue av update")
                        .ville("Epinay")
                        .codePostal(93800)
                        .pays("France")
                        .build())
                .build();
        //ACt
        Destinataire destinataire = inMemoryDestinataire.updateDestinataire("36c9ffc8-00fa-4339-87c3-e74013b87aec",
                input);

        //Assert
        assertNotNull(destinataire);
        assertNotNull(destinataire.getId());
        assertEquals("dupont update", destinataire.getNom());
        Adresse adresse = destinataire.getAdresse();
        assertNotNull(adresse);
        assertEquals("7 rue av update", adresse.getAdresse());
        assertEquals("Epinay", adresse.getVille());
        assertEquals("France", adresse.getPays());
        assertEquals(93800, adresse.getCodePostal()
                .intValue());
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