import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {
    private FileProcessor fp;
    private final String TEST_FILE_NAME = "temp_test_file.txt";
    private final File resourceFile = new File("src/main/resources/" + TEST_FILE_NAME);

    @BeforeEach
    void setUp() {
        fp = new FileProcessor();
    }

    @AfterEach
    void tearDown() {
        if (resourceFile.exists()) {
            resourceFile.delete();
        }
    }

    @Test
    void testProcessWithFileInResources() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resourceFile))) {
            bw.write("Java JUnit Java Test");
        }

        fp.process(new String[]{TEST_FILE_NAME});

        assertNotNull(fp.words.get("Java"));
        assertEquals(2, fp.words.get("Java"), "Слово 'Java' должно встретиться 2 раза");
        assertEquals(1, fp.words.get("JUnit"), "Слово 'JUnit' должно встретиться 1 раз");
    }

    @Test
    void testSpecialSymbolsInResources() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resourceFile))) {
            bw.write("apple,orange.apple");
        }

        fp.process(new String[]{TEST_FILE_NAME});

        assertEquals(2, fp.words.get("apple"));
        assertEquals(1, fp.words.get("orange"));
    }
}