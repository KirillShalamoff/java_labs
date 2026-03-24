public class ListNode {
    public ListNode(String word) {
        this.name = word;
        this.freq = 1;
    }

    public int getFreq() {
        return freq;
    }

    public String getName() {
        return this.name;
    }

    public double getAbsFreq() {
        return this.absFreq;
    }

    String name;
    int freq;
    double absFreq;
}
