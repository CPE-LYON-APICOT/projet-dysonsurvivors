package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.Map;
import java.util.Set;

import javafx.print.PrinterJob;

public class Joueur extends Personnage{
    private Set<Objet> inventaire;
    private int XP;
    private int niveau;


    public Joueur(String nom, int pv, int pvMax) {
        super(nom, pv, pvMax);
    }

    private void attaquer() {
        System.out.println("Joueur attaque");
    }

    public void seDeplacer(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) {
        // System.out.println("Joueur marche");
        double dx = 0, dy = 0;
        if (upPressed) dy -= 1;
        if (downPressed) dy += 1;
        if (leftPressed) dx -= 1;
        if (rightPressed) dx += 1;

        // Apply movement
        double newX = this.getHitbox().getCenterX() + dx;
        double newY = this.getHitbox().getCenterY() + dy;

        this.getHitbox().setCenterX(newX);
        this.getHitbox().setCenterY(newY);
        
    }
}
