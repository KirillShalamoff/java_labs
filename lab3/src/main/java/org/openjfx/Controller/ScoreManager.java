package org.openjfx.Controller;

import java.io.*;

public class ScoreManager {

    private String path;

    public ScoreManager(String path) {
        this.path = path;
    }

    public void saveScores(int scores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.path))) {
            Integer sc = scores;
            bw.write("===== " + sc.toString() + " ====\n");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadScoresAsString() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
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
