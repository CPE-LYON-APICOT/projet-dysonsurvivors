package com.dysonsurvivors.dysonsurvivors.Controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import com.dysonsurvivors.dysonsurvivors.Models.IhandleKeyAction;
import com.dysonsurvivors.dysonsurvivors.Models.JoueurSingleton;
import com.dysonsurvivors.dysonsurvivors.Models.SArrowKey;
import com.dysonsurvivors.dysonsurvivors.Models.SAzerty;
import com.dysonsurvivors.dysonsurvivors.Models.SQwerty;

public class ParamController {
    private int GAME_WIDTH;
    private int GAME_HEIGHT;
    private Pane gamePane;
    private Pane paramPane;
    private boolean isActive;
    private JoueurController joueurController;

    public ParamController(int GAME_WIDTH, int GAME_HEIGHT, Pane gamePane, JoueurController joueurController) {
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.gamePane = gamePane;
        this.paramPane = new Pane();
        this.joueurController = joueurController;
        this.isActive = false;
        createParamPane();
    }

    private void createParamPane() {

        paramPane.setStyle("-fx-background-color: #CCCCCC;");

        ComboBox<String> paramComboBox = new ComboBox<>();
        paramComboBox.setItems(FXCollections.observableArrayList("Azerty", "Qwerty", "ArrowKeys"));
        paramComboBox.setPromptText("Azerty");
        paramComboBox.setTranslateX(10);
        paramComboBox.setTranslateY(50);
        
        Label titleLabel = new Label("Paramètres");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label KeysLabel = new Label("Keyboard keys :");
        KeysLabel.setStyle("-fx-font-size: 10px;");
        KeysLabel.setTranslateX(10);
        KeysLabel.setTranslateY(30);
        
        Button quitButton = new Button("Quitter le jeu");
        quitButton.setOnAction(event -> System.exit(0));
        quitButton.setTranslateX(10);
        quitButton.setTranslateY(80);

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

        paramPane.getChildren().addAll(titleLabel,KeysLabel, paramComboBox, quitButton);
        paramPane.setLayoutX(JoueurSingleton.getInstance().getHitbox().getLayoutX() - 100);
        paramPane.setLayoutY(JoueurSingleton.getInstance().getHitbox().getLayoutY() - 100);
        paramPane.setPrefSize(200, 120);
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
    
}
