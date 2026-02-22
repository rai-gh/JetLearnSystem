package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class LtInstruction implements JetInstruction {
    private Object angle;

    public LtInstruction(Object angle) {
        this.angle = angle;
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        int finalAngle;

        if (angle instanceof String && ((String) angle).startsWith(":")) {
            String varName = (String) angle;
            if (!variables.containsKey(varName)) {
                throw new JetExecutionException("変数 " + varName + " が定義されていません。");
            }
            finalAngle = (Integer) variables.get(varName);
        } else {
            finalAngle = (Integer) angle;
        }

        frame.displayInfoMessage("タートルが左へ " + finalAngle + " 度回転します。");
        turtle.lt(finalAngle);
    }
}