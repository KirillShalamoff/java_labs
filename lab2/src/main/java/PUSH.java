public class PUSH extends Command{
    @Override
    public void execute(ExecutionContext context, String[] args) {
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
