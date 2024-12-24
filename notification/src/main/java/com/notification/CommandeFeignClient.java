package com.notification;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "commandes-service", url = "http://localhost:8222/api/commandes")
public interface CommandeFeignClient {

    @GetMapping("/{id}")
    CommandeResponse getCommandeById(@PathVariable Long id);

    class CommandeResponse {
        private Long id;
        private Long produitId;
        private Integer quantite;
        private String status;

        // Getters et Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getProduitId() {
            return produitId;
        }

        public void setProduitId(Long produitId) {
            this.produitId = produitId;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

