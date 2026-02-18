import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {
    public Map<String, Integer> words = new HashMap<>();

    public void process(String[] args) {
        if (args.length > 0) {
            readFile(args[0]);
        } else {
            System.err.println("FileName argument is missing!");
        }
    }

    private void readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/"+fileName), "UTF-8"))) {
            StringBuilder sb = new StringBuilder();
            int c;

            while ((c = br.read()) != -1) {
                char ch = (char) c;

                if (Character.isLetterOrDigit(ch)) {
                    sb.append(ch);
                }
                else {
                    saveWord(sb);
                }
            }
            saveWord(sb);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private void saveWord(StringBuilder sb) {
        if (sb.length() > 0) {
            String word = sb.toString();
            words.put(word, words.getOrDefault(word, 0) + 1);
            sb.setLength(0);
        }
    }
}