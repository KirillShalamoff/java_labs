//прогнозирование с рамками + движение по диоганали
package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.Controller.ConsoleInputHandler;
import org.openjfx.Managers.ScoreEntry;
import org.openjfx.Managers.ScoreManager;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.View.ConsoleRenderer;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/scene.fxml"));
            Parent root = loader.load();

            // 2. Создаем сцену
            Scene scene = new Scene(root);

            // 3. Настраиваем и показываем окно
            primaryStage.setTitle("Tetris NSU Edition");
            primaryStage.setScene(scene);

            // Запрещаем менять размер окна, чтобы верстка не поехала
            primaryStage.setResizable(false);

            primaryStage.show();

        } catch (IOException e) {
            System.err.println("eror while loading FXML file. Check path to scene.fxml!");
            e.printStackTrace();
        }
    }

    private static void runConsoleVersion() {
        Scanner scanner = new Scanner(System.in);
        ScoreManager sm = new ScoreManager("./src/main/resources/org/openjfx/HighScores.txt");
        TetrisEngine engine = new TetrisEngine();

        System.out.println("entry your name:\n");
        String playerName = scanner.nextLine();

        while(true) {
            System.out.println("\n=== TETRIS MENU ===");
            System.out.println("[1] - New Game");
            System.out.println("[2] - High Scores");
            System.out.println("[3] - About");
            System.out.println("[4] - Exit");

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "1", "newgame" -> runNewGame(engine, sm, playerName);
                case "2", "highscores" -> printHighScores(sm);
                case "3", "about" -> System.out.print("About Game: \n" + engine.getAbout() + "\n");
                case "4", "exit" -> { return; }
            }
        }
    }

    private static void printHighScores(ScoreManager sm) {
        List<ScoreEntry> sortedScores = sm.loadScores();

        StringBuilder sb = new StringBuilder();
        for (ScoreEntry se : sortedScores) {
            sb.append(se.getName() + " - " + se.getScore() + "\n");
        }

        String resultText = sb.toString();


        System.out.println("Best results:");
        System.out.print(resultText.isEmpty() ? "List is empty!" : resultText);
    }

    private static void runNewGame(TetrisEngine engine, ScoreManager sm, String playerName) {
        engine.start();
        ConsoleInputHandler inputHandler = new ConsoleInputHandler();

        while(engine.getStatus()) {
            String cmd = inputHandler.getCommand();

            engine.update();
            ConsoleRenderer.render(engine.getBoard(), engine.getCurrentShape());

            switch (cmd) {
                case "EXIT"   -> engine.stop();
                case "LEFT"   -> engine.tryMoveLeft();
                case "RIGHT"  -> engine.tryMoveRight();
                case "ROTATE" -> engine.tryRotate();
                case "DOWN"   -> engine.update();
                case "HADRDDROP" -> engine.hardDrop();
            }

            try {
                Thread.sleep(500); // Пауза полсекунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sm.saveScore(engine.getScores(), playerName);
        System.out.println("\nCongrats! You are earned " + engine.getScores() + " scores!\n");
        engine.setScores(0);

    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--console")) {
            runConsoleVersion();
        } else {
            launch(args);
        }
    }

}



