package jetlearnsystem;

import java.util.Map;

/**
 * 次の演習問題IDを決定するロジックを抽象化するインターフェース。
 */
public interface ProblemSequenceDecider {

    /**
     * 次の演習問題IDを決定する。
     * @param currentProblemId 現在の問題ID
     * @param isCorrect ユーザーが正解したかどうか (true/false)
     * @param contextData 生徒の履歴、エラーパターンなどの追加データ（将来の拡張用）
     * @return 次の問題ID、または "END"
     */
    String getNextProblemId(String currentProblemId, boolean isCorrect, Map<String, Object> contextData);
}