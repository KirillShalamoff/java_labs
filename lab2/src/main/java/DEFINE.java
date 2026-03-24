public class DEFINE extends Command {
    @Override
    public void execute(ExecutionContext context, String[] args){
        context.getVariables().put(args[0], Double.parseDouble(args[1]));
    }
}
