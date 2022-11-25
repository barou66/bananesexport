package com.aios.bananesexport.controller.mapper;

import com.aios.bananesexport.controller.dto.CommandeDto;
import com.aios.bananesexport.controller.dto.CommandeInput;
import com.aios.bananesexport.controller.dto.CommandeResponse;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.repository.model.Destinataire;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CommandeMapper {
    private CommandeMapper() {

    }

    public static Commande inputToModel(CommandeInput input) {
        if(Objects.isNull(input)) {
            return null;
        }
        return Commande.builder()
                .destinataire(Destinataire.builder()
                        .id(input.getDestinataireId())
                        .build())
                .dateDeLivraison(input.getDateDeLivraison())
                .quantite(input.getQuantite())
                .build();
    }

    public static CommandeDto modelToDto(Commande commande) {
        if(Objects.isNull(commande)) {
            return null;
        }
        return CommandeDto.builder()
                .id(commande.getId())
                .destinataire(DestinataireMapper.modelToDto(commande.getDestinataire()))
                .dateDeLivraison(commande.getDateDeLivraison())
                .prix(commande.getPrix())
                .quantite(commande.getQuantite())
                .build();
    }

    public static CommandeResponse commandesToResponse(List<Commande> commandes) {
        if(CollectionUtils.isEmpty(commandes)) {
            return CommandeResponse.builder()
                    .commandes(Collections.emptyList())
                    .build();
        }
        return CommandeResponse.builder()
                .commandes(commandes.stream()
                .map(CommandeMapper::modelToDto)
                .collect(Collectors.toList()))
                .build();
    }
}
