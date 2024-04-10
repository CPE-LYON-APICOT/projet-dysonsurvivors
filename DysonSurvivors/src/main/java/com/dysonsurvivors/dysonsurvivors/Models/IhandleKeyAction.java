package com.dysonsurvivors.dysonsurvivors.Models;

import javafx.scene.input.KeyCode;

public interface IhandleKeyAction {
    public void handleKeyRelease(KeyCode code);
    public void handleKeyPress(KeyCode code);
}
