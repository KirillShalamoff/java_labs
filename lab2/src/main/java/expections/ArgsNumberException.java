package expections;

public class ArgsNumberException extends CalculatorException {
    public ArgsNumberException(int required, String name) {
        super(String.format("не корректное число аргументов для команды %s, требуется %d!", name, required));
    }
}
