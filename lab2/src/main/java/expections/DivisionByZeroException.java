package expections;

public class DivisionByZeroException extends CalculatorException {
    public DivisionByZeroException(Double var1) {
        super(String.format("Нельзя делить %s на 0 !", var1));
    }
}
