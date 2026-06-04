import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class TXTReaderTest {

    @TempDir
    Path tempDir; // Создает временную папку для теста

    @Test
    void testReadFile() throws Exception {
        // Создаем временный файл.
        StructHandler handler = new StructHandler();

        // Эмуляция данных
        StringBuilder sb = new StringBuilder("hello world hello");
        handler.saveWord(new StringBuilder("hello"));
        handler.saveWord(new StringBuilder("world"));

        assertEquals(2, handler.nodes.size());
    }
}