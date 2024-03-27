package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.shape.Circle;

public class SCourir implements IDeplacementMonstre{
    
    public void seDeplacer(Joueur joueur, Monstre monstre) {
        Circle hJoueur = joueur.getHitbox();
        Circle hMonstre = monstre.getHitbox();

        double playerX = hJoueur.getCenterX();
        double playerY = hJoueur.getCenterY();
        double monstreX = hMonstre.getCenterX();
        double monstreY = hMonstre.getCenterY();

        double angle = Math.atan2(playerY - monstreY, playerX - monstreX);
        double speed = 0.8; // You can adjust the speed of the monstre here
        double newX = monstreX + Math.cos(angle) * speed;
        double newY = monstreY + Math.sin(angle) * speed;

        monstre.getHitbox().setCenterX(newX);
        monstre.getHitbox().setCenterY(newY);

        System.out.println("Monstre Court");
    }
}
