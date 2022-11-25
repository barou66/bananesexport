package com.aios.bananesexport.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema
public class CommandeDto {
    @Schema(example = "aa7f876f-22ef-4d43-9390-d8134bfb72cd")
    private String id;
    @Schema
    private DestinataireDto destinataire;
    @Schema
    private LocalDate dateDeLivraison;
    @Schema
    private Integer quantite;
    @Schema
    private double prix;
}
