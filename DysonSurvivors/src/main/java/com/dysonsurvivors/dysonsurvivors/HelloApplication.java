package com.dysonsurvivors.dysonsurvivors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import com.dysonsurvivors.dysonsurvivors.Models.Joueur;
import com.dysonsurvivors.dysonsurvivors.Models.Monstre;
import com.dysonsurvivors.dysonsurvivors.Models.Factory.FMonstre;

public class HelloApplication extends Application {

    private static final int CHARACTER_RADIUS = 20;
    private static final int ENEMY_RADIUS = 15;
    private static final int GAME_WIDTH = 1024;
    private static final int GAME_HEIGHT = 600;
    private Circle character;
    private Circle enemy;
    private Label coordinatesLabel;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private Monstre[] listeMonstre;
    private Joueur joueur;


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game");

        /*character = (Circle) loader.getNamespace().get("character");*/
        coordinatesLabel = (Label) loader.getNamespace().get("coordinatesLabel");

        // Creation du joueur
        joueur = new Joueur("Joueur", 100, 100);
        joueur.getHitbox().setStyle("-fx-fill: blue;");
        joueur.getHitbox().setCenterX(GAME_WIDTH / 2);
        joueur.getHitbox().setCenterY(GAME_HEIGHT / 2);
        Pane gamePane = (Pane) root.lookup("#gamePane");
        gamePane.getChildren().add(joueur.getHitbox());

/*        enemy = new Circle(ENEMY_RADIUS);
        enemy.setStyle("-fx-fill: red;");
        enemy.relocate(GAME_WIDTH / 2, GAME_HEIGHT / 2);*/

        FMonstre monstreFactory = new FMonstre();

        //liste de monstre
        listeMonstre = new Monstre[60];

        //boucle qui fait des monstres et les ajoute a une liste de monstres:
        for (int i = 0; i < 10; i++) {
            int niveauMonstre = (int) (Math.random() * 3) + 1;
            Monstre monstre = monstreFactory.creerMonstre(niveauMonstre);
            monstre.getHitbox().setStyle("-fx-fill: red;");
            // fais apparaitre les monstres a des positions aleatoires
            monstre.getHitbox().setCenterX((int) (Math.random() * GAME_WIDTH));
            monstre.getHitbox().setCenterY((int) (Math.random() * GAME_HEIGHT));
            /*Pane gamePane = (Pane) root.lookup("#gamePane");*/
            gamePane.getChildren().add(monstre.getHitbox());
            listeMonstre[i] = monstre; 
        }

        // Event handlers for key presses and releases
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        scene.setOnKeyReleased(event -> handleKeyRelease(event.getCode()));

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
        joueur.seDeplacer(upPressed, downPressed, leftPressed, rightPressed);
        // Met a jour les coordonnees du joueur
        updateCoordinatesLabel();
        // Centre la camera sur le joueur
        centerCameraOnPlayer();
        // Fait se deplacer les monstres de la liste
        for (int i = 0; i < 10; i++) {
            listeMonstre[i].seDeplacer(joueur);
        }
    }

    private void updateCoordinatesLabel() {
        double playerX = joueur.getHitbox().getCenterX();
        double playerY = joueur.getHitbox().getCenterY();
        coordinatesLabel.setText("Coordinates: (" + Math.round(playerX) + ", " +
                Math.round(playerY) + ")");
    }

    private void handleKeyPress(KeyCode code) {
        switch (code) {
            case Z:
                upPressed = true;
                break;
            case S:
                downPressed = true;
                break;
            case Q:
                leftPressed = true;
                break;
            case D:
                rightPressed = true;
                break;
            default:
                break;
        }
    }

    private void handleKeyRelease(KeyCode code) {
        switch (code) {
            case Z:
                upPressed = false;
                break;
            case S:
                downPressed = false;
                break;
            case Q:
                leftPressed = false;
                break;
            case D:
                rightPressed = false;
                break;
            default:
                break;
        }
    }

    private void centerCameraOnPlayer() {
        double playerX = joueur.getHitbox().getCenterX();
        double playerY = joueur.getHitbox().getCenterY();

        // Calculate the position to center the camera on the player
        double cameraOffsetX = playerX - GAME_WIDTH / 2;
        double cameraOffsetY = playerY - GAME_HEIGHT / 2;

        // Translate the gamePane (your game area) by the calculated offset
        Pane gamePane = (Pane) joueur.getHitbox().getParent();
        gamePane.setTranslateX(-cameraOffsetX);
        gamePane.setTranslateY(-cameraOffsetY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}