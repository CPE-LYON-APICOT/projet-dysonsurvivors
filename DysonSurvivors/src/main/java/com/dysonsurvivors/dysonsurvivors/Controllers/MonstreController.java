package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.Factory.FMonstre;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes.ChampignonHallucinogene;

import java.util.ArrayList;

import com.dysonsurvivors.dysonsurvivors.Models.LootTable;
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
        LootTable lootTable = new LootTable(gamePane);
        String sprite = "ghost_1.png"; //Par defaut
        lootTable.addItem(new ChampignonHallucinogene(), 0.3);
        for (int i = 0; i < nbMonstres; i++) {
            int niveau = (int) (Math.random() * 3) + 1+difficulty;
            int atk = 1 + difficulty;
            if (niveau > 3) {
                sprite = "ghost_"+((int) (Math.random() * 3)+1)+"_2.png"; 
            }else{
               sprite = "ghost_"+((int) (Math.random() * 3)+1)+".png"; 
            }
            
            Monstre monstre = monstreFactory.creerMonstre(niveau, atk, sprite, lootTable);
            // fais apparaitre les monstres a des positions aleatoires

            int spawnX = (int) (Math.random() * gamePane.getWidth());
            int spawnY = (int) (Math.random() * gamePane.getHeight());

            monstre.getHitbox().setLayoutX(spawnX);
            monstre.getHitbox().setLayoutY(spawnY);
            listeMonstres.add(monstre);
            gamePane.getChildren().add(monstre.getHitbox());
        }
    }

    public void tuerMonstre(Monstre monstre) {
        // Enleve le monstre de la liste de monstres et du gamePane
        monstre.die();
        gamePane.getChildren().remove(monstre.getHitbox());
        for (int i = 0; i < listeMonstres.size(); i++) {
            if (listeMonstres.get(i) == monstre) {
                listeMonstres.remove(i);
            }
        }
    }
}
