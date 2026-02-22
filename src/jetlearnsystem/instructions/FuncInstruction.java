package jetlearnsystem.instructions;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.JetParser;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class FuncInstruction implements JetInstruction {
    private String functionName;
    private List<Object> arguments;

    public FuncInstruction(String functionName, List<Object> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        // 1. グローバルな関数テーブルから、呼び出すべき関数の定義を取得
        FunctionDefinition funcDef = JetParser.getFunctionTable().get(functionName);
        if (funcDef == null) {
            throw new JetExecutionException("関数 '" + functionName + "' は定義されていません。");
        }

        // 2. パラメータと引数の数が合っているかチェック
        List<String> paramNames = funcDef.getParameters();
        if (paramNames.size() != arguments.size()) {
            throw new JetExecutionException("関数 '" + functionName + "' の引数の数が違います。");
        }

        // 3. この関数呼び出し専用のローカル変数スコープを作成
        Map<String, Object> localVariables = new HashMap<>();

        // 4. ローカルスコープに、パラメータと引数をセット
        for (int i = 0; i < paramNames.size(); i++) {
            String paramName = paramNames.get(i);
            Object argumentValue = arguments.get(i);
            localVariables.put(paramName, argumentValue);
        }

        // 5. 関数の本体（命令リスト）を、ローカルスコープを使って実行
        frame.displayInfoMessage("関数 '" + functionName + "' を実行します。");
        for (JetInstruction instruction : funcDef.getInstructions()) {
            instruction.execute(turtle, frame, localVariables);
        }
        frame.displayInfoMessage("関数 '" + functionName + "' の実行が完了しました。");
    }
}