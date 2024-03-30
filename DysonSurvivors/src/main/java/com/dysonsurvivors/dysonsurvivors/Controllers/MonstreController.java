package com.dysonsurvivors.dysonsurvivors.Controllers;

import javafx.scene.layout.Pane;

import com.dysonsurvivors.dysonsurvivors.Models.Factory.FMonstre;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;

public class MonstreController {

    private int GAME_WIDTH;
    private int GAME_HEIGHT;
    private Monstre[] listeMonstres;
    private Pane gamePane;

    public MonstreController(Monstre[] listeMonstre, int GAME_WIDTH, int GAME_HEIGHT, Pane gamePane) {
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.listeMonstres = listeMonstre;
        this.gamePane = gamePane;
    }

    public void creerMonstre(int nbMonstres) {
        FMonstre monstreFactory = new FMonstre();

        // Boucle qui fait des monstres et les ajoute a une liste de monstres:
        for (int i = 0; i < nbMonstres; i++) {
            // Si la liste de monstres n'est pas pleine, on ajoute un monstre
            if (listeMonstres[i] == null) {
                Monstre monstre = monstreFactory.creerMonstre((int) (Math.random() * 3) + 1);
                // fais apparaitre les monstres a des positions aleatoires
                monstre.getHitbox().setLayoutX((int) (Math.random() * GAME_WIDTH));
                monstre.getHitbox().setLayoutY((int) (Math.random() * GAME_HEIGHT));
                listeMonstres[i] = monstre;
                gamePane.getChildren().add(monstre.getHitbox());
            }
        }
    }

    public void tuerMonstre(Monstre monstre) {
        // Enleve le monstre de la liste de monstres et de la gamePane
        gamePane.getChildren().remove(monstre.getHitbox());
        for (int i = 0; i < listeMonstres.length; i++) {
            if (listeMonstres[i] == monstre) {
                listeMonstres[i] = null;
            }
        }
    }
}
