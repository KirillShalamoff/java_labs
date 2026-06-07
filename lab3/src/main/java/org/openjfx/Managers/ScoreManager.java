package org.openjfx.Managers;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjfx.Managers.ScoreEntry;

public class ScoreManager {

    private final String path;
    private final Logger logger = LogManager.getLogger(ScoreManager.class);

    // Вспомогательный класс для хранения записи рекорда
    public ScoreManager(String path) {
        this.path = path;
    }

    public void saveScore(int score, String player) {
        // 1. Загружаем текущие рекорды
        boolean playerAlreadyExists = false;
        List<ScoreEntry> scores = loadScores();

        for(ScoreEntry se : scores) {
            if (Objects.equals(se.getName(), player)){
                playerAlreadyExists = true;
                if(se.getScore() < score) {
                    se.setScore(score);
                }
            }
        }

        if (!playerAlreadyExists) {
            // 2. Добавляем новый результат
            scores.add(new ScoreEntry(player, score));
        }
        // 3. Сортируем по убыванию очков и оставляем только ТОП-10
        List<ScoreEntry> topScores = scores.stream()
                .sorted(Comparator.comparingInt(ScoreEntry::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());

        // 4. Перезаписываем файл актуальным топом
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (ScoreEntry entry : topScores) {
                pw.println(entry.getName() + " - " + entry.getScore());
            }
            logger.info("Highscores updated. Player {} reached {} scores", player, score);
        } catch (IOException e) {
            logger.error("error while writing in file", e);
        }
    }

    public List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(this.path);

        if (!file.exists()) {
            logger.info("File of highscores is not exists.");
            return scores;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    try {
                        String name = parts[0].trim();
                        int value = Integer.parseInt(parts[1].trim());
                        scores.add(new ScoreEntry(name, value));
                    } catch (NumberFormatException e) {
                        logger.error("Error of parsing number in line: {}", line);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("error while reading file of  highscores", e);
        }

        // Сортируем перед возвратом на всякий случай
        scores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        logger.debug("Loaded {} score entryes", scores.size());
        return scores;
    }
}