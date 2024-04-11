package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public abstract class Objet {
    private String nom;
    private String description;
    private ImageView sprite;
    private Pane hitbox;
    private String nomImage;

    public Objet(String nom, String description, String nomImage){
        this.nom = nom;
        this.description = description;
        /*chargerSprite(nomImage);*/
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

    /*public void chargerSprite(String nomImage) {
        // Charger la sprite sheet
        Image spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream(nomImage)));

        // Créer une ImageView pour afficher la sprite animée
        sprite = new ImageView(spriteSheet);

        // Définir les coordonnées et la taille de la première frame
        sprite.setViewport(new Rectangle2D(0, 0, 64, 64));

        // Ajouter l'ImageView à une Pane
        Pane objetPane = new Pane(sprite);
        hitbox = objetPane;
        // Définir la taille de la hitbox
        hitbox.setPrefSize(64, 64);
        hitbox.setStyle("-fx-background-color: blue;");
    }*/

}
