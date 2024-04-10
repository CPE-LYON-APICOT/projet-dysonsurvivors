package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Inventaire;
import com.dysonsurvivors.dysonsurvivors.Models.Objet;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class InventaireController {
    private Inventaire inventaire;
    private Pane inventairePane; // Supposons que vous utilisez un Pane pour afficher l'inventaire
    private Pane gamePane; // Supposons que vous utilisez un Pane pour afficher le jeu

    public InventaireController(Inventaire inventaire, Pane gamePane) {
        this.inventaire = inventaire;
        this.inventairePane = new Pane();
        this.gamePane = gamePane;
    }

    public void afficherInventaire() {
        inventairePane.getChildren().clear(); // Effacer le contenu précédent de l'inventaire

        double yPosition = 0; // Position verticale initiale

        // Parcourir les objets dans l'inventaire et les afficher sur l'écran de jeu
        for (int i = 0; i < inventaire.getObjets().length; i++) {
            Objet objet = inventaire.getObjets()[i];
            if (objet != null) {
                System.out.println("Affichage de l'objet: " + objet.getNom());
                // Afficher l'objet sur l'écran de jeu (par exemple, en créant des ImageView pour chaque objet et en les ajoutant à l'inventairePane)
                ImageView imageView = new ImageView(objet.getImage());
                imageView.setX(0); // Position horizontale fixe (à gauche)
                imageView.setY(yPosition); // Position verticale définie
                yPosition += 50; // Augmenter la position verticale pour l'objet suivant
                inventairePane.getChildren().add(imageView);
                /*// Redimessionner l'inventairePane pour s'adapter à la taille des objets
                inventairePane.setPrefHeight(yPosition);*/
                // Colorier l'arrière-plan de l'inventairePane
                inventairePane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
                inventairePane.setPrefSize(200, 200); // Définir la taille de l'inventairePane
                gamePane.getChildren().add(inventairePane); // Ajouter l'inventairePane au gamePane
            }
        }
    }
}
