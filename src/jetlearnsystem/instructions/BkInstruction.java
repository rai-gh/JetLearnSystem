package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class BkInstruction implements JetInstruction {
    private Object distance;

    public BkInstruction(Object distance) {
        this.distance = distance;
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        int finalDistance;

        if (distance instanceof String && ((String) distance).startsWith(":")) {
            String varName = (String) distance;
            if (!variables.containsKey(varName)) {
                throw new JetExecutionException("変数 " + varName + " が定義されていません。");
            }
            finalDistance = (Integer) variables.get(varName);
        } else {
            finalDistance = (Integer) distance;
        }

        frame.displayInfoMessage("タートルが後ろへ " + finalDistance + " 動きます。");
        turtle.bk(finalDistance);
    }
}