package com.dysonsurvivors.dysonsurvivors.Models;

import java.util.Objects;
import java.util.Set;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Joueur extends Personnage{
    private Set<Objet> inventaire;
    private int XP;
    private int niveau;
    private ImageView sprite;
    // Déclarer une timeline pour l'animation
    private Timeline animationTimeline;
    // Déclarer une variable pour suivre la frame actuelle de l'animation
    private int currentFrame = 0;
    // Déclarer les variables pour suivre l'état des touches de déplacement
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private Rectangle lifeBarMax;
    private Rectangle lifeBarCurrent;
    private Rectangle lifeBarBorder;


    public Joueur(String nom, int pvMax) {
        super(nom, pvMax);
        chargerSprite();
        initAnimationTimeline();
        lifeBarMax = new Rectangle(0, 0, pvMax/2, 3);
        lifeBarCurrent = new Rectangle(0, 0, pv/2, 3);
        lifeBarBorder = new Rectangle(0, 0, pv/2+4, 3+4);
        lifeBarMax.setFill(Color.BLACK);
        lifeBarCurrent.setFill(Color.RED);
        lifeBarBorder.setFill(Color.GOLD);
    }

    // Méthodes pour mettre à jour l'état des touches de déplacement
    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void attaquer() {
        System.out.println("Joueur attaque");
    }

    public void seDeplacer() {
        double dx = 0, dy = 0;
        if (upPressed) dy -= 1;
        if (downPressed) dy += 1;
        if (leftPressed) dx -= 1;
        if (rightPressed) dx += 1;

        // Obtenir les coordonnées du centre de la hitbox
        double centerX = this.getHitbox().getLayoutX() + this.getHitbox().getWidth() / 2;
        double centerY = this.getHitbox().getLayoutY() + this.getHitbox().getHeight() / 2;

        // Appliquer le déplacement au centre du joueur
        double newCenterX = centerX + dx;
        double newCenterY = centerY + dy;

        // Définir les nouvelles coordonnées du coin supérieur gauche de la hitbox
        double newLayoutX = newCenterX - this.getHitbox().getWidth() / 2;
        double newLayoutY = newCenterY - this.getHitbox().getHeight() / 2;

        // Mettre à jour les coordonnées de la hitbox
        this.getHitbox().setLayoutX(newLayoutX);
        this.getHitbox().setLayoutY(newLayoutY);

        // Changer la frame de l'animation en fonction de la direction du déplacement
        if (leftPressed) {
            startAnimation(); // Exemple: Frame de marche vers la gauche
        } else if (rightPressed) {
            startAnimation(); // Exemple: Frame de marche vers la droite
        } else if (upPressed) {
            startAnimation(); // Exemple: Frame de marche vers le haut
        } else if (downPressed) {
            startAnimation(); // Exemple: Frame de marche vers le bas
        } else {
            stopAnimation(); // Exemple: Frame de repos
        }

        // affiche les coordonnées du joueur
        /*System.out.println("x: " + newLayoutX + " y: " + newLayoutY);*/
    }

    private void initAnimationTimeline() {
        // Créer une KeyFrame pour l'animation
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            // Changer la frame de l'animation en fonction de la direction du déplacement
            if (leftPressed) {
                changerFrameAnimation(currentFrame, 1); // Frame de marche vers la gauche
            } else if (rightPressed) {
                changerFrameAnimation(currentFrame, 2); // Frame de marche vers la droite
            } else if (upPressed) {
                changerFrameAnimation(currentFrame, 3); // Frame de marche vers le haut
            } else if (downPressed) {
                changerFrameAnimation(currentFrame, 0); // Frame de marche vers le bas
            } else {
                changerFrameAnimation(currentFrame, 0); // Frame de repos
            }

            // Incrémenter la frame actuelle
            currentFrame = (currentFrame + 1) % 4; // 4 est le nombre total de frames dans la sprite sheet
        });

        // Créer la timeline avec la KeyFrame
        animationTimeline = new Timeline(keyFrame);
        animationTimeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment l'animation
    }

    public void startAnimation() {
        // Démarrer l'animation
        animationTimeline.play();
    }

    public void stopAnimation() {
        // Arrêter l'animation
        animationTimeline.stop();
    }

    private void chargerSprite() {
        // Charger la sprite sheet
        Image spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream("grenouille.png")));

        // Créer une ImageView pour afficher la sprite animée
        sprite = new ImageView(spriteSheet);

        // Définir les coordonnées et la taille de la première frame
        sprite.setViewport(new Rectangle2D(0, 0, 64, 64));

        // Ajouter l'ImageView à une Pane
        Pane joueurPane = new Pane(sprite);
        hitbox = joueurPane;
        // Définir la taille de la hitbox
        hitbox.setPrefSize(64, 64);
        /*hitbox.setStyle("-fx-background-color: blue;");*/
    }

    // Méthode pour changer la frame de l'animation
    private void changerFrameAnimation(int frameX, int frameY) {
        sprite.setViewport(new Rectangle2D(frameX * 64, frameY * 64, 64, 64));
    }

    public Rectangle getLifeBarMax() {
        return lifeBarMax;
    }

    public Rectangle getLifeBarCurrent() {
        return lifeBarCurrent;
    }

    public void setLifeBarCurrent(Rectangle lifeBarCurrent) {
        this.lifeBarCurrent = lifeBarCurrent;
    }

    public void setLifeBarMax(Rectangle lifeBarMax) {
        this.lifeBarMax = lifeBarMax;
    }

    public Rectangle getLifeBarBorder() {
        return lifeBarBorder;
    }

    public void setLifeBarBorder(Rectangle lifeBarBorder) {
        this.lifeBarCurrent = lifeBarBorder;
    }
}
