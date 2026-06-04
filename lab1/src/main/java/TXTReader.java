import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TXTReader {
    public static void readFile(String fileName, StructHandler container) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/"+fileName), "UTF-8"))) {
            StringBuilder sb = new StringBuilder();
            int c;

            while ((c = br.read()) != -1) {
                char ch = (char) c;

                if (Character.isLetterOrDigit(ch)) {
                    sb.append(ch);
                }
                else {
                    container.saveWord(sb);
                }
            }
            container.saveWord(sb);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
