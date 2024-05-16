package com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes;

import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.Stats;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

import com.dysonsurvivors.dysonsurvivors.Models.Monstre;

public class Bulle extends Arme{

    public Bulle() {
        super("Bulle", "Une super bulle qui mouille super bien !", "Mushrooms/1.png");  // Mushrooms/1.png pour le champignon
        this.setDegats(5);
        this.setPortee(100); // En pixels
        this.setVitesseDeTir(0.3); // En ms
        // Créer un nouvel élément graphique pour l'attaque
        this.setHauteurHitbox(20);
        this.setLargeurHitbox(20);
        this.setAttaqueImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(this.getNomImage()))));
        this.setAttaqueImageView(new ImageView(this.getAttaqueImage()));
        this.getAttaqueImageView().setFitWidth(this.getLargeurHitbox());
        this.getAttaqueImageView().setFitHeight(this.getHauteurHitbox());

    }

    private boolean attaqueEnCours = false;

    @Override
    public void utiliser(Joueur joueur, Pane gamePane, ArrayList<Monstre> listeMonstres) {
        if (!attaqueEnCours) {
            // Création du nœud représentant l'attaque
            ImageView attaqueImageView = this.getAttaqueImageView();
            Pane attaquePane = new Pane(attaqueImageView);

            // Positionner l'attaque à côté du joueur dans la direction où il regarde
            double attaqueX = joueur.getHitbox().getLayoutX() + joueur.getHitbox().getWidth() / 2 - 10;
            double attaqueY = joueur.getHitbox().getLayoutY() + joueur.getHitbox().getHeight() / 2;
            attaquePane.setLayoutX(attaqueX);
            attaquePane.setLayoutY(attaqueY);

            // Ajouter l'attaque au gamePane
            gamePane.getChildren().add(attaquePane);

            // Ajouter l'attaque en cours à la liste des attaques en cours
            attaqueEnCours = true;

            // Calculer les composantes x et y du vecteur de direction en fonction de l'angle d'orientation du joueur
            double directionX = Math.cos(Math.toRadians(joueur.getOrientation())) * this.getPortee();
            double directionY = Math.sin(Math.toRadians(joueur.getOrientation())) * this.getPortee();

            // Créer une transition pour animer l'attaque
            TranslateTransition attaqueTransition = new TranslateTransition(Duration.seconds(this.getVitesseDeTir()), attaquePane);
            attaqueTransition.setByX(directionX);
            attaqueTransition.setByY(directionY);

            // Actualiser les limites de collision de l'attaque à chaque image pendant son déplacement
            attaqueTransition.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                Bounds attaqueBounds = attaquePane.localToScene(attaquePane.getBoundsInLocal());

                // Si la liste de monstres n'est pas vide
                if (listeMonstres.size() > 0) {
                    for (Monstre monstre : listeMonstres) {
                        Bounds monstreBounds = monstre.getHitbox().localToScene(monstre.getHitbox().getBoundsInLocal());
                        if (attaqueBounds.intersects(monstreBounds)) {
                            // Collision détectée, appliquer les dégâts au monstre (dégats de l'arme + stat de force du joueur)
                            monstre.prendreDegats(this.getDegats() + joueur.getStat(Stats.FORCE));

                            // Calculer le vecteur de recul en fonction de la direction du projectile
                            double reculX = directionX / 5; // Diviser par 5 pour un recul plus lent
                            double reculY = directionY / 5; // Diviser par 5 pour un recul plus lent

                            // Appliquer le recul au monstre
                            TranslateTransition reculTransition = new TranslateTransition(Duration.seconds(0.1), monstre.getHitbox());
                            reculTransition.setByX(reculX);
                            reculTransition.setByY(reculY);
                            reculTransition.play();

                            // Supprimer l'attaque une fois qu'elle a touché un monstre
                            gamePane.getChildren().remove(attaquePane);
                            /*attaqueEnCours = false;*/

                            // Afficher les points de vie du monstre
                            /*System.out.println("PV du monstre : " + monstre.getPv());*/
                            break; // Sortir de la boucle dès qu'une collision est détectée
                        }
                    }
                }
            });

            // Gérer la fin de la transition
            attaqueTransition.setOnFinished(event -> {
                // Supprimer l'attaque une fois terminée
                gamePane.getChildren().remove(attaquePane);
                // Mettre à jour l'état de l'attaque en cours
                attaqueEnCours = false;
            });

            attaqueTransition.play();
        }
    }

}
