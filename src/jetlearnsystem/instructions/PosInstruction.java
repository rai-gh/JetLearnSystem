package jetlearnsystem.instructions;

import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;
import java.util.Map;

/**
 * 現在のタートルのX, Y座標と角度をフィードバックエリアに出力する命令。
 */
public class PosInstruction implements JetInstruction {

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> globalVariables) throws JetExecutionException {
        double x = turtle.getDoubleX();
        double y = turtle.getDoubleY();
        double angle = turtle.getDoubleAngle();
        
        frame.displayInfoMessage(
            String.format("現在のタートル座標: X=%.2f, Y=%.2f, 角度=%.1f度", x, y, angle)
        );
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public String toString() {
        return "座標を表示";
    }
}