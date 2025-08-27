package com.michael.livres.liste_de_livre_backend.repository;

import com.michael.livres.liste_de_livre_backend.model.Livre;
import com.michael.livres.liste_de_livre_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByUser(User user);
}

