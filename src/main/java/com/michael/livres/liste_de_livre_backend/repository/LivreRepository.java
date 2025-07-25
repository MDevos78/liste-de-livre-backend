package com.michael.livres.liste_de_livre_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michael.livres.liste_de_livre_backend.model.Livre;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    // Spring Data JPA génère automatiquement des méthodes comme findAll(), findById(), save(), deleteById()
    // Vous pouvez ajouter des méthodes personnalisées ici si besoin, ex: List<Livre> findByAuteur(String auteur);
}
