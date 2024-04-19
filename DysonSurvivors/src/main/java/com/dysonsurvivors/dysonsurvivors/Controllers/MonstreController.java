package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.Factory.FMonstre;

import java.util.ArrayList;

import com.dysonsurvivors.dysonsurvivors.Models.Monstre;
import javafx.scene.layout.Pane;

public class MonstreController {

    private int GAME_WIDTH;
    private int GAME_HEIGHT;
    private ArrayList<Monstre> listeMonstres;
    private Pane gamePane;
    FMonstre monstreFactory;

    public MonstreController(ArrayList<Monstre> listeMonstre, int GAME_WIDTH, int GAME_HEIGHT, Pane gamePane) {
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.listeMonstres = listeMonstre;
        this.gamePane = gamePane;
        this.monstreFactory = new FMonstre();
    }

    public void creerMonstre(int nbMonstres,int difficulty) {
        
        // Boucle qui fait des monstres et les ajoute a une liste de monstres:
        System.out.println("nbMonstres: " + nbMonstres);
        for (int i = 0; i < nbMonstres; i++) {
            int niveau = (int) (Math.random() * 3) + 1+difficulty;
            int atk = 1 + difficulty;

            System.out.println("Niveau: " + niveau + " Atk: " + atk);
            Monstre monstre = monstreFactory.creerMonstre(niveau ,atk);
            // fais apparaitre les monstres a des positions aleatoires

            int spawnX = (int) (Math.random() * gamePane.getWidth());
            int spawnY = (int) (Math.random() * gamePane.getHeight());

            if (spawnX < gamePane.getWidth()/2 + 100 && spawnX > gamePane.getWidth()/2 - 100) {
                if (spawnY < gamePane.getHeight()/2 + 100 && spawnY > gamePane.getHeight()/2 - 100) {
                    spawnX += 100;
                    spawnY += 100;
                }
            }


            monstre.getHitbox().setLayoutX(spawnX);
            monstre.getHitbox().setLayoutY(spawnY);
            listeMonstres.add(monstre);
            gamePane.getChildren().add(monstre.getHitbox());
        }
    }

    public void tuerMonstre(Monstre monstre) {
        // Enleve le monstre de la liste de monstres et de la gamePane
        gamePane.getChildren().remove(monstre.getHitbox());
        for (int i = 0; i < listeMonstres.size(); i++) {
            if (listeMonstres.get(i) == monstre) {
                listeMonstres.remove(i);
            }
        }
    }
}
