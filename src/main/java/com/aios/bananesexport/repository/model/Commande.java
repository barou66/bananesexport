package com.aios.bananesexport.repository.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"destinataire", "dateDeLivraison", "quantite", "prix"})
public class Commande {
    private String id;
    private Destinataire destinataire;
    private LocalDate dateDeLivraison;
    private int quantite;
    private double prix;
}
