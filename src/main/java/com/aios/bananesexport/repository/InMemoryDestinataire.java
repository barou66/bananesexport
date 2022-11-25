package com.aios.bananesexport.repository;

import com.aios.bananesexport.repository.model.Destinataire;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class InMemoryDestinataire {
    private Map<String, Destinataire> destinataires = new HashMap<>();

    public Destinataire createDestinataire(Destinataire destinataire) {
        destinataire.setId(UUID.randomUUID()
                .toString());
        destinataires.put(destinataire.getId(), destinataire);
        return destinataire;
    }

    public boolean isDestinataireExist(Destinataire destinataire) {
        return this.destinataires.values()
                .stream()
                .anyMatch(d -> Objects.deepEquals(d, destinataire));
    }

    public Optional<String> findIdByDestinataire(Destinataire destinataire) {
        return this.destinataires.values()
                .stream()
                .filter(d -> Objects.deepEquals(d, destinataire))
                .map(Destinataire::getId)
                .findFirst();
    }

    public Destinataire findDestinataireById(String id) {
        return destinataires.getOrDefault(id, null);
    }

    public List<Destinataire> findDestinataires() {
        return new ArrayList<>(destinataires.values());
    }

    public void deleteDestinataireById(String id) {
        destinataires.remove(id);
    }

    public Destinataire updateDestinataire(String id, Destinataire destinataire) {
        destinataire.setId(id);
        destinataires.put(id, destinataire);
        return destinataire;
    }
}
