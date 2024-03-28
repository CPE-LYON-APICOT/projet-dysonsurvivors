package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.Map;
import java.util.EnumMap;


import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public abstract class Personnage{
    private String nom;
    protected int pv;
    protected int pvMax;
    protected Map<Stats, Integer> stats;

    protected Pane hitbox;

    public Personnage(String nom, int pv, int pvMax) {
        /*this.hitbox = new Circle(20);*/
        this.nom = nom;
        this.pv = pv;
        this.pvMax = pvMax;
        this.stats = new EnumMap<>(Stats.class);
        initialiserCompetences();
    }

    private void initialiserCompetences() {
        for (Stats stat : Stats.values()) {
            stats.put(stat, 1);
        }
    }

    public void seDeplacer() {
        System.out.println("Personnage ne possède pas de déplacement");
    }

    public Pane getHitbox() {
        return hitbox;
    }
}
