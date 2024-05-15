package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.*;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes.Arme;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Inventaire;

import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCode;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;


public class JoueurController implements IIsHitted{
    private Joueur joueur;
    private int GAME_WIDTH;
    private int GAME_HEIGHT;
    private Pane gamePane;
    private IhandleKeyAction handleKeyAction;
    private long lastAttackTime;

    public JoueurController(int GAME_WIDTH, int GAME_HEIGHT, Pane gamePane) {
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.gamePane = gamePane;
        this.handleKeyAction = new SAzerty();
        lastAttackTime = 0;
    }

    public Joueur CreateJoueur() {
        joueur = JoueurSingleton.getInstance();
        joueur.getHitbox().setLayoutX(GAME_WIDTH / 2);
        joueur.getHitbox().setLayoutY(GAME_HEIGHT / 2);
        gamePane.getChildren().add(joueur.getHitbox());
        gamePane.getChildren().add(joueur.getLifeBarBorder());
        gamePane.getChildren().add(joueur.getLifeBarMax());
        gamePane.getChildren().add(joueur.getLifeBarCurrent());
        
        return joueur;
    }
    public Joueur getJoueur() {
        return joueur;
    }

    public void seDeplacer() {
        joueur.seDeplacer();
    }

    public void attaquer() {
        Inventaire inventaire = joueur.getInventaire();
        Objet[] objets = inventaire.getObjets();

        for (Objet objet : objets) {
            if (objet != null) {
                long currentTime = System.currentTimeMillis();
                // Vérifier si le cooldown est terminé et si l'objet est une arme
                if (objet instanceof Arme && currentTime - lastAttackTime >= joueur.getAttackCooldown()) {
                    // Mettre à jour le temps de la dernière attaque
                    lastAttackTime = currentTime;
                    ((Arme) objet).utiliser(joueur, gamePane);
                }
            }
            // TODO : pour chaque objet de l'inventaire, vérifier s'il touche un monstre

        }
    }

    public void updateCoordinatesLabel(Label coordinatesLabel) {
        double playerX = joueur.getHitbox().getLayoutX() + joueur.getHitbox().getWidth()/2;
        double playerY = joueur.getHitbox().getLayoutY() + joueur.getHitbox().getHeight()/2;
        coordinatesLabel.setText("Coordinates: (" + Math.round(playerX) + ", " +
                Math.round(playerY) + ")");
    }

    public void updateCoordinatesLife() {
        double playerX = joueur.getHitbox().getLayoutX() + joueur.getHitbox().getWidth()/2;
        double playerY = joueur.getHitbox().getLayoutY() + joueur.getHitbox().getHeight()/2;
        joueur.getLifeBarMax().setLayoutY(playerY + joueur.getHitbox().getHeight()/2+5);
        joueur.getLifeBarMax().setLayoutX(playerX - joueur.getLifeBarMax().getWidth()/2);
        joueur.getLifeBarCurrent().setLayoutY(playerY + joueur.getHitbox().getHeight()/2+5);
        joueur.getLifeBarCurrent().setLayoutX(playerX - joueur.getLifeBarMax().getWidth()/2);
        joueur.getLifeBarBorder().setLayoutY(playerY + joueur.getHitbox().getHeight()/2+3);
        joueur.getLifeBarBorder().setLayoutX(playerX - joueur.getLifeBarMax().getWidth()/2-2);
    }

    public void centerCameraOnPlayer() {
        double playerX = joueur.getHitbox().getLayoutX();
        double playerY = joueur.getHitbox().getLayoutY();

        // Calculate the position to center the camera on the player
        double cameraOffsetX = playerX - GAME_WIDTH / 2;
        double cameraOffsetY = playerY - GAME_HEIGHT / 2;

        // Translate the gamePane (your game area) by the calculated offset
        Pane gamePane = (Pane) joueur.getHitbox().getParent();
        gamePane.setTranslateX(-cameraOffsetX);
        gamePane.setTranslateY(-cameraOffsetY);
    }

    public void handleKeyPress(KeyCode code) {
        this.handleKeyAction.handleKeyPress(code);
    }

    public void handleKeyRelease(KeyCode code) {
        this.handleKeyAction.handleKeyRelease(code);
    }

    public void setHandleKeyAction(IhandleKeyAction handleKeyAction) {
        this.handleKeyAction = handleKeyAction;
    }

public void isHitted(ArrayList<Monstre> listeMonstres) {
        for (Monstre monstre : listeMonstres) {
            if (monstre != null) {
                Bounds joueurBounds = joueur.getHitbox().getBoundsInParent();
                Bounds monstreBounds = monstre.getHitbox().getBoundsInParent();
    
                // Ajuste les limites de la hitbox du joueur
                double joueurMinX = joueurBounds.getMinX() + joueurBounds.getWidth() / 2;
                double joueurMinY = joueurBounds.getMinY() + joueurBounds.getHeight() / 2;
                double joueurMaxX = joueurBounds.getMaxX() - joueurBounds.getWidth() / 2;
                double joueurMaxY = joueurBounds.getMaxY() - joueurBounds.getHeight() / 2;
    
                // Vérifie la collision ajustée
                if (joueurMinX <= monstreBounds.getMaxX() && joueurMaxX >= monstreBounds.getMinX() &&
                    joueurMinY <= monstreBounds.getMaxY() && joueurMaxY >= monstreBounds.getMinY()) {
                    joueur.perdreVie(monstre.getAttaque());
                }
            }
        }
    }

    public void setAttackCooldown(long attackCooldown) {
        this.joueur.setAttackCooldown(attackCooldown);
    }

}
