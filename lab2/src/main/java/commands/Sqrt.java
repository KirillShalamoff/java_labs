package commands;
import context.ExecutionContext;

import java.util.Arrays;

import static java.lang.Math.sqrt;

public class Sqrt extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        logger.info("Выполнение команды Sqrt с аргументами: {}", Arrays.toString(args));

        Double var = context.getStack().pop();
        Double value = sqrt(var);
        context.getStack().push(value);
    }
}
