package commands;
import context.ExecutionContext;
import expections.DivisionByZeroException;

public class Div extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args) {
        Double var1 = context.getStack().pop();
        Double var2 = context.getStack().pop();
        if(var1 == 0.0){
            throw new DivisionByZeroException(var2);
        }
        Double value = var2 / var1;
        context.getStack().push(value);
    }
}