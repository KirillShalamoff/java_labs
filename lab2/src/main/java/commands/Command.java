package commands;
import context.ExecutionContext;
import expections.CommandNotFoundException;
import expections.DivisionByZeroException;
import expections.ArgsNumberException;

public class Command {
    public void execute(ExecutionContext context, String[] args)
            throws DivisionByZeroException, CommandNotFoundException, ArgsNumberException {}
}
