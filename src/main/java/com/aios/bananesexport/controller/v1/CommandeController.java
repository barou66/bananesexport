package com.aios.bananesexport.controller.v1;

import com.aios.bananesexport.controller.ApiConstants;
import com.aios.bananesexport.controller.abs.ApiResponse200;
import com.aios.bananesexport.controller.abs.ApiResponse201;
import com.aios.bananesexport.controller.abs.ApiResponse204;
import com.aios.bananesexport.controller.dto.CommandeDto;
import com.aios.bananesexport.controller.dto.CommandeInput;
import com.aios.bananesexport.controller.dto.CommandeResponse;
import com.aios.bananesexport.controller.mapper.CommandeMapper;
import com.aios.bananesexport.service.CommandeService;
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
@RequestMapping(path = ApiConstants.API_VERSION_ONE + "commandes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CommandeController", description = "CommandeController - Api")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    @Operation(summary = "Create Commande", operationId = "createCommande")
    @ApiResponse201(content = {@Content(schema = @Schema(implementation = CommandeDto.class))})
    public ResponseEntity<CommandeDto> createCommande(@RequestBody @Valid @NotNull CommandeInput input) {
        return new ResponseEntity<>(
                CommandeMapper.modelToDto(commandeService.createCommande(CommandeMapper.inputToModel(input))),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update Commande", operationId = "updateCommandeDto")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = CommandeDto.class))})
    public ResponseEntity<CommandeDto> updateCommande(@PathVariable String id,
                                                      @RequestBody @Valid @NotNull CommandeInput input) {
        return new ResponseEntity<>(
                CommandeMapper.modelToDto(commandeService.updateCommande(id, CommandeMapper.inputToModel(input))),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Commande by id", operationId = "deleteCommande")
    @ApiResponse204()
    public ResponseEntity<Void> deleteCommande(@PathVariable @Valid @NotBlank String id) {
        commandeService.deleteCommandeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get Commande by id", operationId = "getCommandeById")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = CommandeDto.class))})
    public ResponseEntity<CommandeDto> getCommandeById(@PathVariable @Valid @NotBlank String id) {
        return new ResponseEntity<>(CommandeMapper.modelToDto(commandeService.findCommandeById(id)), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "find all Commande", operationId = "findCommandes")
    @ApiResponse200(content = @Content(schema = @Schema(implementation = CommandeResponse.class)))
    public ResponseEntity<CommandeResponse> findCommandes() {
        return ResponseEntity.ok(CommandeMapper.commandesToResponse(commandeService.findCommandes()));
    }
}
