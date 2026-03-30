public class FileProcessor {

    public static void process(String[] args) {
        if (args.length > 0) {
            StructHandler container = new StructHandler();
            TXTReader.readFile(args[0], container);
            container.sort();
            container.countAbsFreq();
            CSVWriter.write(container.nodes);
        } else {
            System.err.println("FileName argument is missing!");
        }
    }
}