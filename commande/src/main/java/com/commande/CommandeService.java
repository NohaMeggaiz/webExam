package com.commande;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitFeignClient produitFeignClient;

    public Commande ajouterCommande(Commande commande) {
        ProduitFeignClient.ProduitResponse produit = produitFeignClient.getProduitById(commande.getProduitId());

        if (produit.getStock() < commande.getQuantite()) {
            throw new RuntimeException("Stock insuffisant pour le produit " + produit.getNom());
        }

        commande.setDateCommande(LocalDateTime.now());
        commande.setStatus("En attente");
        return commandeRepository.save(commande);
    }


    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Commande mettreAJourStatut(Long id, String statut) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
        commande.setStatus(statut);
        return commandeRepository.save(commande);
    }
}
