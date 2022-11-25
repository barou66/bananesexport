package com.aios.bananesexport.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeResponse {
    private List<CommandeDto> commandes;
}
