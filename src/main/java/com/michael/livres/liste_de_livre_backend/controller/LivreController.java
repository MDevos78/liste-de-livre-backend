package com.michael.livres.liste_de_livre_backend.controller; // Ajustez le package

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.michael.livres.liste_de_livre_backend.model.Livre;
import com.michael.livres.liste_de_livre_backend.repository.LivreRepository;

import java.util.List;
import java.util.Optional;

@RestController // Indique que c'est un contrôleur REST
@RequestMapping("/api/livres") // Chemin de base pour toutes les requêtes de ce contrôleur
@CrossOrigin(origins = "http://localhost:8080") // Permet les requêtes du frontend Vue.js (ajustez le port si nécessaire)
public class LivreController {

    @Autowired // Injecte le repository
    private LivreRepository livreRepository;

    // GET all books
    @GetMapping
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    // GET book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Optional<Livre> livre = livreRepository.findById(id);
        return livre.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST (create new book)
    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        try {
            Livre savedLivre = livreRepository.save(livre);
            return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT (update book)
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id, @RequestBody Livre livreDetails) {
        Optional<Livre> livreOptional = livreRepository.findById(id);
        if (livreOptional.isPresent()) {
            Livre livre = livreOptional.get();
            livre.setTitre(livreDetails.getTitre());
            livre.setAuteur(livreDetails.getAuteur());
            livre.setAnneePublication(livreDetails.getAnneePublication());
            livre.setIsbn(livreDetails.getIsbn());
            livre.setGenre(livreDetails.getGenre());
            // La date_ajout n'est généralement pas mise à jour manuellement
            return new ResponseEntity<>(livreRepository.save(livre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE book
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLivre(@PathVariable Long id) {
        try {
            livreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
