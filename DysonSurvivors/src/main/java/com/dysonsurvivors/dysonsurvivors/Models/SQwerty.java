package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.input.KeyCode;

public class SQwerty implements IhandleKeyAction{
    
    public void handleKeyPress(KeyCode code) {
        Joueur joueur = JoueurSingleton.getInstance();
        switch (code) {
            case W:
                joueur.setUpPressed(true);
                break;
            case S:
                joueur.setDownPressed(true);
                break;
            case A:
                joueur.setLeftPressed(true);
                break;
            case D:
                joueur.setRightPressed(true);
                break;
            default:
                break;
        }
    }

    public void handleKeyRelease(KeyCode code){
        Joueur joueur = JoueurSingleton.getInstance();
        switch (code) {
            case W:
                joueur.setUpPressed(false);
                break;
            case S:
                joueur.setDownPressed(false);
                break;
            case A:
                joueur.setLeftPressed(false);
                break;
            case D:
                joueur.setRightPressed(false);
                break;
            default:
                break;
        }
    }

}
