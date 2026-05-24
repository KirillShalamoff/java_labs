package commands;
import context.ExecutionContext;
import expections.CommandNotFoundException;
import expections.DivisionByZeroException;
import expections.ArgsNumberException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Command {
    protected final Logger logger = LogManager.getLogger(this.getClass());
    public void execute(ExecutionContext context, String[] args)
            throws DivisionByZeroException, CommandNotFoundException, ArgsNumberException {}
}
