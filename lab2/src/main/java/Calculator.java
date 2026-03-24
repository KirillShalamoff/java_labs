import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {

    private CommandFactory factory;
    private ExecutionContext context;

    public Calculator(CommandFactory factory){
        this.factory = factory;
        this.context = new ExecutionContext();
    }

    public void calculate(String filename){

        try(BufferedReader reader = new BufferedReader( new FileReader("src/main/resources/" + filename)))
        {
            String line;

            while((line = reader.readLine()) != null){
                try{
                    processLine(line);
                } catch (CalculatorExpection e) {
                    System.err.println("Ошибка: " + e.getMessage());

                }
            }


        }
        catch (IOException e)
        {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
    }

    private void processLine(String line) {
        // 1. Очищаем строку от пробелов по краям
        line = line.trim();

        // 2. Игнорируем пустые строки и комментарии (если они начинаются на #)
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        // 3. Режем на слова
        String[] words = line.split("\\s+");
        String commandName = words[0].toUpperCase(); // Приводим к верхнему регистру для надежности

        // 4. Просим фабрику дать команду
        Command cmd = factory.getCommand(commandName);

        // 5. ГЛАВНАЯ ПРОВЕРКА
        if (cmd == null) {
            throw new CalculatorExpection("Команда " + commandName + " не поддерживается");
        }

        // 6. Выделяем аргументы
        String[] args = Arrays.copyOfRange(words, 1, words.length);

        // 7. Оборачиваем выполнение в try-catch (как просили в методичке)
        try {
            cmd.execute(context, args);
        } catch (CalculatorExpection e) {
            // Если команда сломалась (например, деление на 0), печатаем и идем дальше
            System.err.println("Ошибка при выполнении " + commandName + ": " + e.getMessage());
        }
    }
}
