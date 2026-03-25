package commands;
import context.ExecutionContext;
import expections.ArgsNumberException;

public class Print extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        if (args.length != 0) {
            throw new ArgsNumberException(0, "Print");
        }
        System.out.println(context.getStack().peek());
    }
}
