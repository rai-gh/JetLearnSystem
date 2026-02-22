package jetlearnsystem.instructions;

import java.util.List;

/**
 * ユーザー定義関数の定義（パラメータリストと命令ブロック）を保持するクラス。
 */
public class FunctionDefinition {
    private List<String> parameters;
    private List<JetInstruction> instructions;

    public FunctionDefinition(List<String> parameters, List<JetInstruction> instructions) {
        this.parameters = parameters;
        this.instructions = instructions;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<JetInstruction> getInstructions() {
        return instructions;
    }
}