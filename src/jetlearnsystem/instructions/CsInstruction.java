package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class CsInstruction implements JetInstruction {
    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        frame.displayInfoMessage("タートルを初期状態に戻し、画面をクリアしました。");
        frame.clear();
    }
}