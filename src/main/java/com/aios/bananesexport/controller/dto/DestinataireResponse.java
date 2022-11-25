package com.aios.bananesexport.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema
public class DestinataireResponse {
    private List<DestinataireDto> destinataires;
}
