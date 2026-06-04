import java.util.ArrayList;
import java.util.Comparator;


public class StructHandler {
    public ArrayList<ListNode> nodes = new ArrayList<ListNode>();
    public int numberOfWords;

    public void saveWord(StringBuilder sb) {
        if (!sb.isEmpty()) {
            String word = sb.toString(); //это в MapHandler
            for (ListNode node : nodes) {
                if (node.name.equals(word)) {
                    node.freq++;
                    sb.setLength(0);
                    numberOfWords++;
                    return;
                }
            }
            nodes.add(new ListNode(word));
            sb.setLength(0);
            numberOfWords++;
        }
    }
    public void sort() {
        this.nodes.sort(Comparator.comparing(ListNode::getFreq));
    }

    public void countAbsFreq() {
        for (ListNode node : this.nodes) {
            node.absFreq = (double) node.freq / numberOfWords;
        }
    }
}
