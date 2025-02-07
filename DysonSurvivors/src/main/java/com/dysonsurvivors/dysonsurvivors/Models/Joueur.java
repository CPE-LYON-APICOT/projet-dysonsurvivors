package com.dysonsurvivors.dysonsurvivors.Models;

import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Inventaire;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes.Bulle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class Joueur extends Personnage{
    private Inventaire inventaire;
    private int exp;
    private int expMax;
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
    private Rectangle expMaxBar;
    private Rectangle expCurrentBar;
    private Rectangle expBorder;
    private double speed;
    private double orientation;
    private long attackCooldown;

    // Gestion de l'invincibilité du joueur pour ne pas qu'il meurt trop vite
    private boolean invincible;
    private Timeline invincibleTimeline;


    public Joueur(String nom, int pvMax) {
        super(nom, pvMax);
        speed = 1;
        attackCooldown = 100; // millisecondes
        chargerSprite();
        initAnimationTimeline();

        // Barre de vie
        lifeBarMax = new Rectangle(0, 0, pvMax/2, 3);
        lifeBarCurrent = new Rectangle(0, 0, pv/2, 3);
        lifeBarBorder = new Rectangle(0, 0, pv/2+4, 3+4);
        lifeBarMax.setFill(Color.BLACK);
        lifeBarCurrent.setFill(Color.RED);
        lifeBarBorder.setFill(Color.GOLD);

        // Barre d'expérience
        exp = 0;
        expMax = 100;
        niveau = 1;
        expMaxBar = new Rectangle(0, 0, 0, 3);
        expCurrentBar = new Rectangle(0, 0, 0, 3);
        expBorder = new Rectangle(0, 0, 0, 3+4);
        expMaxBar.setFill(Color.BLACK);
        expCurrentBar.setFill(Color.LIGHTBLUE);
        expBorder.setFill(Color.GOLD);

        // Stats par défaut
        stats.put(Stats.FORCE, 5);
        stats.put(Stats.PERCEPTION, 3);
        stats.put(Stats.ENDURANCE, 4);
        stats.put(Stats.CHARISME, 10);
        stats.put(Stats.INTELLIGENCE, 1);
        stats.put(Stats.AGILITE, 6);
        stats.put(Stats.CHANCE, 1);

        inventaire = new Inventaire();
        Objet objet = new Bulle();
        this.getInventaire().ajouterObjet(objet);

        this.invincible = false; // Le joueur n'est pas invincible au début
        initInvincibleTimeline(); // Initialisez la timeline d'invincibilité
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAttackCooldown(long attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public long getAttackCooldown() {
        return attackCooldown;
    }

    public void seDeplacer() {
        double dx = 0, dy = 0;
        if (upPressed) dy -= speed;
        if (downPressed) dy += speed;
        if (leftPressed) dx -= speed;
        if (rightPressed) dx += speed;

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
            // Definir l'orientation : Convertir l'angle en radians
            orientation = 180;
        } else if (rightPressed) {
            startAnimation(); // Exemple: Frame de marche vers la droite
            orientation = 0;
        } else if (upPressed) {
            startAnimation(); // Exemple: Frame de marche vers le haut
            orientation = 270;
        } else if (downPressed) {
            startAnimation(); // Exemple: Frame de marche vers le bas
            orientation = 90;
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

    public Rectangle getExpMaxBar() {
        return expMaxBar;
    }

    public Rectangle getExpCurrentBar() {
        return expCurrentBar;
    }

    public void setExpCurrentBar(Rectangle expCurrentBar) {
        this.expCurrentBar = expCurrentBar;
    }

    public Rectangle getExpBorder() {
        return expBorder;
    }

    public void setExpBorder(Rectangle expBorder) {
        this.expBorder = expBorder;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExpMax() {
        return expMax;
    }

    public void setExpMax(int expMax) {
        this.expMax = expMax;
    }

    public void gagnerExp(int exp) {
        this.exp += exp;
        if (this.exp >= expMax) {
            this.exp -= expMax;
            expMax += 50;
            this.gagnerUnNiveau();
        }
        expMaxBar.setWidth(expMax);
        expCurrentBar.setWidth(this.exp);
    }

    public int getNiveau() {
        return niveau;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void perdreVie(int degats) {
        if (!invincible) { // Vérifiez si le joueur est invincible
            super.perdreVie(degats);
            lifeBarCurrent.setWidth(pv / 2);
            // Démarrez la période d'invincibilité
            demarrerInvincibilite();
        }
    }

    private void demarrerInvincibilite() {
        invincible = true; // Le joueur devient invincible
        invincibleTimeline.play(); // Démarrez la timeline
    }

    private void initInvincibleTimeline() {
        // Créez une KeyFrame pour désactiver l'invincibilité après un certain délai
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), event -> {
            invincible = false; // Le joueur n'est plus invincible après 2 secondes
            invincibleTimeline.stop(); // Arrêtez la timeline
        });

        // Créez la timeline avec la KeyFrame
        invincibleTimeline = new Timeline(keyFrame);
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public void gagnerUnNiveau() {
        niveau++;

        // Augmenter les stats du joueur
        stats.put(Stats.FORCE, stats.get(Stats.FORCE) + 2);
        stats.put(Stats.PERCEPTION, stats.get(Stats.PERCEPTION) + 1);
        stats.put(Stats.ENDURANCE, stats.get(Stats.ENDURANCE) + 1);
        stats.put(Stats.CHARISME, stats.get(Stats.CHARISME) + 1);
        stats.put(Stats.INTELLIGENCE, stats.get(Stats.INTELLIGENCE) + 1);
        stats.put(Stats.AGILITE, stats.get(Stats.AGILITE) + 1);
        stats.put(Stats.CHANCE, stats.get(Stats.CHANCE) + 1);

        // Augmenter les PV max du joueur
        pvMax += 5;
        pv = pvMax;
        lifeBarMax.setWidth(pv / 2);
        lifeBarCurrent.setWidth(pv / 2);
        lifeBarBorder.setWidth(pv / 2 + 4);
    }
}
