package commands;
import context.ExecutionContext;

public class Pop extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        context.getStack().pop();
    }
}
