package com.dysonsurvivors.dysonsurvivors.Models;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Circle;

public class Monstre extends Personnage{
    private IDeplacementMonstre deplacement;
    private DoubleProperty radius;

    public Monstre(String nom, int pv, int pvMax, IDeplacementMonstre deplacement) {
        super(nom, pv, pvMax);
        this.deplacement = deplacement;

        this.radius = new Circle(this.hitbox.getRadius()).radiusProperty();
        this.radius.set(this.radius.get()*(0.5*stats.get(Stats.FORCE)));
        this.hitbox.radiusProperty().bind(this.radius);

    }

    private void attaquer() {
        System.out.println("Monstre attaque");
    }

    public void seDeplacer(Joueur joueur) {
        deplacement.seDeplacer(joueur,this);
    }
}