package com.aios.bananesexport.service.impl;

import com.aios.bananesexport.exception.BusinessException;
import com.aios.bananesexport.exception.ResourceNotFoundException;
import com.aios.bananesexport.repository.InMemoryDestinataire;
import com.aios.bananesexport.repository.model.Destinataire;
import com.aios.bananesexport.service.DestinataireService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DestinataireServiceImpl implements DestinataireService {
    private final InMemoryDestinataire inMemoryDestinataire;

    public DestinataireServiceImpl(InMemoryDestinataire inMemoryDestinataire) {
        this.inMemoryDestinataire = inMemoryDestinataire;
    }

    @Override
    public Destinataire createDestinataire(Destinataire destinataire) {
        if(inMemoryDestinataire.isDestinataireExist(destinataire)) {
            throw new BusinessException("destinataire.already.exist");
        }
        return inMemoryDestinataire.createDestinataire(destinataire);
    }

    @Override
    public Destinataire updateDestinataire(String id, Destinataire destinataire) {
        if(Objects.isNull(inMemoryDestinataire.findDestinataireById(id))) {
            throw new ResourceNotFoundException("destinataire.not.found");
        }
        checkIfUnique(id, destinataire);
        return inMemoryDestinataire.updateDestinataire(id, destinataire);
    }

    private void checkIfUnique(String id, Destinataire destinataire) {
        Optional<String> idByDestinataire = inMemoryDestinataire.findIdByDestinataire(destinataire);
        if(idByDestinataire.isPresent() && !idByDestinataire.get().equals(id)){
            throw new BusinessException("destinaire.must.be.unique");
        }
    }

    @Override
    public Destinataire findDestinataireById(String id) {
        Destinataire destinataire = inMemoryDestinataire.findDestinataireById(id);
        if(Objects.isNull(destinataire)) {
            throw new ResourceNotFoundException("destinataire.not.found");
        }
        return destinataire;
    }


    @Override
    public List<Destinataire> findDestinataires() {
        return inMemoryDestinataire.findDestinataires();
    }

    @Override
    public void deleteDestinataireById(String id) {
        if(Objects.isNull(inMemoryDestinataire.findDestinataireById(id))) {
            throw new ResourceNotFoundException("destinataire.not.found");
        }
        inMemoryDestinataire.deleteDestinataireById(id);
    }
}
