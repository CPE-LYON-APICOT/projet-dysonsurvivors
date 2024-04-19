package com.dysonsurvivors.dysonsurvivors.Controllers;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {
    Media sound = new Media(getClass().getResource("Music/pdm-soundtrack.mp3").toExternalForm());
    MediaPlayer mediaPlayer;
    
    public SoundController() {
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }

    public void playMusic() {
        mediaPlayer.play();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public void setMusic(String music) {
        sound = new Media(getClass().getResource("Music/" + music + ".mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
}
