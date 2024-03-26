package com.dysonsurvivors.dysonsurvivors;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {

    private static final int CHARACTER_RADIUS = 20;
    private static final int ENEMY_RADIUS = 15;
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private Circle character;
    private Circle enemy;
    private Label coordinatesLabel;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game");

        character = (Circle) loader.getNamespace().get("character");
        coordinatesLabel = (Label) loader.getNamespace().get("coordinatesLabel");

        TranslateTransition translateCharacterX = new TranslateTransition(Duration.millis(200), character);
        TranslateTransition translateCharacterY = new TranslateTransition(Duration.millis(200), character);
        TranslateTransition translateRootX = new TranslateTransition(Duration.millis(200), (Pane) root);
        TranslateTransition translateRootY = new TranslateTransition(Duration.millis(200), (Pane) root);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z:
                    translateCharacterX.stop();
                    translateCharacterY.setToY(character.getTranslateY() - 10);
                    translateRootY.setByY(10);
                    translateCharacterY.play();
                    translateRootY.play();
                    break;
                case S:
                    translateCharacterX.stop();
                    translateCharacterY.setToY(character.getTranslateY() + 10);
                    translateRootY.setByY(-10);
                    translateCharacterY.play();
                    translateRootY.play();
                    break;
                case Q:
                    translateCharacterY.stop();
                    translateCharacterX.setToX(character.getTranslateX() - 10);
                    translateRootX.setByX(10);
                    translateCharacterX.play();
                    translateRootX.play();
                    break;
                case D:
                    translateCharacterY.stop();
                    translateCharacterX.setToX(character.getTranslateX() + 10);
                    translateRootX.setByX(-10);
                    translateCharacterX.play();
                    translateRootX.play();
                    break;
                default:
                    break;
            }
            coordinatesLabel.setText("Coordinates: (" + (character.getCenterX() + translateCharacterX.getByX()) + ", " +
                    (character.getCenterY() + translateCharacterY.getByY()) + ")");
        });

        enemy = new Circle(ENEMY_RADIUS);
        enemy.setStyle("-fx-fill: red;");
        enemy.relocate(GAME_WIDTH / 2, GAME_HEIGHT / 2);

        Pane gamePane = (Pane) root.lookup("#gamePane");
        gamePane.getChildren().add(enemy);

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
        // Update player coordinates
        double playerX = character.getTranslateX() + character.getCenterX();
        double playerY = character.getTranslateY() + character.getCenterY();

        // Move enemy towards the player
        moveEnemyTowardsPlayer();
        // Update coordinates label
        updateCoordinatesLabel(playerX, playerY);
    }

    private void moveEnemyTowardsPlayer() {
        double playerX = character.getTranslateX() + character.getCenterX();
        double playerY = character.getTranslateY() + character.getCenterY();
        double enemyX = enemy.getCenterX();
        double enemyY = enemy.getCenterY();

        double angle = Math.atan2(playerY - enemyY, playerX - enemyX);
        double speed = 0.1; // You can adjust the speed of the enemy here
        double newX = enemyX + Math.cos(angle) * speed;
        double newY = enemyY + Math.sin(angle) * speed;

        enemy.setCenterX(newX);
        enemy.setCenterY(newY);
    }

    private void updateCoordinatesLabel(double playerX, double playerY) {
        long playerXLong = Math.round(playerX);
        long playerYLong = Math.round(playerY);
        coordinatesLabel.setText("Coordinates: (" + playerXLong + ", " + playerYLong + ")");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
