package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.input.KeyCode;

public class SAzerty implements IhandleKeyAction{
    
    public void handleKeyPress(KeyCode code) {
        Joueur joueur = JoueurSingleton.getInstance();
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

    public void handleKeyRelease(KeyCode code){
        Joueur joueur = JoueurSingleton.getInstance();
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

}
