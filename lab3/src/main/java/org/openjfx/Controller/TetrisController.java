package org.openjfx.Controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.Managers.ScoreEntry;
import org.openjfx.Managers.SoundManager;
import org.openjfx.View.GraphicsRenderer;
import org.openjfx.Managers.ScoreManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TetrisController {

    private final Logger logger = LogManager.getLogger(TetrisController.class);
    private ScoreManager scoreManager;
    private SoundManager soundManager;
    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;
    @FXML private TextField playerNameField;
    @FXML private Canvas nextShapeCanvas;

    private String playerName;
    private TetrisEngine engine;
    private GraphicsRenderer renderer;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    private boolean isMuted = false;
    private boolean isGameOverSoundPlayed = false;
    private final Set<KeyCode> activeKeys = new HashSet<>();

    @FXML
    public void initialize() {
        this.soundManager = new SoundManager();
        this.scoreManager = new ScoreManager("./src/main/resources/org/openjfx/HighScores.txt");
        this.playerName = "ANONIMOUS";
        engine = new TetrisEngine();
        renderer = new GraphicsRenderer(gameCanvas);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 500_000_000) {
                    updateGame();
                    lastUpdate = now;
                }
            }
        };

        Platform.runLater(() -> {
            gameCanvas.getScene().setOnKeyPressed(this::handleKeyPress);
            gameCanvas.getScene().setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
        });

        logger.info("Tetrius controller initialized");
    }

    private void updateGame() {
        engine.update();
        logger.info("engine updated");
        scoreLabel.setText(String.valueOf(engine.getScores()));
        renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
        if (engine.getStatus() == false) {
            if(!isGameOverSoundPlayed) {
                soundManager.playGameOverSound("/org/openjfx/music/game_over.mp3");
                isGameOverSoundPlayed = true;
            }
            stopGame();
            logger.info("engine stopped");
        }
    }

    private void handleKeyPress(KeyEvent event) {
        if (!engine.getStatus()) return;

        KeyCode code = event.getCode();
        activeKeys.add(code); // Добавляем текущую клавишу в сет

        logger.info("key {} pressed", code);

        // Проверяем диагонали (если нажаты две клавиши сразу)
        if (activeKeys.contains(KeyCode.DOWN) || activeKeys.contains(KeyCode.S)) {
            if (activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A)) {
                engine.tryMoveLeftDiag();
                renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);return; // Выходим, чтобы не срабатывали одиночные нажатия ниже
            }
            if (activeKeys.contains(KeyCode.RIGHT) || activeKeys.contains(KeyCode.D)) {
                engine.tryMoveRightDiag();
                renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
                return;
            }
        }
        switch (code) {
            case A, LEFT  -> engine.tryMoveLeft();
            case D, RIGHT -> engine.tryMoveRight();
            case W, UP    -> engine.tryRotate();
            case S, DOWN  -> engine.update();
            case SPACE  -> engine.hardDrop();
            case ESCAPE   -> stopGame();
        }

        renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
    }

    private void stopGame() {
        gameLoop.stop();
        engine.stop();
        this.scoreManager.saveScore(engine.getScores(), playerName); // Сохраняем в твой файл!

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("You lose!");
            alert.setContentText("your result: " + engine.getScores());
            alert.showAndWait();

        });
    }

    // Методы для кнопок из FXML
    @FXML
    void onNewGameClick() {
        engine.setScores(0);
        String input = playerNameField.getText().trim();
        if(!input.isEmpty()) {
            playerName = input;
        }
        logger.info("Player`s name confirmed: " + playerName);
        soundManager.playBackgroundMusic("/org/openjfx/music/theme.mp3");
        isGameOverSoundPlayed = false; // Сбрасываем флаг для новой игры
        engine.start();
        gameLoop.start();
        logger.info("new game started");
        gameCanvas.requestFocus(); // Это критично для HBox!
        renderer.render(engine, engine.getCurrentShape(), engine.getNextShape(), nextShapeCanvas);
    }

    @FXML
    void onMuteClick() {
        isMuted = !isMuted;
        soundManager.setMuted(isMuted);
    }

    @FXML
    private void onHighScoresClick() {
        logger.info("Highscore btn pressed");
        List<ScoreEntry> sortedScores = this.scoreManager.loadScores();

        StringBuilder sb = new StringBuilder();
        for (ScoreEntry se : sortedScores) {
            sb.append(se.getName() + " - " + se.getScore() + "\n");
        }

        logger.info("String with results produced");

        String resultText = sb.toString();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("HIGHSCORES");
        alert.setHeaderText("best results:");
        alert.setContentText(resultText.isEmpty() ? "List is empty" : resultText);
        logger.info("results in window");
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