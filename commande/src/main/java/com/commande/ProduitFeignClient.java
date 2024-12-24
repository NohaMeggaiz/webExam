package com.commande;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produits-service", url = "http://localhost:8222/api/produits")
public interface ProduitFeignClient {

    @GetMapping("/{id}")
    ProduitResponse getProduitById(@PathVariable Long id);

    class ProduitResponse {
        private Long id;
        private String nom;
        private Double prix;
        private Integer stock;

        // Getters et Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public Double getPrix() {
            return prix;
        }

        public void setPrix(Double prix) {
            this.prix = prix;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }
}
