package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.Map;

public class Joueur extends Personnage{
    private Inventaire inventaire;
    private int XP;
    private int niveau;

    public Joueur(String nom, int pv, int pvMax, Map<Stats, int> stats, Inventaire inventaire, int XP, int niveau) {
        super(nom, pv, pvMax, stats);
        this.inventaire = inventaire;
        this.XP = XP;
        this.niveau = niveau;
    }

    private void attaquer() {
        System.out.println("Joueur attaque");
    }
}
