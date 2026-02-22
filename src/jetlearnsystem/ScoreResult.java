package jetlearnsystem;

/**
 * 採点結果を保持するクラス。
 */
public class ScoreResult {
    private final boolean isCorrect;
    private final String message;
    private final long timeTakenMs;
    private final boolean isTimeUp;

    /**
     * 4引数のコンストラクタを定義し、ExerciseControllerからの呼び出しと一致させる
     */
    public ScoreResult(boolean isCorrect, String message, long timeTakenMs, boolean isTimeUp) {
        this.isCorrect = isCorrect;
        this.message = message;
        this.timeTakenMs = timeTakenMs;
        this.isTimeUp = isTimeUp;
    }
    
    public boolean isCorrect() {
        return isCorrect;
    }

    public String getMessage() {
        return message;
    }
    
    public long getTimeTakenMs() { 
        return timeTakenMs; 
    }
    
    public boolean isTimeUp() {
        return isTimeUp;
    }
}