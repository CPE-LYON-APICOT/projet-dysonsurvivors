package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.Map;

public abstract class Personnage implements IDeplacement{
    private String nom;
    private int pv;
    private int pvMax;
    private Map<Stats, int> stats;

    public Personnage(String nom, int pv, int pvMax, Map<Stats,int> stats) {
        this.nom = nom;
        this.pv = pv;
        this.pvMax = pvMax;
        this.stats = stats;
    }

    public void seDeplacer() {
        System.out.println("Personnage se déplace");
    }
}
