package com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes;

import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Equipement;
import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Arme extends Equipement {
    private double degats;
    private double portee;
    private double vitesseDeTir;
    private double hauteurHitbox;
    private double largeurHitbox;

    private Image attaqueImage;
    private ImageView attaqueImageView;

    public Arme(String nom, String description, String nomImage) {
        super(nom, description, nomImage);
    }

    public int getDegats() {
        return (int) degats;
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

    public Image getAttaqueImage() {
        return attaqueImage;
    }

    public void setAttaqueImage(Image attaqueImage) {
        this.attaqueImage = attaqueImage;
    }

    public ImageView getAttaqueImageView() {
        return attaqueImageView;
    }

    public void setAttaqueImageView(ImageView attaqueImageView) {
        this.attaqueImageView = attaqueImageView;
    }

    public double getHauteurHitbox() {
        return hauteurHitbox;
    }

    public void setHauteurHitbox(double hauteurHitbox) {
        this.hauteurHitbox = hauteurHitbox;
    }

    public double getLargeurHitbox() {
        return largeurHitbox;
    }

    public void setLargeurHitbox(double largeurHitbox) {
        this.largeurHitbox = largeurHitbox;
    }

    public void utiliser(Joueur joueur, Pane gamePane, ArrayList<Monstre> listeMonstres) {
        // A définir dans les classes filles
    }
}
