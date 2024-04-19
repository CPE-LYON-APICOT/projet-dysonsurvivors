package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.IhandleKeyAction;
import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.JoueurSingleton;
import com.dysonsurvivors.dysonsurvivors.Models.SAzerty;
import com.dysonsurvivors.dysonsurvivors.Models.IIsHitted;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;

import javafx.scene.input.KeyCode;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class JoueurController implements IIsHitted{
    private Joueur joueur;
    private int GAME_WIDTH;
    private int GAME_HEIGHT;
    private Pane gamePane;
    private IhandleKeyAction handleKeyAction;

    public JoueurController(int GAME_WIDTH, int GAME_HEIGHT, Pane gamePane) {
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.gamePane = gamePane;
        this.handleKeyAction = new SAzerty();
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
        joueur.attaquer();
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

    public void isHitted(Monstre[] listeMonstres) {
        // Vérifie si un monstre du gamepan est en collision avec le joueur
        for (Monstre monstre : listeMonstres) {
            if (monstre != null) {
                if (joueur.getHitbox().getBoundsInParent().intersects(monstre.getHitbox().getBoundsInParent())) {
                    joueur.perdreVie(monstre.getAttaque());
                }
            }
        }
    }

}
