package com.dysonsurvivors.dysonsurvivors.Models.Factory;

import com.dysonsurvivors.dysonsurvivors.Models.*;

public class FMonstre {
    public Monstre creerMonstre(int niveau, int atk, String spriteImage, LootTable lootTable) {
        String nom = "Monstre";
        int pvMax = 10 * niveau;
        int attaque = atk + niveau;
        int dropExp = 10 * niveau;
        return new Monstre(nom, pvMax, genererDeplacement(), attaque, spriteImage, lootTable, dropExp);
    }

    private IDeplacementMonstre genererDeplacement() {
        //Générer un déplacement aléatoire parmi SBoiter, SMarcher, SCourir
        double random = Math.random();
        if (random < 0.33) {
            return new SMarcher();
        } else if (random < 0.66) {
            return new SCourir();
        } else {
            return new SBoiter();
        }

    }
}
