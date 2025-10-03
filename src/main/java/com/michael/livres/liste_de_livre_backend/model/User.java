package com.michael.livres.liste_de_livre_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore; // ⭐ NOUVEL IMPORT

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


	@Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ⭐ COUPE la boucle ici pour que la sérialisation d'un User n'inclue pas sa liste de Livres (ce qui causerait la boucle)
    private List<Livre> livres;
    
    

    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public OffsetDateTime getCreatedAt() {
    	return createdAt;
    }
    
    public void setCreatedAt(OffsetDateTime createdAt) {
    	this.createdAt = createdAt;
    }
    
}


