package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.layout.Pane;

import java.util.Random;

public class SCourir implements IDeplacementMonstre{

    public void seDeplacer(Joueur joueur, Monstre monstre) {
        Pane hJoueur = joueur.getHitbox();
        Pane hMonstre = monstre.getHitbox();

        double playerCenterX = hJoueur.getLayoutX() + hJoueur.getPrefWidth() / 2;
        double playerCenterY = hJoueur.getLayoutY() + hJoueur.getPrefHeight() / 2;
        double monstreCenterX = hMonstre.getLayoutX() + hMonstre.getPrefWidth() / 2;
        double monstreCenterY = hMonstre.getLayoutY() + hMonstre.getPrefHeight() / 2;

        double angleToPlayer = Math.atan2(playerCenterY - monstreCenterY, playerCenterX - monstreCenterX);
        double speed = 0.7; // Ajuster la vitesse du monstre

        // Ajout de l'aléatoire à l'angle de déplacement
        Random random = new Random();
        double randomAngle = random.nextDouble() * Math.PI - Math.PI / 2; // Angle aléatoire entre -pi/8 et pi/8
        double finalAngle = angleToPlayer + randomAngle;

        double newX = monstreCenterX + Math.cos(finalAngle) * speed;
        double newY = monstreCenterY + Math.sin(finalAngle) * speed;

        // Mettre à jour les coordonnées de la hitbox du monstre
        monstre.getHitbox().setLayoutX(newX - hMonstre.getPrefWidth() / 2);
        monstre.getHitbox().setLayoutY(newY - hMonstre.getPrefHeight() / 2);
    }
}
