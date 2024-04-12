package com.dysonsurvivors.dysonsurvivors.Controllers;

import com.dysonsurvivors.dysonsurvivors.Models.*;
import com.dysonsurvivors.dysonsurvivors.Models.Inventaire.Equipements.Armes.ChampignonHallucinogene;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ParamController {
    private Pane gamePane;
    private Pane paramPane;
    private boolean isActive;
    private JoueurController joueurController;
    private int paramWidth;
    private int paramHeight;


    public ParamController(Pane gamePane, JoueurController joueurController) {
        this.gamePane = gamePane;
        this.paramPane = new Pane();
        this.joueurController = joueurController;
        this.isActive = false;
        this.paramWidth = 200;
        this.paramHeight = 220;
        createParamPane();
    }

    private void createParamPane() {

        // Application d'une classe css pour le style de la fenêtre de paramètres
        paramPane.getStyleClass().add("param-pane");

        Label titleLabel = new Label("Paramètres");
        titleLabel.getStyleClass().add("param-title");

        Label KeysLabel = new Label("Keyboard keys :");
        KeysLabel.getStyleClass().add("param-label");
        KeysLabel.setTranslateX(7);
        KeysLabel.setTranslateY(45);

        ComboBox<String> paramComboBox = new ComboBox<>();
        paramComboBox.getStyleClass().add("param-combobox");
        paramComboBox.setItems(FXCollections.observableArrayList("Azerty", "Qwerty", "ArrowKeys"));
        paramComboBox.setPromptText("Azerty");
        paramComboBox.setTranslateX(10);
        paramComboBox.setTranslateY(70);
        
        Button quitButton = new Button("Quitter le jeu");
        quitButton.getStyleClass().add("param-button");
        quitButton.setOnAction(event -> System.exit(0));
        quitButton.setTranslateX(10);
        quitButton.setTranslateY(175);

        Button fullscreenButton = new Button("Plein écran");
        fullscreenButton.getStyleClass().add("param-button");
        fullscreenButton.setOnAction(event -> toggleFullscreen());
        fullscreenButton.setTranslateX(10);
        fullscreenButton.setTranslateY(115); // Ajustez la position en fonction de votre mise en page

        paramComboBox.setOnAction(event -> {
            String selectedParam = paramComboBox.getSelectionModel().getSelectedItem();
            IhandleKeyAction handleKeyAction;
            switch (selectedParam) {
                case "Azerty":
                    handleKeyAction = new SAzerty();
                    joueurController.setHandleKeyAction(handleKeyAction);
                    break;
                case "Qwerty":
                    handleKeyAction = new SQwerty();
                    joueurController.setHandleKeyAction(handleKeyAction);
                    break;
                case "ArrowKeys":
                    handleKeyAction = new SArrowKey();
                    joueurController.setHandleKeyAction(handleKeyAction);
                    break;
                default:
                    break;
            }
        });

        paramPane.getChildren().addAll(titleLabel,KeysLabel, paramComboBox, quitButton, fullscreenButton);
        paramPane.setLayoutX(JoueurSingleton.getInstance().getHitbox().getLayoutX() - 100);
        paramPane.setLayoutY(JoueurSingleton.getInstance().getHitbox().getLayoutY() - 100);
        /* Taille adaptative en fonction du contenu */
        paramPane.setPrefSize(paramWidth, paramHeight);
    }

    public void handleKeyPress(KeyCode code) {
        if (code == KeyCode.ESCAPE) {
            if (gamePane.getChildren().contains(this.paramPane)) {
                this.isActive = false;
                cacherParametres();
            } else {
                this.isActive = true;
                paramPane.setLayoutX(JoueurSingleton.getInstance().getHitbox().getLayoutX() - 100);
                paramPane.setLayoutY(JoueurSingleton.getInstance().getHitbox().getLayoutY() - 100);
                afficherParametres();
            }
        }
    }


    public void afficherParametres() {
        gamePane.getChildren().add(this.paramPane);
    }

    public void cacherParametres() {
        gamePane.getChildren().remove(this.paramPane);
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public Pane getParamPane() {
        return this.paramPane;
    }

    private void toggleFullscreen() {
        Stage stage = (Stage) gamePane.getScene().getWindow();
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
            joueurController.centerCameraOnPlayer();
        } else {
            stage.setFullScreen(true);
            joueurController.centerCameraOnPlayer();
        }
    }

    public int getParamWidth() {
        return this.paramWidth;
    }

    public void setParamWidth(int paramWidth) {
        this.paramWidth = paramWidth;
    }

    public int getParamHeight() {
        return this.paramHeight;
    }

    public void setParamHeight(int paramHeight) {
        this.paramHeight = paramHeight;
    }
    
}
