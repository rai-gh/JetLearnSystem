package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class WaitInstruction implements JetInstruction {
    private Object duration;

    public WaitInstruction(Object duration) {
        this.duration = duration;
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        int finalDuration;

        if (duration instanceof String && ((String) duration).startsWith(":")) {
            String varName = (String) duration;
            if (!variables.containsKey(varName)) {
                throw new JetExecutionException("変数 " + varName + " が定義されていません。");
            }
            Object value = variables.get(varName);
            if (!(value instanceof Integer)) {
                throw new JetExecutionException("変数 " + varName + " は数値でなければなりません。");
            }
            finalDuration = (Integer) value;
        } else if (duration instanceof Integer) {
            finalDuration = (Integer) duration;
        } else {
            throw new JetExecutionException("待機時間は数値または変数である必要があります。");
        }

        if (finalDuration < 0) {
            throw new JetExecutionException("待機時間は負の値にできません。");
        }

        frame.displayInfoMessage(finalDuration + "ミリ秒待機します。");
        try {
            Thread.sleep(finalDuration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new JetExecutionException("待機中に中断されました。", e);
        }
    }

    @Override
    public String toString() {
        return "待機 " + duration;
    }
}