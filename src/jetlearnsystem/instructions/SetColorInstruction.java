package jetlearnsystem.instructions;

import java.awt.Color;
import java.util.Map;
import jetlearnsystem.JetExecutionException;
import jetlearnsystem.Turtle;
import jetlearnsystem.TurtleFrame;

public class SetColorInstruction implements JetInstruction {
    private String colorName;
    private Color color;

    /**
     * コンストラクタ。色名を受け取り、対応するColorオブジェクトを準備する。
     * @param colorName "赤", "あか" などの色名
     */
    public SetColorInstruction(String colorName) {
        this.colorName = colorName;
        this.color = convertColor(colorName);
    }

    @Override
    public void execute(Turtle turtle, TurtleFrame frame, Map<String, Object> variables) throws JetExecutionException {
        if (this.color == null) {
            throw new JetExecutionException("認識できない色名です: '" + this.colorName + "'");
        }
        frame.displayInfoMessage("タートルの色を「" + this.colorName + "」に変更します。");
        turtle.setColor(this.color);
    }

    /**
     * JET言語の色名をJavaのColorオブジェクトに変換するヘルパーメソッド。
     * @param name 色名
     * @return 対応するColorオブジェクト。見つからない場合はnull。
     */
    private Color convertColor(String name) {
        switch (name) {
            case "黒": case "くろ":
                return Color.BLACK;
            case "赤": case "あか":
                return Color.RED;
            case "緑": case "みどり":
                return Color.GREEN;
            case "黄色": case "きいろ": case "黄": case "き":
                return Color.YELLOW;
            case "青": case "あお":
                return Color.BLUE;
            case "白": case "しろ":
                return Color.WHITE;
            case "オレンジ": case "だいだい":
                return Color.ORANGE;
            case "ピンク": case "ももいろ": case "桃色": case "もも": case "桃":
                return Color.PINK;
            case "シアン": case "みずいろ": case "水色": case "みず": case "水":
                return Color.CYAN;
            case "マゼンタ": case "むらさき": case "紫":
                return Color.MAGENTA;
            case "グレー": case "はいいろ": case "灰色": case "はい": case "灰":
                return Color.GRAY;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "色を " + colorName + " に変える";
    }
}