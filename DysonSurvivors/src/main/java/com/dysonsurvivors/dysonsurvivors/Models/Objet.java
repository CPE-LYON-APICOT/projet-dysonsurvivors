package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public abstract class Objet {
    private String nom;
    private String description;
    private String nomImage;

    public Objet(String nom, String description, String nomImage){
        this.nom = nom;
        this.description = description;
        this.nomImage = nomImage;
    }

    public String getNom(){
        return this.nom;
    }

    public String getDescription(){
        return this.description;
    }

    public String getNomImage(){
        return this.nomImage;
    }

}
