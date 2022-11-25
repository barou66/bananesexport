package com.aios.bananesexport.service;

import com.aios.bananesexport.repository.model.Destinataire;

import java.util.List;

public interface DestinataireService {
    Destinataire createDestinataire(Destinataire destinataire);

    Destinataire updateDestinataire(String id, Destinataire destinataire);

    Destinataire findDestinataireById(String id);

    List<Destinataire> findDestinataires();

    void deleteDestinataireById(String id);
}
