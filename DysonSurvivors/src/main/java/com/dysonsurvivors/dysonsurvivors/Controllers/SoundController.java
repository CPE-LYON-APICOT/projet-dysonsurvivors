package com.dysonsurvivors.dysonsurvivors.Controllers;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {
    Media sound = new Media(getClass().getResource("Music/pdm-soundtrack.mp3").toExternalForm());
    
    public SoundController() {
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void stopMusic() {
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.stop();
    }

    public void playMusic() {
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void pauseMusic() {
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.pause();
    }

    public void setVolume(double volume) {
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(volume);
    }

    public void setMusic(String music) {
        sound = new Media(getClass().getResource("Music/" + music + ".mp3").toExternalForm());
    }
}
