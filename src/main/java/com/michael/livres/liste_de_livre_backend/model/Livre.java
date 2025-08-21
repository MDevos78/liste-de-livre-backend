package com.michael.livres.liste_de_livre_backend.model; // Assurez-vous que ce package correspond au vôtre

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import jakarta.persistence.Table;

@Entity
@Table(name = "livres") // <-- Ajoutez cette ligne pour spécifier le nom de la table
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String auteur;

    private Integer anneePublication;

    @Column(unique = true)
    private String isbn;

    @Column(name = "date_ajout", insertable = false, updatable = false)
    private LocalDateTime dateAjout;
    
    @Column(nullable = false)
    private String genre;

    public Livre() {}

    public Livre(String titre, String auteur, Integer anneePublication, String isbn, String genre) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.genre = genre;
    }
    
    // ⭐ C'est la méthode à ajouter pour générer l'ISBN
    @PrePersist
    public void generateIsbnFromTitle() {
        if (this.isbn == null || this.isbn.isEmpty()) {
            if (this.titre != null) {
                // Supprime les espaces et met tout en minuscules
                this.isbn = this.titre.replaceAll("\\s+", "").toLowerCase();
            }
        }
    }

    // Getters et Setters
    public Long getId() { 
    	return id; 
    	}
    public void setId(Long id) { 
    	this.id = id; 
    	}
    public String getTitre() { 
    	return titre; 
    	}
    public void setTitre(String titre) { 
    	this.titre = titre;
    	}
    public String getAuteur() { 
    	return auteur; 
    	}
    public void setAuteur(String auteur) { 
    	this.auteur = auteur;
    	}
    public Integer getAnneePublication() { 
    	return anneePublication; 
    	}
    public void setAnneePublication(Integer anneePublication) { 
    	this.anneePublication = anneePublication; 
    	}
    public String getIsbn() { 
    	return isbn; 
    	}
    public void setIsbn(String isbn) { 
    	this.isbn = isbn;
    	}
    public LocalDateTime getDateAjout() {
    	return dateAjout; 
    	}
    public void setDateAjout(LocalDateTime dateAjout) { 
    	this.dateAjout = dateAjout; 
    	}
    public String getGenre() { 
    	return genre; 
    	}
    public void setGenre(String genre) { 
    	this.genre = genre;
    	}
}
