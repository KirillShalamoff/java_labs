import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CSVWriter {
    public static void write(ArrayList<ListNode> nodes) {
        if (nodes.isEmpty()) {
            System.err.println("Nothing to write, map is empty.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("table.csv"), "UTF-8"))) {
            for (ListNode node : nodes) {

                writer.write(node.name + "," + node.freq + "," + node.absFreq + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}