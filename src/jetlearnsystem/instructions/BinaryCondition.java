package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

/**
 * 2つのオペランドと1つの比較演算子からなる条件式を表現するクラス。
 * 例: 100 > :変数A
 */
public class BinaryCondition implements Condition {
    private Object leftOperand;
    private String operator;
    private Object rightOperand;

    public BinaryCondition(Object leftOperand, String operator, Object rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    @Override
    public boolean evaluate(Map<String, Object> variables, Turtle turtle, TurtleFrame frame) throws JetExecutionException {
        Object leftValueObj = resolveOperand(leftOperand, variables);
        Object rightValueObj = resolveOperand(rightOperand, variables);

        if (!(leftValueObj instanceof Integer) || !(rightValueObj instanceof Integer)) {
            throw new JetExecutionException("比較演算のオペランドは数値である必要があります。");
        }

        int left = (Integer) leftValueObj;
        int right = (Integer) rightValueObj;

        switch (operator) {
            case ">":
                return left > right;
            case "<":
                return left < right;
            case "=":
                return left == right;
            case "<>":
                return left != right;
            case "<=":
                return left <= right;
            case ">=":
                return left >= right;
            default:
                throw new JetExecutionException("認識できない比較演算子です: " + operator);
        }
    }

    /**
     * オペランドが変数名であれば変数マップから値を解決し、そうでなければそのままの値を返すヘルパーメソッド。
     * @param operand 評価対象のオペランド (数値または変数名)
     * @param variables 現在のスコープの変数マップ
     * @return 解決されたオペランドの値
     * @throws JetExecutionException 変数が未定義の場合
     */
    private Object resolveOperand(Object operand, Map<String, Object> variables) throws JetExecutionException {
        if (operand instanceof String && ((String) operand).startsWith(":")) {
            String varName = (String) operand;
            if (!variables.containsKey(varName)) {
                throw new JetExecutionException("条件式で参照されている変数 " + varName + " が定義されていません。");
            }
            return variables.get(varName);
        }
        return operand;
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + operator + " " + rightOperand + ")";
    }
}