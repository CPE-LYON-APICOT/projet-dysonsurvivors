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
    private Monstre[] listeMonstres;
    private int nbMonstres;
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
        /*joueur.getHitbox().setStyle("-fx-fill: blue;");*/
        joueur.getHitbox().setLayoutX(GAME_WIDTH / 2);
        joueur.getHitbox().setLayoutY(GAME_HEIGHT / 2);
        Pane gamePane = (Pane) root.lookup("#gamePane");
        gamePane.getChildren().add(joueur.getHitbox());

/*        enemy = new Circle(ENEMY_RADIUS);
        enemy.setStyle("-fx-fill: red;");
        enemy.relocate(GAME_WIDTH / 2, GAME_HEIGHT / 2);*/

        FMonstre monstreFactory = new FMonstre();

        //liste de monstre
        nbMonstres = 10;
        listeMonstres = new Monstre[nbMonstres];

        //boucle qui fait des monstres et les ajoute a une liste de monstres:
        for (int i = 0; i < nbMonstres; i++) {
            int niveauMonstre = (int) (Math.random() * 3) + 1;
            Monstre monstre = monstreFactory.creerMonstre(niveauMonstre);
            /*monstre.getHitbox().setStyle("-fx-fill: red;");*/
            // fais apparaitre les monstres a des positions aleatoires
            monstre.getHitbox().setLayoutX((int) (Math.random() * GAME_WIDTH));
            monstre.getHitbox().setLayoutY((int) (Math.random() * GAME_HEIGHT));
          /*  Pane gamePane = (Pane) root.lookup("#gamePane");*/
            gamePane.getChildren().add(monstre.getHitbox());
            listeMonstres[i] = monstre;
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
        joueur.seDeplacer();
        // Met a jour les coordonnees du joueur
        updateCoordinatesLabel();
        // Centre la camera sur le joueur
        centerCameraOnPlayer();
        // Fait se deplacer les monstres de la liste
        for (int i = 0; i < nbMonstres; i++) {
            /*System.out.println("Monstre " + i);*/
            listeMonstres[i].seDeplacer(joueur);
        }
    }

    private void updateCoordinatesLabel() {
        double playerX = joueur.getHitbox().getLayoutX() + joueur.getHitbox().getWidth()/2;
        double playerY = joueur.getHitbox().getLayoutY() + joueur.getHitbox().getHeight()/2;
        coordinatesLabel.setText("Coordinates: (" + Math.round(playerX) + ", " +
                Math.round(playerY) + ")");
    }

    private void handleKeyPress(KeyCode code) {
        switch (code) {
            case Z:
                joueur.setUpPressed(true);
                break;
            case S:
                joueur.setDownPressed(true);
                break;
            case Q:
                joueur.setLeftPressed(true);
                break;
            case D:
                joueur.setRightPressed(true);
                break;
            default:
                break;
        }
    }

    private void handleKeyRelease(KeyCode code) {
        switch (code) {
            case Z:
                joueur.setUpPressed(false);
                break;
            case S:
                joueur.setDownPressed(false);
                break;
            case Q:
                joueur.setLeftPressed(false);
                break;
            case D:
                joueur.setRightPressed(false);
                break;
            default:
                break;
        }
    }

    private void centerCameraOnPlayer() {
        double playerX = joueur.getHitbox().getLayoutX();
        double playerY = joueur.getHitbox().getLayoutY();

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