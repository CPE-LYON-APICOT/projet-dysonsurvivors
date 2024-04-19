package com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes;

import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Equipement;
import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import javafx.scene.layout.Pane;

public class Arme extends Equipement {
    private double degats;
    private double portee;
    private double vitesseDeTir;

    public Arme(String nom, String description, String nomImage) {
        super(nom, description, nomImage);
    }

    public double getDegats() {
        return degats;
    }

    public void setDegats(double degats) {
        this.degats = degats;
    }

    public double getPortee() {
        return portee;
    }

    public void setPortee(double portee) {
        this.portee = portee;
    }

    public double getVitesseDeTir() {
        return vitesseDeTir;
    }

    public void setVitesseDeTir(double vitesseDeTir) {
        this.vitesseDeTir = vitesseDeTir;
    }

    public void utiliser(Joueur joueur, Pane gamePane) {
        // A définir dans les classes filles
    }
}
