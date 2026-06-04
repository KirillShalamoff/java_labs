package context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExecutionContext {
    private final Stack<Double> stack;
    private final Map<String, Double> variables;

    public ExecutionContext(){
        this.stack = new Stack<>();
        this.variables = new HashMap<>();
    }

    public Stack<Double> getStack(){
        return stack;
    }

    public Map<String, Double> getVariables(){
        return variables;
    }
}
