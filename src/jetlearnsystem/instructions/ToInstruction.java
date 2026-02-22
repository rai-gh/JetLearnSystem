package jetlearnsystem.instructions;

import java.util.List;
import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.JetParser;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

/**
 * 関数定義命令("関数 NAME を作る ...")を表すクラス。
 */
public class ToInstruction implements JetInstruction {
    private String functionName;
    private FunctionDefinition functionDefinition;

    public ToInstruction(String name, List<String> params, List<JetInstruction> instructions) {
        this.functionName = name;
        this.functionDefinition = new FunctionDefinition(params, instructions);
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        JetParser.getFunctionTable().put(this.functionName, this.functionDefinition);
        frame.displayInfoMessage("関数 '" + this.functionName + "' を定義しました。");
    }

    @Override
    public String toString() {
        return "関数 " + functionName + " を作る";
    }
}