package com.michael.livres.liste_de_livre_backend.controller;

import com.michael.livres.liste_de_livre_backend.model.Livre;
import com.michael.livres.liste_de_livre_backend.model.User;
import com.michael.livres.liste_de_livre_backend.repository.LivreRepository;
import com.michael.livres.liste_de_livre_backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreRepository livreRepository; // ⭐ Utilisez le repository directement
    private final UserRepository userRepository;

    public LivreController(LivreRepository livreRepository, UserRepository userRepository) {
        this.livreRepository = livreRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                                         .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        livre.setUser(currentUser);
        Livre savedLivre = livreRepository.save(livre); // ⭐ Appelez la méthode save du repository
        return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivres() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                                         .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        List<Livre> livres = livreRepository.findByUser(currentUser); // ⭐ Appelez la méthode findByUser du repository
        return ResponseEntity.ok(livres);
    }
}