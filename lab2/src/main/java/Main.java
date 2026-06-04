import calculator.Calculator;
import factory.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(Calculator.class);

        try {


            logger.info("Инициализация калькулятора...");
            Calculator calculator = new Calculator();

            calculator.calculate(args);


        } catch (Exception e) {
            logger.fatal("Критическая ошибка запуска: " + e.getMessage(),e);
        }
    }
}
