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

public class HelloApplication extends Application {

    private static final int CHARACTER_RADIUS = 20;
    private static final int ENEMY_RADIUS = 15;
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private Circle character;
    private Circle enemy;
    private Label coordinatesLabel;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private double playerSpeed = 3;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game");

        character = (Circle) loader.getNamespace().get("character");
        coordinatesLabel = (Label) loader.getNamespace().get("coordinatesLabel");

        enemy = new Circle(ENEMY_RADIUS);
        enemy.setStyle("-fx-fill: red;");
        enemy.relocate(GAME_WIDTH / 2, GAME_HEIGHT / 2);

        Pane gamePane = (Pane) root.lookup("#gamePane");
        gamePane.getChildren().add(enemy);

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
        // Move the player
        moveCharacter();
        // Move enemy towards the player
        moveEnemyTowardsPlayer();
        // Update coordinates label
        updateCoordinatesLabel();
        // Center camera on player
        centerCameraOnPlayer();
    }

    private void moveCharacter() {
        double dx = 0, dy = 0;
        if (upPressed) dy -= playerSpeed;
        if (downPressed) dy += playerSpeed;
        if (leftPressed) dx -= playerSpeed;
        if (rightPressed) dx += playerSpeed;

        // Apply movement
        double newX = character.getCenterX() + dx;
        double newY = character.getCenterY() + dy;

        character.setCenterX(newX);
        character.setCenterY(newY);
    }

    private void moveEnemyTowardsPlayer() {
        double playerX = character.getCenterX();
        double playerY = character.getCenterY();
        double enemyX = enemy.getCenterX();
        double enemyY = enemy.getCenterY();

        double angle = Math.atan2(playerY - enemyY, playerX - enemyX);
        double speed = 1; // You can adjust the speed of the enemy here
        double newX = enemyX + Math.cos(angle) * speed;
        double newY = enemyY + Math.sin(angle) * speed;

        enemy.setCenterX(newX);
        enemy.setCenterY(newY);
    }

    private void updateCoordinatesLabel() {
        double playerX = character.getCenterX();
        double playerY = character.getCenterY();
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
        double playerX = character.getCenterX();
        double playerY = character.getCenterY();

        // Calculate the position to center the camera on the player
        double cameraOffsetX = playerX;
        double cameraOffsetY = playerY;

        // Translate the gamePane (your game area) by the calculated offset
        Pane gamePane = (Pane) character.getParent();
        gamePane.setTranslateX(-cameraOffsetX);
        gamePane.setTranslateY(-cameraOffsetY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}