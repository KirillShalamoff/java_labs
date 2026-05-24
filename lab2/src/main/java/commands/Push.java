package commands;
import context.ExecutionContext;
import expections.ArgsNumberException;

import java.util.Arrays;

public class Push extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        logger.info("Выполнение команды Push с аргументами: {}", Arrays.toString(args));

        if (args.length != 1) {
            logger.error("Ошибка аргументов в Push: ожидалось 1, получено {}", args.length);
            throw new ArgsNumberException(1, "Push");
        }
        String input = args[0];
        Double value;

        if(context.getVariables().containsKey(input)) {
            value = context.getVariables().get(input);
        } else {
            value = Double.parseDouble(input);
        }

        context.getStack().push(value);
    }
}
