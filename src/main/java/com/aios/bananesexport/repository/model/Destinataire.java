package com.aios.bananesexport.repository.model;

import lombok.*;


@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"nom", "adresse", "codePostal", "ville", "pays"})
public class Destinataire {
    private String id;
    private String nom;
    private Adresse adresse;
}
