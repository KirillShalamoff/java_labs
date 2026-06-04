package commands;
import context.ExecutionContext;
import expections.DivisionByZeroException;

import java.util.Arrays;

public class Div extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        logger.info("Выполнение команды Define с аргументами: {}", Arrays.toString(args));

        Double var1 = context.getStack().pop();
        Double var2 = context.getStack().pop();
        if(var1 == 0.0){
            logger.error("Ошибка в Div: деление на ноль");
            throw new DivisionByZeroException(var2);
        }
        Double value = var2 / var1;
        context.getStack().push(value);
    }
}