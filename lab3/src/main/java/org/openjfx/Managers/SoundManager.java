package org.openjfx.Managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundManager {
    private MediaPlayer backgroundPlayer;
    private boolean isMuted = false;
    private static Logger logger = LogManager.getLogger(SoundManager.class);


    public void playBackgroundMusic(String path) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                logger.error("file not found from path!\n");
                return;
            }

            if (backgroundPlayer != null) {
                backgroundPlayer.stop();
                backgroundPlayer.dispose();
            }

            Media media = new Media(resource.toExternalForm());
            backgroundPlayer = new MediaPlayer(media);

            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundPlayer.setVolume(0.3);
            backgroundPlayer.setMute(isMuted);

            backgroundPlayer.play();
            logger.info("Background music was executet from: {}\n", path);

        } catch (Exception e) {
            logger.error("Error with execute background music: {}\n", e.getMessage(), e);        }
    }

    public void setMuted(boolean muted) {
        this.isMuted = muted;
        if (backgroundPlayer != null) {
            logger.info("mute status changed!\n", muted);
            backgroundPlayer.setMute(muted);
        }
    }

    public void playGameOverSound(String path) {
        if (backgroundPlayer != null) backgroundPlayer.stop();
        if (isMuted) return;

        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                logger.error("file not found from path!\n");
                return;
            }

            AudioClip gameOverSound = new AudioClip(resource.toString());
            gameOverSound.setVolume(0.7);
            gameOverSound.play();
            logger.info("Background music was executet from: {}\n", path);

        } catch (Exception e) {
            logger.error("Error with execute game over music: {}\n", e.getMessage(), e);
            }
    }
}