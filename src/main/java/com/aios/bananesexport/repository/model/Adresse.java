package com.aios.bananesexport.repository.model;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"adresse", "codePostal", "ville", "pays"})
public class Adresse {
    private String adresse;
    private Integer codePostal;
    private String ville;
    private String pays;
}
