package com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes;

import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class ChampignonHallucinogene extends Arme{

    public ChampignonHallucinogene() {
        super("Champignon Hallucinogene", "Un champignon hallucinogène qui vous permettra de vous échapper de la réalité", "Mushrooms/1.png");
        this.setDegats(5);
        this.setPortee(100); // En pixels
        this.setVitesseDeTir(0.5); // En ms
        // Créer un nouvel élément graphique pour l'attaque
        this.setHauteurHitbox(20);
        this.setLargeurHitbox(20);
        this.setAttaqueImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(this.getNomImage()))));
        this.setAttaqueImageView(new ImageView(this.getAttaqueImage()));
        this.getAttaqueImageView().setFitWidth(this.getLargeurHitbox());
        this.getAttaqueImageView().setFitHeight(this.getHauteurHitbox());
    }

    // Créer un Pane pour l'attaque et l'ajouter au gamePane
    @Override
    public void utiliser(Joueur joueur, Pane gamePane) {

        Pane attaquePane = new Pane(this.getAttaqueImageView());

        // Positionner l'attaque à côté du joueur dans la direction où il regarde
        double attaqueX = joueur.getHitbox().getLayoutX() + joueur.getHitbox().getWidth()/2;
        double attaqueY = joueur.getHitbox().getLayoutY() + joueur.getHitbox().getHeight()/2;
        attaquePane.setLayoutX(attaqueX);
        attaquePane.setLayoutY(attaqueY);

        // Ajouter l'attaque au gamePane
        gamePane.getChildren().add(attaquePane);

        // Calculer les composantes x et y du vecteur de direction en fonction de l'angle d'orientation du joueur
        double directionX = Math.cos(Math.toRadians(joueur.getOrientation())) * this.getPortee();
        double directionY = Math.sin(Math.toRadians(joueur.getOrientation())) * this.getPortee();

        // Créer une transition pour animer l'attaque
        TranslateTransition attaqueTransition = new TranslateTransition(Duration.seconds(this.getVitesseDeTir()), attaquePane);
        attaqueTransition.setByX(directionX);
        attaqueTransition.setByY(directionY);
        attaqueTransition.setOnFinished(event -> {
            // Supprimer l'attaque une fois terminée
            gamePane.getChildren().remove(attaquePane);
        });
        attaqueTransition.play();
    }
}
