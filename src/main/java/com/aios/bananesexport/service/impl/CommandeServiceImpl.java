package com.aios.bananesexport.service.impl;

import com.aios.bananesexport.config.ConfigProperties;
import com.aios.bananesexport.exception.BusinessException;
import com.aios.bananesexport.exception.ResourceNotFoundException;
import com.aios.bananesexport.repository.InMemoryCommande;
import com.aios.bananesexport.repository.model.Commande;
import com.aios.bananesexport.service.CommandeService;
import com.aios.bananesexport.service.DestinataireService;
import com.aios.bananesexport.service.helper.CommandeHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommandeServiceImpl implements CommandeService {
    private final InMemoryCommande inMemoryCommande;
    private final ConfigProperties configProperties;
    private final DestinataireService destinataireService;

    public CommandeServiceImpl(InMemoryCommande inMemoryCommande, ConfigProperties configProperties,
                               DestinataireService destinataireService) {
        this.inMemoryCommande = inMemoryCommande;
        this.configProperties = configProperties;
        this.destinataireService = destinataireService;
    }

    @Override
    public Commande createCommande(Commande commande) {
        commonRule(commande);
        return inMemoryCommande.createCommande(commande);
    }


    @Override
    public Commande updateCommande(String id, Commande commande) {
        if(Objects.isNull(inMemoryCommande.findCommandeById(id))) {
            throw new ResourceNotFoundException("commande.not.found");
        }
        commonRule(commande);
        return inMemoryCommande.updateCommande(id, commande);
    }

    @Override
    public Commande findCommandeById(String id) {
        Commande commande = inMemoryCommande.findCommandeById(id);
        if(Objects.isNull(commande)) {
            throw new ResourceNotFoundException("commande.not.found");
        }
        return commande;
    }

    @Override
    public List<Commande> findCommandes() {
        return inMemoryCommande.findCommandes();
    }

    @Override
    public void deleteCommandeById(String id) {
        if(Objects.isNull(inMemoryCommande.findCommandeById(id))) {
            throw new ResourceNotFoundException("commande.not.found");
        }
        inMemoryCommande.deleteCommandeById(id);
    }

    private void commonRule(Commande commande) {
        checkBusinessRule(commande);
        setDestinataire(commande);
        setPrix(commande);
    }

    private void checkBusinessRule(Commande commande) {
        if(isValidQuantite(commande.getQuantite())) {
            throw new BusinessException("quantite.isnot.allow");
        }
        if(isMultipleOf25(commande.getQuantite())) {
            throw new BusinessException("quantite.not25.multiple");
        }
        if(commande.getDateDeLivraison()
                .isBefore(CommandeHelper.minimumLivraisonDate())) {
            throw new BusinessException("livraison.date.not.valid");
        }
    }

    private boolean isMultipleOf25(Integer quantite) {
        return !(quantite % configProperties.getQuantiteMultiple() == 0);
    }

    private boolean isValidQuantite(Integer quantite) {
        return !(quantite >= configProperties.getQuantiteMinimum() && quantite <= configProperties.getQuantiteMaximum());
    }

    private void setPrix(Commande commande) {
        commande.setPrix(configProperties.getPrixKg() * commande.getQuantite());
    }

    private void setDestinataire(Commande commande) {
        if(Objects.nonNull(commande.getDestinataire())) {
            commande.setDestinataire(destinataireService.findDestinataireById(commande.getDestinataire()
                    .getId()));
        }
    }
}
