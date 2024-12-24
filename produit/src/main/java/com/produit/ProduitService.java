package com.produit;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    // Ajouter un produit
    public Produit ajouterProduit(Produit produit) {
        return produitRepository.save(produit);
    }


    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }


    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }


    public Produit mettreAJourProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        produit.setNom(produitDetails.getNom());
        produit.setPrix(produitDetails.getPrix());
        produit.setStock(produitDetails.getStock());

        return produitRepository.save(produit);
    }

    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }
}
