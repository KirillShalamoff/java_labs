public class MULT extends Command{
    @Override
    public void execute(ExecutionContext context, String[] args) {
        Double var1 = context.getStack().pop();
        Double var2 = context.getStack().pop();
        Double value = var2 * var1;
        context.getStack().push(value);
    }
}