package com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes;

public class ChampignonHallucinogene extends Arme{

    public ChampignonHallucinogene() {
        super("Champignon Hallucinogene", "Un champignon hallucinogène qui vous permettra de vous échapper de la réalité", "Mushrooms/1.png");
    }

    public void utiliser() {
        System.out.println("Vous avez utilisé un champignon hallucinogène");
    }
}
