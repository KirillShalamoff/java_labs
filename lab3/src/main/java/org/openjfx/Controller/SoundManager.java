package org.openjfx.Controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    private MediaPlayer backgroundPlayer;
    private boolean isMuted = false;

    public void playBackgroundMusic(String path) {
        try {
            // Обязательно через загрузчик класса, путь начинается с /
            URL resource = getClass().getResource(path);
            if (resource == null) {
                System.err.println("Музыка не найдена по пути: " + path);
                return;
            }

            // Если музыка уже играет — стопаем старую
            if (backgroundPlayer != null) {
                backgroundPlayer.stop();
                backgroundPlayer.dispose(); // Освобождаем ресурсы
            }

            Media media = new Media(resource.toExternalForm());
            backgroundPlayer = new MediaPlayer(media);

            // Настройки фонового трека
            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundPlayer.setVolume(0.3);
            backgroundPlayer.setMute(isMuted);

            // Обработка ошибок в реальном времени
            backgroundPlayer.setOnError(() -> {
                System.err.println("Ошибка плеера: " + backgroundPlayer.getError().getMessage());
            });

            backgroundPlayer.play();
            System.out.println("Фоновая музыка запущена через MediaPlayer");

        } catch (Exception e) {
            System.err.println("Не удалось запустить MediaPlayer: " + e.getMessage());
        }
    }

    public void setMuted(boolean muted) {
        this.isMuted = muted;
        if (backgroundPlayer != null) {
            backgroundPlayer.setMute(muted);
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }
    public void playGameOverSound(String path) {
        if (backgroundPlayer != null) backgroundPlayer.stop();
        if (isMuted) return; // Если мут, то и эффект не играем

        URL resource = getClass().getResource(path);
        if (resource != null) {
            AudioClip gameOverSound = new AudioClip(resource.toString());
            gameOverSound.setVolume(0.7);
            gameOverSound.play();
        }
    }
}