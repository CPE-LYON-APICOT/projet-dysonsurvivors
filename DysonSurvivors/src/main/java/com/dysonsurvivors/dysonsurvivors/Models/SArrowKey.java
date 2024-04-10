package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.input.KeyCode;

public class SArrowKey implements IhandleKeyAction{
    
    public void handleKeyPress(KeyCode code) {
        Joueur joueur = JoueurSingleton.getInstance();
        switch (code) {
            case UP:
                joueur.setUpPressed(true);
                break;
            case DOWN:
                joueur.setDownPressed(true);
                break;
            case LEFT:
                joueur.setLeftPressed(true);
                break;
            case RIGHT:
                joueur.setRightPressed(true);
                break;
            default:
                break;
        }
    }

    public void handleKeyRelease(KeyCode code){
        Joueur joueur = JoueurSingleton.getInstance();
        switch (code) {
            case UP:
                joueur.setUpPressed(false);
                break;
            case DOWN:
                joueur.setDownPressed(false);
                break;
            case LEFT:
                joueur.setLeftPressed(false);
                break;
            case RIGHT:
                joueur.setRightPressed(false);
                break;
            default:
                break;
        }
    }

}
