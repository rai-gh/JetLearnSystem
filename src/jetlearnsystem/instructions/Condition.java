package jetlearnsystem.instructions;

import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

/**
 * 条件式を表現するためのインターフェース。
 * 各条件式はこのインターフェースを実装し、評価ロジックを提供する。
 */
public interface Condition {
    /**
     * 条件式を評価し、その結果（真偽値）を返す。
     * @param variables 現在のスコープで利用可能な変数マップ
     * @param turtle タートルの状態 (もし条件評価でタートルの状態を使用する場合)
     * @param frame フレームへの参照 (メッセージ表示などに使用する場合)
     * @return 条件式が真であれば true、偽であれば false
     * @throws JetExecutionException 条件評価中にエラーが発生した場合
     */
    boolean evaluate(Map<String, Object> variables, Turtle turtle, TurtleFrame frame) throws JetExecutionException;
}