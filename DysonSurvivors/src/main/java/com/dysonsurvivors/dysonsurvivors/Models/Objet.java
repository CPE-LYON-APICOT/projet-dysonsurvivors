package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Objet {
    private String nom;
    private String description;
    private Image image;

    public Objet(String nom, String description, String nomImage){
        this.nom = nom;
        this.description = description;
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("grenouille.png")));
    }

    public String getNom(){
        return this.nom;
    }

    public String getDescription(){
        return this.description;
    }

    public Image getImage(){
        return this.image;
    }
}
