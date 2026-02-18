import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CSVWriterTest {

    @Test
    void testSortingAndOutput() throws Exception {
        FileProcessor fp = new FileProcessor();
        // Наполняем мапу вручную для теста
        fp.words.put("rare", 1);
        fp.words.put("common", 10);
        fp.words.put("medium", 5);

        CSVWriter.write(fp);

        File file = new File("table.csv");
        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        // Проверяем порядок (сортировку по убыванию)
        // Первая строка должна быть про "common", так как их 10
        assertTrue(lines.get(0).contains("common"), "Первым должно идти самое частое слово");
        assertTrue(lines.get(2).contains("rare"), "Последним должно идти самое редкое слово");
    }
}