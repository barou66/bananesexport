package com.aios.bananesexport.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Schema
public class DestinataireInput {
    @Schema(example = "Dupont")
    @NotBlank
    private String nom;
    @Schema
    @NotNull
    private AdresseDto adresse;
}
