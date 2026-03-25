package commands;
import context.ExecutionContext;
import expections.ArgsNumberException;

public class Define extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        if (args.length != 2) {
            throw new ArgsNumberException(2, "Define");
        }
        context.getVariables().put(args[0], Double.parseDouble(args[1]));
    }
}
