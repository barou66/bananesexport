package com.aios.bananesexport.repository;

import com.aios.bananesexport.repository.model.Commande;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryCommande {
    private Map<String, Commande> commandes = new HashMap<>();

    public Commande createCommande(Commande commande) {
        commande.setId(UUID.randomUUID()
                .toString());
        commandes.put(commande.getId(), commande);
        return commande;
    }

    public Commande updateCommande(String id, Commande commande) {
        commande.setId(id);
        commandes.put(id, commande);
        return commande;
    }

    public Commande findCommandeById(String id) {
        return commandes.getOrDefault(id, null);
    }

    public void deleteCommandeById(String id) {
        commandes.remove(id);
    }

    public List<Commande> findCommandes() {
        return new ArrayList<>(commandes.values());
    }
}
