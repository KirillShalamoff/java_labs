public class POP extends Command{
    @Override
    public void execute(ExecutionContext context, String[] args){
        context.getStack().pop();
    }
}
