package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Inventaire;
import com.dysonsurvivors.dysonsurvivors.Models.Objet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class InventaireController {
    private Inventaire inventaire;
    private Pane inventairePane;
    private Pane gamePane;

    public InventaireController(Inventaire inventaire, Pane gamePane) {
        this.inventaire = inventaire;
        this.inventairePane = new Pane();
        this.gamePane = gamePane;
        gamePane.getChildren().add(inventairePane);

    }

    public void afficherInventaire() {
        inventairePane.getChildren().clear(); // Effacer le contenu précédent de l'inventaire
        inventairePane.getStyleClass().add("inventaire-pane");
        inventairePane.setLayoutX(-gamePane.getLayoutX());
        inventairePane.setLayoutY(-gamePane.getLayoutY());

        // Parcourir les objets dans l'inventaire et les afficher sur l'écran de jeu
        Objet[] objets = inventaire.getObjets(); // Récupérer les objets de l'inventaire
        double x = 0; // Position x initiale
        double y = 0; // Position y initiale
        double itemWidth = 30; // Largeur de l'objet
        double itemHeight = 30; // Hauteur de l'objet

        for (Objet objet : objets) {
            if (objet != null) {
                // Charger l'image de l'objet
                Image image = chargerSprite(objet.getNomImage());
                // Créer une ImageView pour l'objet
                ImageView imageView = new ImageView(image);
                // Ajouter l'objet à l'inventairePane
                inventairePane.getChildren().add(imageView);
                // Définir la position de l'objet
                imageView.setLayoutX(x + 5);
                imageView.setLayoutY(y + 5);
                // Définir la taille de l'objet
                imageView.setFitWidth(itemWidth);
                imageView.setFitHeight(itemHeight);
                // Mettre à jour les coordonnées pour le prochain objet
                x += itemWidth; // Décaler horizontalement
                if (x >= inventairePane.getWidth()) {
                    x = 0; // Revenir à la ligne si l'objet dépasse la largeur de l'inventairePane
                    y += itemHeight; // Décaler verticalement
                }
            }
        }
    }

    private Image chargerSprite(String nomImage) {
        // Charger l'image depuis le fichier
        return new Image(getClass().getResourceAsStream(nomImage));
    }

}

