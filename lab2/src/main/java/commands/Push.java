package commands;
import context.ExecutionContext;
import expections.ArgsNumberException;

public class Push extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        if (args.length != 1) {
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
