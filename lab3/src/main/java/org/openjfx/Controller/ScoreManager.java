package org.openjfx.Controller;

import java.io.*;

public class ScoreManager {

    public static void saveScores(int scores, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            Integer sc = scores;
            bw.write("===== " + sc.toString() + " ====\n");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadScoresAsString(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String result = "";
            while((line = br.readLine()) != null) {
                result = result + "\n" + line;
            }

            return result;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
