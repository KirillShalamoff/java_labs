package org.openjfx.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ScoreManager {

    private String path;

    public ScoreManager(String path) {
        this.path = path;
    }

    public void saveScores(int scores) {
        List<Integer> list = loadScoresAsList();
        try (FileWriter fw = new FileWriter(this.path, true)) {

            if(list.size() == 10 && scores < list.get(9)) {
                return;
            }

            fw.write(scores + " ");
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить очки", e);
        }
    }

    // Считываем все числа, сортируем и возвращаем список
    public List<Integer> loadScoresAsList() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(this.path);

        if (!file.exists()) return scores;

        // Scanner идеально подходит для чтения чисел через пробел или перевод строки
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                scores.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Сортировка по возрастанию
        scores.sort(Collections.reverseOrder());
        return scores;
    }
}
