package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.Map;

public class Joueur extends Personnage{
    private Inventaire inventaire;
    private int XP;
    private int niveau;

    public Joueur(String nom, Inventaire inventaire, int XP, int niveau) {
        super(nom);
        this.inventaire = inventaire;
        this.XP = XP;
        this.niveau = niveau;
    }

    private void attaquer() {
        System.out.println("Joueur attaque");
    }
}
