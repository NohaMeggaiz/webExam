package com.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> ajouterProduit(@RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.ajouterProduit(produit));
    }
}
