package jetlearnsystem;

import java.util.Map;
import org.json.JSONObject; 

/**
 * 問題定義ファイル（JSON）の内容を保持するクラス。（簡略化版）
 * 最終的な正解条件と、将来の拡張に必要な情報を含む。
 */
public class ProblemDefinition {
    private String id;
    private String text; 
    private String image;
    private String modelCode;
    private String modelImage;

    public ProblemDefinition(String id, String text, String image, String modelCode, String modelImage) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.modelCode = modelCode;
        this.modelImage = modelImage;
    }

    public String getId() { return id; }
    public String getText() { return text; }
    public String getImage() { return image; }
    public String getModelCode() { return modelCode; }
    public String getModelImage() { return modelImage; }
}