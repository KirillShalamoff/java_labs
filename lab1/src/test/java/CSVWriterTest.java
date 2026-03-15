import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CSVWriterTest {

    @Test
    void testWrite() throws Exception {
        ArrayList<ListNode> nodes = new ArrayList<>();
        ListNode node = new ListNode("test");
        node.freq = 10;
        node.absFreq = 0.5;
        nodes.add(node);

        CSVWriter.write(nodes);

        File file = new File("table.csv");
        assertTrue(file.exists(), "Файл table.csv должен быть создан");

        List<String> lines = Files.readAllLines(file.toPath());
        assertFalse(lines.isEmpty());
        assertTrue(lines.get(0).contains("test,10,0.5"));

        // Удаляем файл после теста
        file.delete();
    }
}