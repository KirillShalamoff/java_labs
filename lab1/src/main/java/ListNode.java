public class ListNode {
    public ListNode(String word) {
        this.name = word;
        this.freq = 1;
    }

    public int getFreq() {
        return freq;
    }
//getters + setters
    String name;
    int freq;
    double absFreq;
}
