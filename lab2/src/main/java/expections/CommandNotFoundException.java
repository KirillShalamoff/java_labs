package expections;

public class CommandNotFoundException extends CalculatorException {
    public CommandNotFoundException(String commandName) {
        super(String.format("Команда %s не найдена!", commandName));
    }
}
