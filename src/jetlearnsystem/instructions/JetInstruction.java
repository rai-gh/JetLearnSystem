package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public interface JetInstruction {
    void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException;
}