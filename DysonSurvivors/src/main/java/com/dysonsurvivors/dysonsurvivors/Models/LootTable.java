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
        double randomValue = random.nextDouble(); // Génère un nombre aléatoire entre 0 et 1

        for (Map.Entry<Objet, Double> entry : items.entrySet()) {
            if (randomValue <= entry.getValue()) {
                // Drop this item
                System.out.println("Dropped: " + entry.getKey().getNom());
                joueur.getInventaire().ajouterObjet(entry.getKey());
                return true;
            }
        }
        // No item dropped
        System.out.println("No item dropped");
        return false;
    }

    // public boolean dropItem() {
    //     Random random = new Random();
    //     double totalWeight = 0.0;
    //     for (double prob : items.values()) {
    //         totalWeight += prob;
    //     }
    //     double randomValue = random.nextDouble() * totalWeight;
    //     for (Map.Entry<Objet, Double> entry : items.entrySet()) {
    //         randomValue -= entry.getValue();
    //         if (randomValue <= 0.0) {
                
    //             System.out.println("Dropped: " + entry.getKey().getNom());
    //             return true;
    //         }
    //     }
    //     // No item dropped
    //     return false;
    // }
}
