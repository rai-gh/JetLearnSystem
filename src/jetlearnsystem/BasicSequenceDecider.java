package jetlearnsystem;

import java.util.Map;
import java.util.Collections;
import java.io.IOException;

public class BasicSequenceDecider implements ProblemSequenceDecider {

    private final ExerciseStructure exerciseStructure;

    public BasicSequenceDecider() {
        ExerciseStructure structure = null;

        ExerciseStructureLoader structureLoader = new ExerciseStructureLoader();
        try {
            structure = structureLoader.loadStructure(); 
        } catch (IOException | RuntimeException e) {
            System.err.println("Error loading exercise structure for BasicSequenceDecider: " + e.getMessage());
        }
        this.exerciseStructure = structure; 
    }

    @Override
    public String getNextProblemId(String currentProblemId, boolean isCorrect, Map<String, Object> contextData) {
        if (exerciseStructure == null) {
            return "END"; 
        }
        return this.exerciseStructure.getNextProblemId(currentProblemId, isCorrect);
    }

    /**
     * 保持している演習構造オブジェクトを取得する。
     * @return ExerciseStructureオブジェクト
     */
    public ExerciseStructure getExerciseStructure() {
        return exerciseStructure;
    }
}