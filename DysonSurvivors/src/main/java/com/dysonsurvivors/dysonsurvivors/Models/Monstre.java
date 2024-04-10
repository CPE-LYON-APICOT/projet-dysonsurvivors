package com.dysonsurvivors.dysonsurvivors.Models;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Objects;

public class Monstre extends Personnage{
    private IDeplacementMonstre deplacement;
    private DoubleProperty radius;
    private ImageView sprite;
    // Déclarer une timeline pour l'animation
    private Timeline animationTimeline;
    private int currentFrame = 0;
    private int oldX = 0;
    private int oldY = 0;

    public Monstre(String nom, int pvMax, IDeplacementMonstre deplacement) {
        super(nom, pvMax);
        this.deplacement = deplacement;
        chargerSprite(); // Charger la sprite du monstre
        initAnimationTimeline();
        startAnimation();
    }

    // private void chargerSprite() {
    //     // Créer une Pane pour le monstre
    //     Pane monstrePane = new Pane();
    //     monstrePane.setPrefSize(30, 30); // Définir la taille du Pane du monstre (à adapter)
    //     monstrePane.setStyle("-fx-background-color: red;"); // Style du Pane du monstre

    //     // Ajouter la Pane du monstre à la hitbox
    //     hitbox = monstrePane;
    // }

    private void chargerSprite() {
        // Charger la sprite sheet
        Image spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ghost.png")));

        // Créer une ImageView pour afficher la sprite animée
        sprite = new ImageView(spriteSheet);

        // Définir les coordonnées et la taille de la première frame
        sprite.setViewport(new Rectangle2D(0, 0, 48, 48));

        // Ajouter l'ImageView à une Pane
        Pane MonstrePane = new Pane(sprite);
        hitbox = MonstrePane;
        // Définir la taille de la hitbox
        hitbox.setPrefSize(48, 48);
        /*hitbox.setStyle("-fx-background-color: blue;");*/
    }

    private void attaquer() {
        System.out.println("Monstre attaque");
    }
    public void seDeplacer(Joueur joueur) {
        deplacement.seDeplacer(joueur,this);
    }

    private void initAnimationTimeline() {
        // Créer une KeyFrame pour l'animation
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            // Changer la frame de l'animation en fonction de la direction du déplacement
            if (this.getHitbox().getLayoutY() - oldY < 0) {
                oldY = (int) this.getHitbox().getLayoutY();
                changerFrameAnimation(currentFrame, 3); // Frame de marche vers la gauche
            } else if (this.getHitbox().getLayoutY() - oldY > 0) {
                oldY = (int) this.getHitbox().getLayoutY();
                changerFrameAnimation(currentFrame, 0); // Frame de marche vers le bas
            } else if (this.getHitbox().getLayoutX() - oldX < 0) {
                oldX = (int) this.getHitbox().getLayoutX();
                changerFrameAnimation(currentFrame, 1); // Frame de marche vers la droite
            } else if (this.getHitbox().getLayoutX() - oldX  > 0) {
                oldX = (int) this.getHitbox().getLayoutX();
                changerFrameAnimation(currentFrame, 2); // Frame de marche vers le haut
            } else {
                changerFrameAnimation(currentFrame, 0); // Frame de repos
            }

            // Incrémenter la frame actuelle
            currentFrame = (currentFrame + 1) % 3; // 4 est le nombre total de frames dans la sprite sheet
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

    private void changerFrameAnimation(int frameX, int frameY) {
        sprite.setViewport(new Rectangle2D(frameX * 48, frameY * 48, 48, 48));
    }
}