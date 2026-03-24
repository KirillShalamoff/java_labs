import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExecutionContext {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> variables = new HashMap<>();
//constructor
    public Stack<Double> getStack(){
        return stack;
    }

    public Map<String, Double> getVariables(){
        return variables;
    }
}
