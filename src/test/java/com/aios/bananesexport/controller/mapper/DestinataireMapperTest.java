package com.aios.bananesexport.controller.mapper;

import com.aios.bananesexport.controller.dto.AdresseDto;
import com.aios.bananesexport.controller.dto.DestinataireDto;
import com.aios.bananesexport.controller.dto.DestinataireInput;
import com.aios.bananesexport.controller.dto.DestinataireResponse;
import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Destinataire;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DestinataireMapperTest {

    @Test
    void inputToModel() {
        assertNull(DestinataireMapper.inputToModel(null));
        Destinataire destinataire = DestinataireMapper.inputToModel(DestinataireInput.builder()
                .nom("Destinataire")
                .adresse(getAdresseDto())
                .build());
        assertNotNull(destinataire);
        assertEquals("Destinataire",destinataire.getNom());
        assertNotNull(destinataire.getAdresse());
        assertNull(destinataire.getId());
    }

    @Test
    void adresseDtoToModel() {
        assertNull(DestinataireMapper.adresseDtoToModel(null));
        Adresse adresse = DestinataireMapper.adresseDtoToModel(getAdresseDto());
        assertEquals("71 rue de la boétie 75008",adresse.getAdresse());
        assertEquals(75008,adresse.getCodePostal());
        assertEquals("Paris",adresse.getVille());
        assertEquals("France",adresse.getPays());
    }


    @Test
    void modelToDto() {
        assertNull(DestinataireMapper.modelToDto(null));
        DestinataireDto destinataire = DestinataireMapper.modelToDto(getDestinataire());
        assertEquals("D1",destinataire.getId());
        assertEquals("Dupont",destinataire.getNom());
        assertNotNull(destinataire.getAdresse());
    }

    @Test
    void testAdresseModelToDto() {
        assertNull(DestinataireMapper.adresseModelToDto(null));
        AdresseDto adresse = DestinataireMapper.adresseModelToDto(getAdresse());
        assertEquals("71 rue de la boétie 75008",adresse.getAdresse());
        assertEquals(75008,adresse.getCodePostal());
        assertEquals("Paris",adresse.getVille());
        assertEquals("France",adresse.getPays());
    }

    @Test
    void destinatairesToResponse() {
        DestinataireResponse response = DestinataireMapper.destinatairesToResponse(Collections.emptyList());
        assertNotNull(response);
        assertTrue(CollectionUtils.isEmpty(response.getDestinataires()));
        response = DestinataireMapper.destinatairesToResponse(
                Collections.singletonList(getDestinataire()));
        assertNotNull(response);
        assertEquals(1,response.getDestinataires().size());
        DestinataireDto destinataire = response.getDestinataires()
                .get(0);
        assertEquals("D1",destinataire.getId());
        assertEquals("Dupont",destinataire.getNom());
        assertNotNull(destinataire.getAdresse());
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