package jetlearnsystem;

import jetlearnsystem.instructions.JetInstruction;
import java.util.List;
import java.util.Map;

public class CommandProcessor {
    private Turtle turtle;
    private TurtleFrame frame;
    public CommandProcessor(Turtle turtle, TurtleFrame frame) {
        this.turtle = turtle;
        this.frame = frame;
    }

    public void processExternalCommand(String command) {
        if (command.equalsIgnoreCase("終了")) {
            frame.closeFrame();
            frame.displayInfoMessage("プログラムを終了します。");
            return;
        }

        frame.displayInfoMessage("入力受付: " + command.lines().findFirst().orElse(command.substring(0, Math.min(command.length(), 30)) + (command.length() > 30 ? "..." : "")));

        new Thread(() -> {
            try {
                JetLexer lexer = new JetLexer();
                List<JetToken> tokens = lexer.tokenize(command);

                JetParser parser = new JetParser(tokens);
                List<JetInstruction> program = parser.parse();

                if (program != null && !program.isEmpty()) {
                    Map<String, Object> globalVariables = new java.util.HashMap<>();
                    
                    for (JetInstruction instruction : program) {
                        if (instruction != null) {
                            instruction.execute(turtle, frame, globalVariables);
                        }
                    }
                } else {
                    frame.displayInfoMessage("解析する命令がありませんでした。");
                }

            } catch (JetLexerException e) {
                frame.displayErrorMessage("字句解析エラー: " + e.getMessage() + " (位置: " + e.getPosition() + ")");
            } catch (JetParseException e) {
                frame.displayErrorMessage("構文解析エラー: " + e.getMessage() + " (位置: " + e.getPosition() + ")");
            } catch (JetExecutionException e) {
                frame.displayErrorMessage("実行エラー: " + e.getMessage());
            } catch (Exception e) {
                frame.displayErrorMessage("予期せぬエラーが発生しました: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}