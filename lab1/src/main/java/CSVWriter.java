import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CSVWriter {
    public static void write(FileProcessor fp) {
        if (fp.words.isEmpty()) {
            System.err.println("Nothing to write, map is empty.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("table.csv"), "UTF-8"))) {
            double total = fp.words.values().stream().mapToInt(Integer::intValue).sum();
            Set<Map.Entry<String, Integer>> entries = fp.words.entrySet();
            List<Map.Entry<String, Integer>> list = new ArrayList<>(entries);
            list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

            for (Map.Entry<String, Integer> entry : list) {
                double frequency = entry.getValue() / total; // Теперь будет дробное число
                writer.write(entry.getKey() + "," + entry.getValue() + "," + frequency + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}