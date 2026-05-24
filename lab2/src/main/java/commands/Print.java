package commands;
import context.ExecutionContext;
import expections.ArgsNumberException;

import java.util.Arrays;

public class Print extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        logger.info("Выполнение команды Print с аргументами: {}", Arrays.toString(args));

        if (args.length != 0) {
            logger.error("Ошибка аргументов в Print: ожидалось 0, получено {}", args.length);
            throw new ArgsNumberException(0, "Print");
        }
        System.out.println(context.getStack().peek());
    }
}
