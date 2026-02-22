package jetlearnsystem;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import jetlearnsystem.instructions.JetInstruction;
import jetlearnsystem.ExerciseController.ExerciseUIUpdater;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.Collections;

/**
 * 演習モード全体の流れとロジックを制御するクラス。
 * 問題の読み込み、タイマー管理、採点処理を行う。
 */
public class ExerciseController {
    private ProgramExecutionController programController;
    private TurtleFrame turtleFrame;
    private ProblemLoader problemLoader;
    private ProblemDefinition currentProblem;
    private ProblemSequenceDecider sequenceDecider;
    private ExerciseUIUpdater uiUpdater; 
    private Component currentFrame;
    
    public interface ExerciseUIUpdater {
        void displayProblem(ProblemDefinition problem);
        void displayResult(ScoreResult result);
        void notifyExecutionStart();
        void notifyExecutionEnd();
        void showSelfScorePopup(String modelImage);
    }
    
    public ExerciseController(ProgramExecutionController programController, TurtleFrame turtleFrame, ExerciseUIUpdater uiUpdater, Component currentFrame) {
        this.programController = programController;
        this.turtleFrame = turtleFrame;
        this.uiUpdater = uiUpdater;
        this.currentFrame = currentFrame; 
        this.problemLoader = new ProblemLoader();
        
        try {
            this.sequenceDecider = new BasicSequenceDecider(); 
        } catch (RuntimeException e) {
            turtleFrame.displayErrorMessage("致命的なエラー: 演習構造ファイルの初期化に失敗しました。");
            e.printStackTrace();
            this.sequenceDecider = (currentId, isCorrect, contextData) -> "END"; 
        }
    }
    
    /**
    * 演習構造ファイルに定義されている開始問題IDを取得する。
    * @return 開始問題ID
    */
    public String getStartProblemId() {
        if (sequenceDecider instanceof BasicSequenceDecider) {
            return ((BasicSequenceDecider) sequenceDecider).getExerciseStructure().getStartId();
        }
        return "END"; 
    }

    /**
     * 新しい演習を開始する。問題形式に応じて画面を切り替える。
     * @param problemId 開始する問題のID
     */
    public void startExercise(String problemId) {
        
        try {
            // 1. 問題定義を読み込む
            currentProblem = problemLoader.loadProblem(problemId);
            
            // 2. UIを初期化し、問題を表示する
            uiUpdater.displayProblem(currentProblem);
            
            // 3. ゴースト描画の実行
            executeGhostDrawing(currentProblem.getModelCode());
        } catch (IOException e) {
            uiUpdater.displayResult(new ScoreResult(false, "問題ファイルの読み込みエラーが発生しました。", 0L, false));
            e.printStackTrace();
        } catch (RuntimeException e) {
            uiUpdater.displayResult(new ScoreResult(false, "問題データの構造にエラーがあります。", 0L, false));
            e.printStackTrace();
        }
    }
       
    /**
     * 模範解答コードをゴーストモードで実行し、背景に軌跡を描画する。
     */
    private void executeGhostDrawing(String expectedCode) {
        new Thread(() -> {
            try {
                Turtle turtle = turtleFrame.getTurtle();
                if (turtle == null) return;

                turtleFrame.clearGhostHistory();
                
                turtle.setGhostMode(true);
                turtle.setAnimationEnabled(false);
                turtle.resetState();
                
                if (expectedCode == null || expectedCode.trim().isEmpty()) return;

                JetLexer lexer = new JetLexer();
                List<JetToken> tokens = lexer.tokenize(expectedCode);
                JetParser parser = new JetParser(tokens);
                List<JetInstruction> fullProgram = parser.parse();
                
                Map<String, Object> dummyVariables = new HashMap<>();
                for (JetInstruction instruction : fullProgram) {
                    if (instruction != null) {
                        instruction.execute(turtle, turtleFrame, dummyVariables);
                    }
                }
                turtleFrame.repaint();

            } catch (Exception e) {
                turtleFrame.displayErrorMessage("ゴースト描画中にエラー: " + e.getMessage());
            } finally {
                Turtle turtle = turtleFrame.getTurtle();
                if (turtle != null) {
                    turtle.setGhostMode(false);
                    turtle.setAnimationEnabled(true);
                    turtle.resetState();
                }
            }
        }).start();
    }

    public String getCurrentProblemId() {
        return currentProblem.getId();
    }
    
    /**
    * 自己採点の結果に応じて次のアクションを決定する。
    * @param action "CORRECT" または "INCORRECT" または "END"
    */
    public void handleSelfScoreChoice(String action) { 
        if ("CORRECT".equals(action)) {
            moveToNextProblem(true);
        } else if ("INCORRECT".equals(action)) {
            moveToNextProblem(false);
        } else if ("END".equals(action)) {
            if (currentFrame instanceof JFrame) {
                 ((JFrame)currentFrame).dispose();
            }
            new HomeFrame().setVisible(true);
            JOptionPane.showMessageDialog(null, "演習を終了します。", "終了", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * 正解/不正解の結果に応じて次の問題へ遷移させる。
     * DrawingFrameとSelectImageFrameの両方から呼び出される。
     */
    public void moveToNextProblem(boolean isCorrect) {
        if (currentProblem == null || sequenceDecider == null) {
            // currentFrameがJFrameなら、HomeFrameに戻る
            if (currentFrame instanceof JFrame) {
                ((JFrame)currentFrame).dispose();
            }
            new HomeFrame().setVisible(true);
            JOptionPane.showMessageDialog(null, "演習構造が読み込まれていないため、Homeに戻ります。", "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nextId = sequenceDecider.getNextProblemId(currentProblem.getId(),isCorrect,Collections.emptyMap());

        if ("END".equals(nextId)) {
            if (currentFrame instanceof JFrame) {
                 ((JFrame)currentFrame).dispose();
            } else {
                ((JFrame)SwingUtilities.getWindowAncestor(currentFrame)).dispose();
            }
            new HomeFrame().setVisible(true);
            
            String endMessage = isCorrect ? "全ての演習をクリアしました！素晴らしい！" : "演習を終了します。";
            JOptionPane.showMessageDialog(null, endMessage, "終了", JOptionPane.INFORMATION_MESSAGE);
            
        } else {
            startExercise(nextId); 
        }
    }
    
    /**
     * ユーザーコードを実行せずに、採点フロー（結果表示と模範解答表示）を開始する。
     * @param codeText ユーザーが入力したプログラムコード（再解析用）
     */
    public void startScoringFlow(String codeText) {
        if (currentProblem == null) {
            uiUpdater.displayResult(new ScoreResult(false, "問題が読み込まれていません。", 0L, false));
            return;
        }

        uiUpdater.notifyExecutionStart();
        uiUpdater.notifyExecutionEnd();

        ScoreResult result = new ScoreResult(
            true, 
            "実行結果と目標図を比較してください。", 
            0L, 
            false
        );
        
        uiUpdater.displayResult(result);
        
        uiUpdater.showSelfScorePopup(currentProblem.getModelImage()); 
    }
}