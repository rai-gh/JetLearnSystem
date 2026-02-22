package jetlearnsystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONException;

public class ProblemLoader {

    private static final String BASE_RESOURCE_PATH = "/jetlearnsystem/questions/json/";
    
    public ProblemDefinition loadProblem(String problemId) throws IOException {
        String resourcePath = BASE_RESOURCE_PATH + problemId + ".json";
        
        InputStream is = ProblemLoader.class.getResourceAsStream(resourcePath);

        if (is == null) {
            throw new IOException("指定された問題ファイルが見つかりません: " + resourcePath + 
                                  " (src/jetlearnsystem/questions/json/ に存在することを確認してください)");
        }

        String jsonContent;
        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
            jsonContent = scanner.hasNext() ? scanner.next() : "";
        }
        
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonContent);
        } catch (JSONException e) {
            throw new RuntimeException("JSONファイルのパースエラー: " + resourcePath, e);
        }
        
        try {
            String id = jsonObject.getString("id");
            String text = jsonObject.getString("text");
            String image = jsonObject.getString("image");
            String modelCode = jsonObject.getString("model_code");
            String modelImage = jsonObject.getString("model_image");
            
            return new ProblemDefinition(id, text, image, modelCode, modelImage);
              
        } catch (JSONException e) {
            throw new RuntimeException("問題定義データの抽出エラー。JSONキーまたは型を確認してください: " + e.getMessage(), e);
        }
    }
}