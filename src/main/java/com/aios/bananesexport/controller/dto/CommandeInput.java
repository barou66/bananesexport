package com.aios.bananesexport.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Builder
@Schema
public class CommandeInput {
    @NotBlank
    @Schema(example = "aa7f876f-22ef-4d43-9390-d8134bfb72cd")
    private String destinataireId;
    @NotNull
    @Schema
    private LocalDate dateDeLivraison;
    @NotNull
    @Schema(example = "350")
    private int quantite;
}
