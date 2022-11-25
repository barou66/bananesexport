package com.aios.bananesexport.repository;

import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.repository.model.Destinataire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class InMemoryCommandeTest {

    @Autowired
    private InMemoryCommande inMemoryCommande;

    @BeforeEach
    public void initMap() throws NoSuchFieldException, IllegalAccessException {
        Field commandesField = InMemoryCommande.class.getDeclaredField("commandes");
        commandesField.setAccessible(true);

        Map<String, Commande> commandeMap = (Map<String, Commande>) commandesField.get(inMemoryCommande);
        commandeMap.clear();
        commandeMap.put("36c9ffc8-00fa-4339-87c3-e74013b87ccc", getCommande());
    }

    @Test
    void test_createCommande() {
        //Arrange
        Commande input = getCommande();
        //ACt
        Commande commande = inMemoryCommande.createCommande(input);

        //Assert
        assertNotNull(commande);
        assertNotNull(commande.getId());
        assertEquals(312.5,commande.getPrix());
        assertEquals(125,commande.getQuantite());
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
    void test_findCommandeById() {
        Commande commande = inMemoryCommande.findCommandeById(
                "36c9ffc8-00fa-4339-87c3-e74013b87ccc");
        //Assert
        assertNotNull(commande);
        assertNotNull(commande.getId());
        assertEquals(312.5,commande.getPrix());
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
    void test_findCommandes() {
        List<Commande> commandes = inMemoryCommande.findCommandes();
        //Assert
      Commande commande = commandes.get(0);
      assertNotNull(commande);
      assertNotNull(commande.getId());
      assertEquals(312.5,commande.getPrix());
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
    void test_deleteCommandeById() {
        inMemoryCommande.deleteCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc");
        Commande commande = inMemoryCommande.findCommandeById("36c9ffc8-00fa-4339-87c3-e74013b87ccc");
        assertNull(commande);
    }

   @Test
    void test_updateCommande() {
        //Arrange
        Commande input = getCommande();
        input.setQuantite(300);
        input.setPrix(750.0);
        //ACt
        Commande commande = inMemoryCommande.updateCommande("36c9ffc8-00fa-4339-87c3-e74013b87ccc",
                input);

        //Assert
       assertNotNull(commande);
       assertNotNull(commande.getId());
       assertEquals(750.0,commande.getPrix());
       assertEquals(300,commande.getQuantite());
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

    private Commande getCommande() {
        return Commande.builder()
                .id("36c9ffc8-00fa-4339-87c3-e74013b87ccc")
                .destinataire(Destinataire.builder()
                        .id("36c9ffc8-00fa-4339-87c3-e74013b87aec")
                        .nom("dupont")
                        .adresse(Adresse.builder()
                                .adresse("7 rue av")
                                .ville("Paris")
                                .codePostal(75008)
                                .pays("France")
                                .build())
                        .build())
                .dateDeLivraison(LocalDate.now()
                        .plusDays(7))
                .prix(312.5)
                .quantite(125)
                .build();
    }

}