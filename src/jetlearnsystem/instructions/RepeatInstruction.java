package jetlearnsystem.instructions;

import java.util.List;
import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class RepeatInstruction implements JetInstruction {
    private Object times;
    private List<JetInstruction> instructionsToRepeat;

    public RepeatInstruction(Object times, List<JetInstruction> instructions) {
        this.times = times;
        this.instructionsToRepeat = instructions;
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        int finalTimes;

        if (times instanceof String && ((String) times).startsWith(":")) {
            String varName = (String) times;
            if (!variables.containsKey(varName)) {
                throw new JetExecutionException("変数 " + varName + " が定義されていません。");
            }
            finalTimes = (Integer) variables.get(varName);
        } else {
            finalTimes = (Integer) times;
        }

        frame.displayInfoMessage(finalTimes + "回の繰り返しを開始します。");
        for (int i = 0; i < finalTimes; i++) {
            for (JetInstruction instruction : this.instructionsToRepeat) {
                instruction.execute(turtle, frame, variables);
            }
        }
        frame.displayInfoMessage("繰り返しの実行が完了しました。");
    }
}