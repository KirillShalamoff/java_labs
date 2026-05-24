package commands;
import context.ExecutionContext;

import java.util.Arrays;

public class Minus extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        logger.info("Выполнение команды Minus с аргументами: {}", Arrays.toString(args));

        Double var1 = context.getStack().pop();
        Double var2 = context.getStack().pop();
        Double value = var2 - var1;
        context.getStack().push(value);
    }
}