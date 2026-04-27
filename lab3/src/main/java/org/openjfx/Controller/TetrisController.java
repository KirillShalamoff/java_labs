package org.openjfx.Controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.View.GraphicsRenderer;
import org.openjfx.Controller.ScoreManager;
import java.util.List;

public class TetrisController {

    private ScoreManager scoreManager;
    private SoundManager soundManager;
    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;

    @FXML private Canvas nextShapeCanvas;

    private TetrisEngine engine;
    private GraphicsRenderer renderer;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    private boolean isMuted = false; // Флаг для мута
    private boolean isGameOverSoundPlayed = false; // Чтобы звук смерти играл 1 раз

    // Метод initialize вызывается автоматически после загрузки FXML
    @FXML
    public void initialize() {
        this.soundManager = new SoundManager();
        soundManager.playBackgroundMusic("/org/openjfx/music/theme.mp3");
        this.scoreManager = new ScoreManager("./src/main/resources/org/openjfx/HighScores.txt");
        engine = new TetrisEngine();
        renderer = new GraphicsRenderer(gameCanvas);

        // Настраиваем пульс игры
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 500_000_000) {
                    updateGame();
                    lastUpdate = now;
                }
            }
        };

        // Важно: чтобы клавиатура работала, вешаем слушатель на сцену чуть позже
        Platform.runLater(() -> {
            gameCanvas.getScene().setOnKeyPressed(this::handleKeyPress);
        });
    }

    private void updateGame() {
        engine.update();
        scoreLabel.setText(String.valueOf(engine.getScores()));
        renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
        if (engine.getStatus() == false) {
            if(!isGameOverSoundPlayed) {
                soundManager.playGameOverSound("/org/openjfx/music/game_over.mp3");
                isGameOverSoundPlayed = true;
            }
            stopGame();
        }
    }

    private void handleKeyPress(KeyEvent event) {
        if (!engine.getStatus()) return;

        switch (event.getCode()) {
            case A, LEFT  -> engine.tryMoveLeft();
            case D, RIGHT -> engine.tryMoveRight();
            case W, UP    -> engine.tryRotate();
            case S, DOWN  -> engine.update();
            case ESCAPE   -> stopGame();
        }
        // Перерисовываем мгновенно при нажатии кнопки
        renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
    }

    private void stopGame() {
        gameLoop.stop();
        engine.stop();
        this.scoreManager.saveScores(engine.getScores()); // Сохраняем в твой файл!
        engine.setScores(0);


        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Вы проиграли!");
            alert.setContentText("Ваш результат: " + engine.getScores());
            alert.showAndWait();

        });
    }

    // Методы для кнопок из FXML
    @FXML
    void onNewGameClick() {
        isGameOverSoundPlayed = false; // Сбрасываем флаг для новой игры
        engine.start();
        gameLoop.start();
        gameCanvas.requestFocus(); // Это критично для HBox!
        renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
    }

    @FXML
    void onMuteClick() {
        isMuted = !isMuted;
        soundManager.setMuted(isMuted);
        System.out.println(isMuted ? "Звук выключен" : "Звук включен");
    }

    @FXML
    private void onHighScoresClick() {
        List<Integer> sortedScores = this.scoreManager.loadScoresAsList();

        // Склеиваем числа из списка в строку, разделяя их переносом строки \n
        StringBuilder sb = new StringBuilder();
        for (Integer s : sortedScores) {
            sb.append(s).append("\n");
        }

        String resultText = sb.toString();

        // Выводим (пример с Alert)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Рекорды");
        alert.setHeaderText("Результаты по возрастанию:");
        alert.setContentText(resultText.isEmpty() ? "Список пуст" : resultText);
        alert.showAndWait();
    }

    @FXML
    void onAboutClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Tetris JavaFX");
        alert.setContentText(engine.getAbout());
        alert.showAndWait();
    }

    @FXML
    void onExitClick() {
        Platform.exit();
    }
}