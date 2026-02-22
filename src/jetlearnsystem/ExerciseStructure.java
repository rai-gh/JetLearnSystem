package jetlearnsystem;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

/**
 * 演習の出題順序と遷移ルールを保持するクラス。
 */
public class ExerciseStructure {
    private final String startId;
    private final Map<String, ProblemTransition> problems;

    public ExerciseStructure(String startId, Map<String, ProblemTransition> problems) {
        this.startId = startId;
        this.problems = problems != null ? Collections.unmodifiableMap(problems) : Collections.emptyMap();
    }

    public String getStartId() {
        return startId;
    }

    /**
     * 現在の問題IDに基づいて、次の問題IDを取得する。
     * @param currentId 現在の問題ID
     * @param isCorrect trueなら正解ルート、falseなら不正解ルート
     * @return 次の問題ID、または "END"
     */
    public String getNextProblemId(String currentId, boolean isCorrect) {
        ProblemTransition transition = problems.get(currentId);
        if (transition == null) {
            return "END";
        }
        return isCorrect ? transition.nextOnCorrect : transition.nextOnFail;
    }
    
    public static class ProblemTransition {
        public final String nextOnCorrect;
        public final String nextOnFail;
        
        public ProblemTransition(String nextOnCorrect, String nextOnFail) {
            this.nextOnCorrect = nextOnCorrect;
            this.nextOnFail = nextOnFail;
        }
    }
}