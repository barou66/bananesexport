package com.aios.bananesexport.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Schema
public class AdresseDto {
    @Schema(example = "7 rue de la boetie 75000 Paris")
    @NotBlank
    private String adresse;
    @NonNull
    @Schema(example = "75000")
    private Integer codePostal;
    @Schema(example = "Paris")
    @NotBlank
    private String ville;
    @Schema(example = "France")
    @NotBlank
    private String pays;
}
