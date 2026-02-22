package jetlearnsystem;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import jetlearnsystem.instructions.JetInstruction;

/**
 * JETプログラムの実行ロジックを管理するコントローラークラス。
 * GUIから独立しており、他のフレームでも再利用可能。
 */
public class ProgramExecutionController {

    private Turtle turtle;
    private TurtleFrame turtleFrame;

    private List<JetInstruction> parsedProgram;
    private int currentInstructionIndex;
    private Map<String, Object> globalVariables;

    private List<Integer> historyIndices;

    public interface ButtonStateUpdater {
        void updateButtons(boolean canRun, boolean canRunAll, boolean canNext, boolean canBack, boolean canReset);
    }

    private ButtonStateUpdater buttonStateUpdater;

    public ProgramExecutionController(Turtle turtle, TurtleFrame turtleFrame, ButtonStateUpdater buttonStateUpdater) {
        this.turtle = turtle;
        this.turtleFrame = turtleFrame;
        this.buttonStateUpdater = buttonStateUpdater;
        resetExecutionState(true);
    }

    public List<JetInstruction> getParsedProgram() {
        return parsedProgram;
    }

    public int getCurrentInstructionIndex() {
        return currentInstructionIndex;
    }

    private void resetExecutionState() {
        resetExecutionState(true);
    }

    /**
     * 実行状態をリセットする。
     * @param updateButtons trueの場合、リセット後にボタンの状態を更新する
     */
    private void resetExecutionState(boolean updateButtons) {
        parsedProgram = new ArrayList<>();
        currentInstructionIndex = 0;
        globalVariables = new HashMap<>();
        historyIndices = new ArrayList<>();
        if (updateButtons) {
            updateButtonStates(false);
        }
    }
    
    /**
     * UIのボタン状態を更新するヘルパーメソッド。
     * UI更新をEDT（Event Dispatch Thread）で安全に実行する。
     * @param running 現在プログラムが実行中かどうか
     */
    private void updateButtonStates(boolean running) {
        if (buttonStateUpdater != null) {
            final boolean canRun = !running;
            final boolean canRunAll = !running;
            final boolean canNext = !running && (parsedProgram != null && currentInstructionIndex < parsedProgram.size());
            final boolean canBack = !running && !historyIndices.isEmpty();
            final boolean canReset = !running;

            javax.swing.SwingUtilities.invokeLater(() -> {
                buttonStateUpdater.updateButtons(canRun, canRunAll, canNext, canBack, canReset);
            });
        }
    }

