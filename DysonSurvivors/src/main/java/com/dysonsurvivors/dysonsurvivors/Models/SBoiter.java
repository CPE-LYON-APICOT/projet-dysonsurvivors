package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class SBoiter implements IDeplacementMonstre {
    public void seDeplacer(Joueur joueur, Monstre monstre) {
        Pane hJoueur = joueur.getHitbox();
        Pane hMonstre = monstre.getHitbox();

        double playerCenterX = hJoueur.getLayoutX() + hJoueur.getPrefWidth() / 2;
        double playerCenterY = hJoueur.getLayoutY() + hJoueur.getPrefHeight() / 2;
        double monstreCenterX = hMonstre.getLayoutX() + hMonstre.getPrefWidth() / 2;
        double monstreCenterY = hMonstre.getLayoutY() + hMonstre.getPrefHeight() / 2;

        double angle = Math.atan2(playerCenterY - monstreCenterY, playerCenterX - monstreCenterX);
        double speed = 0.3; // Ajuster la vitesse du monstre
        double newX = monstreCenterX + Math.cos(angle) * speed;
        double newY = monstreCenterY + Math.sin(angle) * speed;

        // Mettre à jour les coordonnées de la hitbox du monstre
        monstre.getHitbox().setLayoutX(newX - hMonstre.getPrefWidth() / 2);
        monstre.getHitbox().setLayoutY(newY - hMonstre.getPrefHeight() / 2);
    }
}
