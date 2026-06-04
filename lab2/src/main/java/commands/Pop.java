package commands;
import context.ExecutionContext;

import java.util.Arrays;

public class Pop extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        logger.info("Выполнение команды Pop с аргументами: {}", Arrays.toString(args));

        context.getStack().pop();
    }
}
