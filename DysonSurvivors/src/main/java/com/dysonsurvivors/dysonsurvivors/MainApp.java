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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainApp extends Application {
    private static int GAME_WIDTH = 960;
    private static int GAME_HEIGHT = 540;
    private Label coordinatesLabel;
    private MonstreController monstreController;
    private Monstre[] listeMonstres;
    private int nbMonstresMax;
    private JoueurController joueurController;
    private Joueur joueur;
    private InventaireController inventaireController;
    private ParamController paramController;
    private SoundController soundController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /* Musique */
        // String musicFile = "src/main/resources/com/dysonsurvivors/dysonsurvivors/Music/pdm-soundtrack.mp3";
        // Créer un objet Media avec le fichier audio
        // Media sound = new Media(new File(musicFile).toURI().toString());
        soundController = new SoundController();


        // Facultatif : mettre en pause la musique après un certain temps
        // mediaPlayer.setOnPlaying(() -> {
        //     new java.util.Timer().schedule(
        //             new java.util.TimerTask() {
        //                 @Override
        //                 public void run() {
        //                     mediaPlayer.pause();
        //                 }
        //             },
        //             30000 // Durée en millisecondes (30 secondes)
        //     );
        // });

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
        nbMonstresMax = 20;
        listeMonstres = new Monstre[nbMonstresMax];
        monstreController = new MonstreController(listeMonstres, GAME_WIDTH, GAME_HEIGHT, gamePane);
        monstreController.creerMonstre(20);

        // Création d'objets pour l'ajouter à l'inventaire*
        for (int i = 1; i < 5; i++) {
            Objet objet = new ChampignonHallucinogene("Champi Champlax", "Oeeeeee!", "Mushrooms/" + i + ".png");
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

        primaryStage.show();
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