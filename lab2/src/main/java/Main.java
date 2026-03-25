import calculator.Calculator;
import factory.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(Calculator.class);

        try {
            logger.info("Инициализация фабрики...");
            CommandFactory factory = new CommandFactory();

            logger.info("Инициализация калькулятора...");
            Calculator calculator = new Calculator(factory);

            if (args.length == 0){
                calculator.calculate();
            } else {
                calculator.calculate(args[0]);
            }

        } catch (Exception e) {
            logger.fatal("Критическая ошибка запуска: " + e.getMessage(),e);
        }
    }
}
