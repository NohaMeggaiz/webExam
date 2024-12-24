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

    private final CommandeKafkaProducer kafkaProducer;

    public CommandeService(CommandeRepository commandeRepository,
                           CommandeKafkaProducer kafkaProducer,
                           ProduitFeignClient produitFeignClient) {
        this.commandeRepository = commandeRepository;
        this.kafkaProducer = kafkaProducer;
        this.produitFeignClient = produitFeignClient;
    }

    public Commande processCommande(Commande commande) {
        ProduitFeignClient.ProduitResponse produit = produitFeignClient.getProduitById(commande.getProduitId());
        if (produit == null) {
            throw new IllegalArgumentException("Produit not found with ID: " + commande.getProduitId());
        }

        if (commande.getQuantite() <= 0) {
            throw new IllegalArgumentException("Quantité must be greater than zero");
        }
        if (commande.getQuantite() > produit.getStock()) {
            throw new IllegalArgumentException("Not enough stock for produit: " + produit.getNom());
        }

        commande.setProduitNom(produit.getNom());
        commande.setPrixUnitaire(produit.getPrix());
        commande.setTotalPrix(commande.getQuantite() * produit.getPrix());
        commande.setCreatedAt(LocalDateTime.now());
        commande.setStatus("CREATED");

        // Save the Commande to the database
        Commande savedCommande = commandeRepository.save(commande);

        // Prepare Kafka message
        String kafkaMessage = String.format(
                "Commande Created: [ID: %s, Produit: %s, Quantité: %d, Prix Total: %.2f, Status: %s]",
                savedCommande.getId(),
                savedCommande.getProduitNom(),
                savedCommande.getQuantite(),
                savedCommande.getTotalPrix(),
                savedCommande.getStatus()
        );

        // Send message to Kafka
        kafkaProducer.sendMessage(kafkaMessage);

        System.out.println("Commande processed and sent to Kafka: " + kafkaMessage);

        return savedCommande;
    }
}
