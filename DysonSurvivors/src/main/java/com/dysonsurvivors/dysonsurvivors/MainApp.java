package com.dysonsurvivors.dysonsurvivors;

import com.dysonsurvivors.dysonsurvivors.Controllers.InventaireController;
import com.dysonsurvivors.dysonsurvivors.Controllers.JoueurController;
import com.dysonsurvivors.dysonsurvivors.Controllers.MonstreController;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes.ChampignonHallucinogene;
import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;
import com.dysonsurvivors.dysonsurvivors.Models.Objet;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    private static int GAME_WIDTH = 1024;
    private static int GAME_HEIGHT = 600;
    private Label coordinatesLabel;
    private MonstreController monstreController;
    private Monstre[] listeMonstres;
    private int nbMonstresMax;
    private JoueurController joueurController;
    private Joueur joueur;
    private InventaireController inventaireController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /* Musique */
        String musicFile = "src/main/resources/com/dysonsurvivors/dysonsurvivors/music/pdm-soundtrack.mp3";
        // Créer un objet Media avec le fichier audio
        Media sound = new Media(new File(musicFile).toURI().toString());
        // Créer un lecteur audio
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        // Configurer le lecteur audio pour qu'il joue en boucle
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        // Démarrer la lecture de la musique de fond
        mediaPlayer.play();

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

        // Création d'un objet pour l'ajouter à l'inventaire*
        Objet objet = new ChampignonHallucinogene("Champi Champlax", "Oeeeeee!", "1.png");
        joueur.getInventaire().ajouterObjet(objet);
        inventaireController = new InventaireController(joueur.getInventaire(), gamePane);
        inventaireController.afficherInventaire();

        // Creation des monstres
        nbMonstresMax = 10;
        listeMonstres = new Monstre[nbMonstresMax];
        monstreController = new MonstreController(listeMonstres, GAME_WIDTH, GAME_HEIGHT, gamePane);
        monstreController.creerMonstre(5);

        // Event handlers for key presses and releases
        scene.setOnKeyPressed(event -> joueurController.handleKeyPress(event.getCode()));
        scene.setOnKeyReleased(event -> joueurController.handleKeyRelease(event.getCode()));

        // Start the game loop
        startGameLoop();

        primaryStage.show();
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    private void update() {
        // Deplacement du joueur
        joueurController.seDeplacer();
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}