package jetlearnsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONException;
import jetlearnsystem.ExerciseStructure.ProblemTransition;

public class ExerciseStructureLoader {

    // 演習構造ファイルが配置されるべきパス
    private static final String RESOURCE_PATH = "/jetlearnsystem/questions/exercise_structure.json";
    
    /**
     * 演習構造ファイル（exercise_structure.json）を読み込む。
     */
    public ExerciseStructure loadStructure() throws IOException {
        
        // 1. クラスパスからリソースストリームを取得
        InputStream is = getClass().getResourceAsStream(RESOURCE_PATH);
        if (is == null) {
            throw new IOException("演習構造ファイルが見つかりません: " + RESOURCE_PATH + " (src/questions/に配置されているか確認してください)");
        }

        // 2. ストリームからJSONテキストを読み込む
        String jsonContent;
        try (Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
            jsonContent = scanner.hasNext() ? scanner.next() : "";
        }
        
        // 3. JSONをパース
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonContent);
        } catch (JSONException e) {
            throw new RuntimeException("演習構造ファイルのパースエラー", e);
        }
        
        // 4. データモデルに抽出
        try {
            String startId = jsonObject.getString("start_id");
            JSONObject problemsJson = jsonObject.getJSONObject("problems");
            
            Map<String, ProblemTransition> problemMap = new HashMap<>();
            
            // 問題IDごとに遷移ルールを抽出
            for (String problemId : problemsJson.keySet()) {
                JSONObject transitionJson = problemsJson.getJSONObject(problemId);
                String nextCorrect = transitionJson.getString("next_on_correct");
                String nextFail = transitionJson.getString("next_on_fail");
                
                // ProblemTransitionオブジェクトを作成してMapに追加
                problemMap.put(problemId, new ProblemTransition(nextCorrect, nextFail));
            }

            return new ExerciseStructure(startId, problemMap);
            
        } catch (JSONException e) {
            throw new RuntimeException("演習構造データの抽出エラー: JSONキーを確認してください。", e);
        }
    }
}