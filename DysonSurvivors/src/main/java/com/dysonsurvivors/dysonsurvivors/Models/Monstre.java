package com.dysonsurvivors.dysonsurvivors.Models;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Monstre extends Personnage{
    private IDeplacementMonstre deplacement;
    private DoubleProperty radius;

    public Monstre(String nom, int pv, int pvMax, IDeplacementMonstre deplacement) {
        super(nom, pv, pvMax);
        this.deplacement = deplacement;
        chargerSprite(); // Charger la sprite du monstre
    }

    private void chargerSprite() {
        // Créer une Pane pour le monstre
        Pane monstrePane = new Pane();
        monstrePane.setPrefSize(30, 30); // Définir la taille du Pane du monstre (à adapter)
        monstrePane.setStyle("-fx-background-color: red;"); // Style du Pane du monstre

        // Ajouter la Pane du monstre à la hitbox
        hitbox = monstrePane;
    }
    private void attaquer() {
        System.out.println("Monstre attaque");
    }
    public void seDeplacer(Joueur joueur) {
        deplacement.seDeplacer(joueur,this);
    }
}