import java.util.ArrayList;
import java.util.List;

public class Frame {
    private int currentScore = 0;
    private List<Integer> recordedPoints = new ArrayList<>();

    public State addPoints(int points) {
        currentScore += points;
        recordedPoints.add(points);

        return getState();
    }

    public State getState() {
        int numOfPointsRecorded = recordedPoints.size();

        // Check if this frame is a Strike which hasn't received future scores
        boolean isFirstPointsStrike = (recordedPoints.stream().findFirst().filter(points -> points == 10)).isPresent();
        boolean isIncompleteStrike = (isFirstPointsStrike && numOfPointsRecorded < 3);
        if (isIncompleteStrike) {
            return State.AWAITING_NEXT_FRAME_SCORE;
        }

        //
        boolean isIncompleteSpare = (numOfPointsRecorded == 2 && currentScore == 10);
        if (isIncompleteSpare) {
            return State.AWAITING_NEXT_FRAME_SCORE;
        }

        //
        if (numOfPointsRecorded >= 2) {
            return State.COMPLETE;
        }

        return State.ACTIVE;
    }

    public int getScore() {
        return currentScore;
    }

    public enum State {
        ACTIVE,
        AWAITING_NEXT_FRAME_SCORE,
        COMPLETE
    }
}
