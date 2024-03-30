package com.dysonsurvivors.dysonsurvivors;

import com.dysonsurvivors.dysonsurvivors.Controllers.JoueurController;
import com.dysonsurvivors.dysonsurvivors.Controllers.MonstreController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;

public class MainApp extends Application {
    private static int GAME_WIDTH = 1024;
    private static int GAME_HEIGHT = 600;
    private Label coordinatesLabel;
    private MonstreController monstreController;
    private Monstre[] listeMonstres;
    private int nbMonstresMax;
    private JoueurController joueurController;
    private Joueur joueur;

    @Override
    public void start(Stage primaryStage) throws IOException {
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
        joueur = joueurController.CreateJoueur("Joueur", 100, 100);

        // Creation des monstres
        nbMonstresMax = 10;
        listeMonstres = new Monstre[nbMonstresMax];
        monstreController = new MonstreController(listeMonstres, GAME_WIDTH, GAME_HEIGHT, gamePane);
        monstreController.creerMonstre(5);
        for (Monstre monstre : listeMonstres) {
            System.out.println(monstre);
        }

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