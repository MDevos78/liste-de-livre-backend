package com.michael.livres.liste_de_livre_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.michael.livres.liste_de_livre_backend.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
