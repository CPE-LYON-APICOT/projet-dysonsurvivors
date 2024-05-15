package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.layout.Pane;

public class LootTable {
    private Map<Objet, Double> items;
    private Joueur joueur = JoueurSingleton.getInstance();

    public LootTable(Pane gamePane){
        items = new HashMap<>();
    }

    public void addItem(Objet objet, double probabiliteDrop) {
        items.put(objet, probabiliteDrop);
    }

    public boolean dropItem() {
        Random random = new Random();
        double totalWeight = 0.0;
        for (double prob : items.values()) {
            totalWeight += prob;
        }
        double randomValue = random.nextDouble() * totalWeight;
        for (Map.Entry<Objet, Double> entry : items.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue <= 0.0) {
                joueur.getInventaire().ajouterObjet(entry.getKey());
                System.out.println("Dropped: " + entry.getKey().getNom());
                return true;
            }
        }
        // No item dropped
        return false;
    }
}
