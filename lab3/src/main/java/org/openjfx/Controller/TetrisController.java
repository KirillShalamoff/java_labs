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
import org.openjfx.Controller.ScoreManager; // Твой класс для работы с файлом

public class TetrisController {

    private final String PATH_TO_HSTXT = "/src/main/resources/HighScores.txt";

    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;

    private TetrisEngine engine;
    private GraphicsRenderer renderer;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;

    // Метод initialize вызывается автоматически после загрузки FXML
    @FXML
    public void initialize() {
        engine = new TetrisEngine();
        renderer = new GraphicsRenderer(gameCanvas);

        // Настраиваем пульс игры
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // now в наносекундах. 500_000_000 нс = 0.5 сек
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
        if (engine.getStatus()) {
            engine.update();
            scoreLabel.setText(String.valueOf(engine.getScores()));
            renderer.render(engine, engine.getCurrentShape());

            if (engine.getStatus()) {
                stopGame();
            }
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
        renderer.render(engine, engine.getCurrentShape());
    }

    private void stopGame() {
        gameLoop.stop();
        engine.stop();
        ScoreManager.saveScores(engine.getScores(), PATH_TO_HSTXT); // Сохраняем в твой файл!

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Вы проиграли!");
        alert.setContentText("Ваш результат: " + engine.getScores());
        alert.showAndWait();
    }

    // Методы для кнопок из FXML
    @FXML
    void onNewGameClick() {
        engine.start();
        gameLoop.start();
        renderer.render(engine, engine.getCurrentShape());
    }

    @FXML
    void onHighScoresClick() {
        // Просто выводим Alert с данными из ScoreManager
        String scores = String.join("\n", ScoreManager.loadScoresAsString(PATH_TO_HSTXT));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("High Scores");
        alert.setHeaderText("Лучшие результаты:");
        alert.setContentText(scores.isEmpty() ? "Рекордов пока нет" : scores);
        alert.showAndWait();
    }

    @FXML
    void onAboutClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Tetris JavaFX");
        alert.setContentText("Сделано с душой и без ебаных слипов.");
        alert.showAndWait();
    }

    @FXML
    void onExitClick() {
        Platform.exit();
    }
}