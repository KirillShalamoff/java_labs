
import static java.lang.Math.sqrt;

public class SQRT extends Command{
    @Override
    public void execute(ExecutionContext context, String[] args){
        Double var = context.getStack().pop();
        Double value = sqrt(var);
        context.getStack().push(value);
    }
}
