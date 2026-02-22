package jetlearnsystem.instructions;

import java.util.List;
import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

/**
 * IF命令("もし condition のとき [ ... ]")を表すクラス。
 */
public class IfInstruction implements JetInstruction {
    private Condition condition;
    private List<JetInstruction> ifBlockInstructions;

    public IfInstruction(Condition condition, List<JetInstruction> ifBlockInstructions) {
        this.condition = condition;
        this.ifBlockInstructions = ifBlockInstructions;
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        frame.displayInfoMessage("条件式「" + condition.toString() + "」を評価します。");
        boolean result = condition.evaluate(variables, turtle, frame);

        if (result) {
            frame.displayInfoMessage("条件が真です。ブロック内の命令を実行します。");
            for (JetInstruction instruction : ifBlockInstructions) {
                instruction.execute(turtle, frame, variables);
            }
            frame.displayInfoMessage("ブロック内の命令の実行が完了しました。");
        } else {
            frame.displayInfoMessage("条件が偽です。ブロック内の命令はスキップされます。");
        }
    }

    @Override
    public String toString() {
        return "もし " + condition.toString() + " のとき [ ... ]";
    }
}