package com.aios.bananesexport.controller.mapper;

import com.aios.bananesexport.controller.dto.AdresseDto;
import com.aios.bananesexport.controller.dto.DestinataireDto;
import com.aios.bananesexport.controller.dto.DestinataireInput;
import com.aios.bananesexport.controller.dto.DestinataireResponse;
import com.aios.bananesexport.repository.model.Adresse;
import com.aios.bananesexport.repository.model.Destinataire;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public final class DestinataireMapper {
    private DestinataireMapper() {
    }

    public static Destinataire inputToModel(DestinataireInput input) {
        if(Objects.isNull(input)) {
            return null;
        }
        return Destinataire.builder()
                .nom(input.getNom())
                .adresse(adresseDtoToModel(input.getAdresse()))
                .build();
    }

    public static Adresse adresseDtoToModel(AdresseDto input) {
        if(Objects.isNull(input)) {
            return null;
        }
        return Adresse.builder()
                .adresse(input.getAdresse())
                .codePostal(input.getCodePostal())
                .pays(input.getPays())
                .ville(input.getVille())
                .build();
    }

    public static AdresseDto adresseModelToDto(Adresse model) {
        if(Objects.isNull(model)) {
            return null;
        }
        return AdresseDto.builder()
                .adresse(model.getAdresse())
                .ville(model.getVille())
                .codePostal(model.getCodePostal())
                .pays(model.getPays())
                .build();
    }

    public static DestinataireDto modelToDto(Destinataire model) {
        if(Objects.isNull(model)) {
            return null;
        }
        return DestinataireDto.builder()
                .id(model.getId())
                .nom(model.getNom())
                .adresse(adresseModelToDto(model.getAdresse()))
                .build();
    }

    public static DestinataireResponse destinatairesToResponse(List<Destinataire> destinataires) {
        if(CollectionUtils.isEmpty(destinataires)) {
            return DestinataireResponse.builder()
                    .destinataires(Collections.emptyList())
                    .build();
        }
        return DestinataireResponse.builder()
                .destinataires(destinataires.stream()
                        .map(DestinataireMapper::modelToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