    /**
     * すべてのボタン操作の前に実行し、UIの更新（ボタン無効化）を待つヘルパーメソッド。
     * 非同期実行処理でのみ使用する。
     */
    private boolean waitForButtonsDisabled() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                if (buttonStateUpdater != null) {
                    buttonStateUpdater.updateButtons(false, false, false, false, false);
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            updateButtonStates(false);
            return false;
        }
    }

    public void handleResetAction() {
        new Thread(() -> {
            if (!waitForButtonsDisabled()) return;

            try {
                turtleFrame.clear();
                turtle.setAnimationEnabled(false);
                turtle.resetState();
                JetParser.getFunctionTable().clear();
                resetExecutionState(false);
                turtleFrame.displayInfoMessage("キャンバスと関数定義をリセットしました。");
            } finally {
                turtle.setAnimationEnabled(true);
                updateButtonStates(false); 
            }
        }).start();
    }

    public void handleRunAction(String programText) {
        new Thread(() -> {
            if (!waitForButtonsDisabled()) return;

            try {
                if (programText == null || programText.trim().isEmpty()) {
                    turtleFrame.displayInfoMessage("実行するプログラムがありません。");
                    return;
                }

                resetExecutionState(false);
                turtleFrame.clear();
                turtle.setAnimationEnabled(true);
                turtle.resetState();
                JetParser.getFunctionTable().clear();

                turtleFrame.displayInfoMessage("プログラムを解析して実行準備をします...");
                
                JetLexer lexer = new JetLexer();
                List<JetToken> tokens = lexer.tokenize(programText);
                JetParser parser = new JetParser(tokens);
                parsedProgram = parser.parse();
                
                if (parsedProgram.isEmpty()) {
                    turtleFrame.displayInfoMessage("解析する命令がありませんでした。");
                } else {
                    turtleFrame.displayInfoMessage("プログラムの解析が完了しました。Nextボタンでステップ実行を開始してください。");
                }
            } catch (JetLexerException e) {
                turtleFrame.displayErrorMessage("字句解析エラー: " + e.getMessage() + " (位置: " + e.getPosition() + ")");
                resetExecutionState(false);
            } catch (JetParseException e) {
                turtleFrame.displayErrorMessage("構文解析エラー: " + e.getMessage() + " (位置: " + e.getPosition() + ")");
                resetExecutionState(false);
            } catch (Exception e) {
                turtleFrame.displayErrorMessage("予期せぬエラーが発生しました: " + e.getMessage());
                e.printStackTrace();
                resetExecutionState(false);
            } finally {
                updateButtonStates(false); 
            }
        }).start();
    }

    public void handleNextAction() {
        new Thread(() -> {
            if (!waitForButtonsDisabled()) return;

            try {
                if (parsedProgram == null || parsedProgram.isEmpty() || currentInstructionIndex >= parsedProgram.size()) {
                    turtleFrame.displayInfoMessage("これ以上実行する命令はありません。");
                    return;
                }

                historyIndices.add(currentInstructionIndex);
                turtle.setAnimationEnabled(true); 
                
                JetInstruction instruction = parsedProgram.get(currentInstructionIndex);
                turtleFrame.displayInfoMessage("ステップ実行中 (" + (currentInstructionIndex + 1) + "/" + parsedProgram.size() + "): " + instruction.toString());
                instruction.execute(turtle, turtleFrame, globalVariables);
                currentInstructionIndex++;

                if (currentInstructionIndex >= parsedProgram.size()) {
                    turtleFrame.displayInfoMessage("プログラムの実行が完了しました。");
                }
            } catch (JetExecutionException e) {
                turtleFrame.displayErrorMessage("実行エラー (ステップ " + (currentInstructionIndex + 1) + "): " + e.getMessage());
                currentInstructionIndex = parsedProgram.size();
            } catch (Exception e) {
                turtleFrame.displayErrorMessage("予期せぬエラー (ステップ " + (currentInstructionIndex + 1) + "): " + e.getMessage());
                e.printStackTrace();
                currentInstructionIndex = parsedProgram.size();
            } finally {
                updateButtonStates(false); 
            }
        }).start();
    }

    public void handleBackAction() {
        new Thread(() -> {
            if (!waitForButtonsDisabled()) return;
            
            try {
                if (historyIndices.isEmpty()) {
                    turtleFrame.displayInfoMessage("これ以上戻るステップはありません。");
                    return;
                }

                int previousIndex = historyIndices.remove(historyIndices.size() - 1);

                turtleFrame.displayInfoMessage("ステップ実行を巻き戻します...");
                turtleFrame.clear();
                
                turtle.setAnimationEnabled(false);
                turtle.resetState();
                globalVariables.clear();
                JetParser.getFunctionTable().clear();

                for (int i = 0; i < previousIndex; i++) {
                    parsedProgram.get(i).execute(turtle, turtleFrame, globalVariables);
                }

                if (turtleFrame != null) {
                    turtleFrame.repaint();
                }

                currentInstructionIndex = previousIndex;
                turtleFrame.displayInfoMessage("ステップ " + (currentInstructionIndex + 1) + " の状態に戻りました。");
            } catch (JetExecutionException e) {
                turtleFrame.displayErrorMessage("巻き戻し中にエラーが発生しました: " + e.getMessage());
                resetExecutionState(false);
            } finally {
                turtle.setAnimationEnabled(true);
                updateButtonStates(false); 
            }
        }).start();
    }

    /**
    * プログラム全体を解析し、アニメーションを伴って実行する。
    * この実行は採点フローとは独立している。
    */
    public void handleRunAllAction(String programText) {
        new Thread(() -> {

            try {
                if (programText == null || programText.trim().isEmpty()) {
                    turtleFrame.displayInfoMessage("実行するプログラムがありません。");
                    return;
                }

                resetExecutionState(false); 
                turtleFrame.clear();
                turtle.setAnimationEnabled(true);
                turtle.resetState();
                JetParser.getFunctionTable().clear();

                turtleFrame.displayInfoMessage("プログラムをすべて実行します...");

                JetLexer lexer = new JetLexer();
                List<JetToken> tokens = lexer.tokenize(programText);
                JetParser parser = new JetParser(tokens);
                List<JetInstruction> fullProgram = parser.parse();

                if (fullProgram.isEmpty()) {
                    turtleFrame.displayInfoMessage("解析する命令がありませんでした。");
                } else {
                    Map<String, Object> runallGlobalVariables = new HashMap<>();
                    for (JetInstruction instruction : fullProgram) {
                        if (instruction != null) {
                            instruction.execute(turtle, turtleFrame, runallGlobalVariables);
                        }
                    }
                    turtleFrame.displayInfoMessage("プログラムの実行が完了しました。");
                    if (turtleFrame != null) {
                    }
                }
            } catch (Exception e) {
                turtleFrame.displayErrorMessage("実行エラー: " + e.getMessage());
                e.printStackTrace();
            } finally {
                updateButtonStates(false); 
            }
        }).start();
    }
}