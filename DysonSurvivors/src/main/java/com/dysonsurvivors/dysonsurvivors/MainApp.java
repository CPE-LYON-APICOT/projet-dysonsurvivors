package com.dysonsurvivors.dysonsurvivors;

import com.dysonsurvivors.dysonsurvivors.Controllers.InventaireController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

import com.dysonsurvivors.dysonsurvivors.Controllers.JoueurController;
import com.dysonsurvivors.dysonsurvivors.Controllers.MonstreController;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes.ChampignonHallucinogene;
import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;
import com.dysonsurvivors.dysonsurvivors.Models.Objet;
import com.dysonsurvivors.dysonsurvivors.Controllers.ParamController;
import com.dysonsurvivors.dysonsurvivors.Controllers.SoundController;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class MainApp extends Application {
    private static int GAME_WIDTH = 960;
    private static int GAME_HEIGHT = 540;
    private Label coordinatesLabel;
    private MonstreController monstreController;
    private ArrayList<Monstre> listeMonstres;
    private int nbMonstresMax;
    private JoueurController joueurController;
    private Joueur joueur;
    private InventaireController inventaireController;
    private ParamController paramController;
    private SoundController soundController;
    private Timeline monsterSpawnTimeline;
    private int compteurDifficulty = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /* Musique */
        soundController = new SoundController();

        // Ajouter un gestionnaire d'événements pour fermer la fenêtre
        primaryStage.setOnCloseRequest(event -> {
            // Arrêter l'ensemble de l'application JavaFX
            System.exit(0);
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game");

        Pane gamePane = (Pane) root.lookup("#gamePane");

        coordinatesLabel = (Label) loader.getNamespace().get("coordinatesLabel");


        // Creation du joueur
        joueurController = new JoueurController(GAME_WIDTH, GAME_HEIGHT, gamePane);
        joueur = joueurController.CreateJoueur();

        paramController = new ParamController(gamePane, joueurController, soundController);

        // Creation des monstres
        // nbMonstresMax = 100;
        listeMonstres = new ArrayList<>();
        monstreController = new MonstreController(listeMonstres, GAME_WIDTH, GAME_HEIGHT, gamePane);
        // monstreController.creerMonstre(20);

        // Création d'objets pour l'ajouter à l'inventaire*
        for (int i = 1; i < 5; i++) {
            Objet objet = new ChampignonHallucinogene();
            joueur.getInventaire().ajouterObjet(objet);
        }

        inventaireController = new InventaireController(joueur.getInventaire(), paramController.getParamPane());
        inventaireController.afficherInventaire();

        // Event handlers for key presses and releases
        scene.setOnKeyPressed(event -> {
            joueurController.handleKeyPress(event.getCode());
            paramController.handleKeyPress(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            joueurController.handleKeyRelease(event.getCode());
        });

        
        // Start the game loop
        startGameLoop();
        startMonsterSpawnTimer();
        primaryStage.show();
    }


    private void startMonsterSpawnTimer() {
       
        monsterSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            if (!paramController.getIsActive()) {
                createNewMonsterWave((int) (Math.random() * 3) + compteurDifficulty);
                compteurDifficulty++;
            }
        }));
        
        monsterSpawnTimeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
        monsterSpawnTimeline.play();
    }

    private void createNewMonsterWave(int difficulty) {
        int nbMonstres = (int) (Math.random() * 5) + 1*difficulty;
        monstreController.creerMonstre(10+nbMonstres,difficulty);
    }

    private void startGameLoop() {
        final long startNanoTime = System.nanoTime();
        final double desiredFPS = 120.0; // Nombre de frames par seconde désiré
        final double desiredFrameTime = 1.0 / desiredFPS; // Temps entre chaque frame en secondes

        new AnimationTimer() {
            private long lastUpdate = startNanoTime;

            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastUpdate) / 1e9; // Temps écoulé depuis la dernière frame en secondes

                if (elapsedTime > desiredFrameTime) {
                    lastUpdate = currentNanoTime;

                    if (!paramController.getIsActive()) {
                        update();
                    }
                }
            }
        }.start();
    }

    private void update() {
        // Deplacement du joueur
        joueurController.seDeplacer();
        joueurController.isHitted(listeMonstres);
        // Met a jour les coordonnees du joueur
        joueurController.updateCoordinatesLabel(coordinatesLabel);
        joueurController.updateCoordinatesLife();
        // Centre la camera sur le joueur
        joueurController.centerCameraOnPlayer();
        // Fait attaquer le joueur
        joueurController.attaquer();

        // Fait se deplacer les monstres de la liste
        for (Monstre monstre : listeMonstres) {
            if (monstre != null) {
                monstre.seDeplacer(joueurController.getJoueur());
            }
        }
        // Met a jour la position de l'inventaire
        /*inventaireController.updateInventoryPosition();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}