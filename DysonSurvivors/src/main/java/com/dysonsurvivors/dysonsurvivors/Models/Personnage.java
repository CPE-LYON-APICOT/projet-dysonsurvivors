package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.layout.Pane;

import java.util.EnumMap;
import java.util.Map;

public abstract class Personnage{
    private String nom;
    protected int pv;
    protected int pvMax;
    protected Map<Stats, Integer> stats;

    protected Pane hitbox;

    public Personnage(String nom, int pvMax) {
        /*this.hitbox = new Circle(20);*/
        this.nom = nom;
        this.pvMax = pvMax;
        this.pv = pvMax;
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

    public int getPv() {
        return pv;
    }

    public void perdreVie(int degats) {
        pv -= degats;
    }

    /*FORCE, PERCEPTION, ENDURANCE, CHARISME, INTELLIGENCE, AGILITE, CHANCE*/
    public Integer getStat(Stats stat) {
        return stats.get(stat);
    }
}
