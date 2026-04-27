package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.openjfx.Controller.ConsoleInputHandler;
import org.openjfx.Controller.ScoreManager;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.View.ConsoleRenderer;
import org.openjfx.View.GraphicsRenderer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
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
            System.err.println("Ошибка загрузки FXML файла. Проверь путь к scene.fxml");
            e.printStackTrace();
        }
    }

    private static void runConsoleVersion() {
        Scanner scanner = new Scanner(System.in);
        ScoreManager sm = new ScoreManager("./src/main/resources/org/openjfx/HighScores.txt");
        TetrisEngine engine = new TetrisEngine();
        while(true) {
            System.out.println("\n=== TETRIS MENU ===");
            System.out.println("[1] - New Game");
            System.out.println("[2] - High Scores");
            System.out.println("[3] - About");
            System.out.println("[4] - Exit");

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "1", "newgame" -> runNewGame(engine, sm);
                case "2", "highscores" -> printHighScores(sm);
                case "3", "about" -> System.out.print("About Game: \n" + engine.getAbout() + "\n");
                case "4", "exit" -> { return; }
                default -> System.out.println("Unknown command!");
            }
        }
    }

    private static void printHighScores(ScoreManager sm) {
        List<Integer> sortedScores = sm.loadScoresAsList();

        StringBuilder sb = new StringBuilder();
        for (Integer s : sortedScores) {
            sb.append(s).append("\n");
        }

        String resultText = sb.toString();


        System.out.println("Best results:");
        System.out.print(resultText.isEmpty() ? "Список пуст" : resultText);
    }

    private static void runNewGame(TetrisEngine engine, ScoreManager sm) {

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
            }

            try {
                Thread.sleep(1500); // Пауза полсекунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sm.saveScores(engine.getScores());
        System.out.println("\nCongrats! You are earned" + engine.getScores() + "scores!\n");
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



