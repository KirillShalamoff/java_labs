package calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import commands.Command;
import context.ExecutionContext;
import expections.CalculatorException;
import expections.CommandNotFoundException;
import factory.CommandFactory;
import java.io.*;
import java.util.Arrays;

public class Calculator {

    private static final Logger logger = LogManager.getLogger(Calculator.class);

    private final CommandFactory factory;
    private final ExecutionContext context;

    public Calculator(){
        logger.info("Инициализация фабрики...");
        this.factory = new CommandFactory();
        this.context = new ExecutionContext();
    }

    public void calculate(String[] args){
        final Logger logger = LogManager.getLogger(Calculator.class);

        InputStream is;
        if (args.length == 0){
            is = System.in;
        } else {
            try {
                is = new FileInputStream("src/main/resources/" + args[0]);
            } catch (FileNotFoundException e) {
                logger.info("Файл не найден, перехожу на ввод из консоли...", e);
                is = System.in;
            }
        }

        logger.info("Калькулятор готов к работе.");

        try(BufferedReader reader = new BufferedReader( new InputStreamReader(is)))
        {
            String line;

            while((line = reader.readLine()) != null){
                try{
                    if (line.equals("q")) break;
                    processLine(line);
                } catch (CalculatorException e) {
                    logger.info("Ошибка: " + e.getMessage(), e);

                }
            }




        }
        catch (IOException e)
        {
            logger.error("Error while reading file: " + e.getLocalizedMessage());
        }
    }

    private void processLine(String line) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        String[] words = line.split("\\s+");
        String commandName = words[0]; // Приводим к верхнему регистру для надежности

        Command cmd = factory.getCommand(commandName);

        if (cmd == null) {
            throw new CommandNotFoundException(commandName);
        }

        String[] args = Arrays.copyOfRange(words, 1, words.length);

        try {
            cmd.execute(context, args);
        } catch (CalculatorException e) {
            // Если команда сломалась (например, деление на 0), печатаем и идем дальше
            logger.info("Ошибка при выполнении " + commandName + ": " + e.getMessage(), e);
        }
    }
}
