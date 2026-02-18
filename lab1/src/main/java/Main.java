
public class Main {
    public static void main(String[] args){
        FileProcessor fp = new FileProcessor();
        fp.process(args);
        CSVWriter.write(fp);

    }
}
