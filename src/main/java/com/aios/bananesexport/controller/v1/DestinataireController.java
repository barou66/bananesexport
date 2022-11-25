package com.aios.bananesexport.controller.v1;


import com.aios.bananesexport.controller.ApiConstants;
import com.aios.bananesexport.controller.abs.ApiResponse200;
import com.aios.bananesexport.controller.abs.ApiResponse201;
import com.aios.bananesexport.controller.abs.ApiResponse204;
import com.aios.bananesexport.controller.dto.DestinataireDto;
import com.aios.bananesexport.controller.dto.DestinataireInput;
import com.aios.bananesexport.controller.dto.DestinataireResponse;
import com.aios.bananesexport.controller.mapper.DestinataireMapper;
import com.aios.bananesexport.service.DestinataireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ApiConstants.API_VERSION_ONE + "destinataires", produces = APPLICATION_JSON_VALUE)
@Tag(name = "DestinataireController", description = "DestinataireController - Api")
public class DestinataireController {
    private final DestinataireService destinataireService;

    public DestinataireController(DestinataireService destinataireService) {
        this.destinataireService = destinataireService;
    }

    @PostMapping
    @Operation(summary = "Create Destinataire", operationId = "createDestinataire")
    @ApiResponse201(content = {@Content(schema = @Schema(implementation = DestinataireDto.class))})
    public ResponseEntity<DestinataireDto> createDestinataire(@RequestBody @Valid @NotNull DestinataireInput input) {
        return new ResponseEntity<>(DestinataireMapper.modelToDto(
                destinataireService.createDestinataire(DestinataireMapper.inputToModel(input))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update Destinataire", operationId = "updateDestinataire")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = DestinataireDto.class))})
    public ResponseEntity<DestinataireDto> updateDestinataire(@PathVariable String id,
                                                              @RequestBody @Valid @NotNull DestinataireInput input) {
        return new ResponseEntity<>(DestinataireMapper.modelToDto(
                destinataireService.updateDestinataire(id, DestinataireMapper.inputToModel(input))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Destinataire by id", operationId = "deleteDestinataire")
    @ApiResponse204()
    public ResponseEntity<Void> deleteDestinataire(@PathVariable @Valid @NotBlank String id) {
        destinataireService.deleteDestinataireById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get Destinataire by id", operationId = "getDestinataireById")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = DestinataireDto.class))})
    public ResponseEntity<DestinataireDto> getDestinataireById(@PathVariable @Valid @NotBlank String id) {
        return new ResponseEntity<>(DestinataireMapper.modelToDto(destinataireService.findDestinataireById(id)),
                HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "find all Destinataire", operationId = "findDestinataires")
    @ApiResponse200(content = @Content(schema = @Schema(implementation = DestinataireResponse.class)))
    public ResponseEntity<DestinataireResponse> findDestinataires() {
        return ResponseEntity.ok(DestinataireMapper.destinatairesToResponse(destinataireService.findDestinataires()));
    }

}
