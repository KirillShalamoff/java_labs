package commands;
import context.ExecutionContext;
import expections.ArgsNumberException;

import java.util.Arrays;

public class Define extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        logger.info("Выполнение команды Define с аргументами: {}", Arrays.toString(args));
        if (args.length != 2) {
            logger.error("Ошибка аргументов в Define: ожидалось 2, получено {}", args.length);
            throw new ArgsNumberException(2, "Define");
        }
        context.getVariables().put(args[0], Double.parseDouble(args[1]));
    }
}
