import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StructHandlerTest {
    private StructHandler handler;

    @BeforeEach
    void setUp() {
        handler = new StructHandler();
    }

    @Test
    void testSaveWord_NewAndExisting() {
        handler.saveWord(new StringBuilder("apple"));
        handler.saveWord(new StringBuilder("apple"));
        handler.saveWord(new StringBuilder("banana"));

        assertEquals(2, handler.nodes.size());
        assertEquals(2, handler.nodes.get(0).getFreq()); // apple
        assertEquals(1, handler.nodes.get(1).getFreq()); // banana
        assertEquals(3, handler.numberOfWords);
    }

    @Test
    void testCountAbsFreq() {
        handler.saveWord(new StringBuilder("a")); // 1
        handler.saveWord(new StringBuilder("b")); // 1
        handler.numberOfWords = 2;

        handler.countAbsFreq();

        assertEquals(0.5, handler.nodes.get(0).absFreq, 0.001);
    }

    @Test
    void testSort() {
        handler.saveWord(new StringBuilder("rare")); // freq 1
        handler.saveWord(new StringBuilder("often"));
        handler.saveWord(new StringBuilder("often")); // freq 2

        handler.sort();

        assertEquals("rare", handler.nodes.get(0).name);
        assertEquals("often", handler.nodes.get(1).name);
    }
}