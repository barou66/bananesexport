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
public class DestinataireDto {
    @Schema(example = "ca0e225e-b5c8-4dfa-b58b-e43620161e90")
    private String id;
    @Schema(example = "Dupont")
    @NotBlank
    private String nom;
    @NotNull
    @Schema
    private AdresseDto adresse;
}
