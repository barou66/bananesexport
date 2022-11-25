package com.aios.bananesexport.service;

import com.aios.bananesexport.repository.model.Commande;

import java.util.List;

public interface CommandeService {
    Commande createCommande(Commande commande);

    Commande updateCommande(String id,Commande commande);

    Commande findCommandeById(String id);

    List<Commande> findCommandes();

    void deleteCommandeById(String id);
}
