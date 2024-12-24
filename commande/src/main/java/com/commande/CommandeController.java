package com.commande;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @PostMapping
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande) {
        return ResponseEntity.ok(commandeService.ajouterCommande(commande));
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Commande> mettreAJourStatut(@PathVariable Long id, @RequestBody String statut) {
        return ResponseEntity.ok(commandeService.mettreAJourStatut(id, statut));
    }
}

