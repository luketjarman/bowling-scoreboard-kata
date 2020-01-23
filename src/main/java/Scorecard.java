import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class Scorecard {
    private Frame activeFrame;
    private List<Frame> framesAwaitingFuturePoints;
    private List<Frame> frames;

    public Scorecard() {
        activeFrame = new Frame();
        frames = new ArrayList<>();
        framesAwaitingFuturePoints = new ArrayList<>();
        frames.add(activeFrame);
    }

    public void addPoints(int points) {
        addPointsToAwaitingFrames(points);
        addPointsToActiveFrame(points);
    }

    private void addPointsToActiveFrame(int points) {
        Frame.State newFrameState = activeFrame.addPoints(points);

        switch (newFrameState) {
            case AWAITING_NEXT_FRAME_SCORE:
                framesAwaitingFuturePoints.add(activeFrame);
            case COMPLETE:
                if (frames.size() != 10) {
                    activeFrame = new Frame();
                    frames.add(activeFrame);
                }
                break;
        }
    }

    private void addPointsToAwaitingFrames(int points) {
        Iterator<Frame> frameIterator = framesAwaitingFuturePoints.iterator();
        while (frameIterator.hasNext()) {
            Frame frame = frameIterator.next();
            Frame.State newFrameState = frame.addPoints(points);
            if (newFrameState.equals(Frame.State.COMPLETE)) {
                frameIterator.remove();
            }
        }
    }

    public int getCurrentScore() {
        Optional<Integer> score = frames.stream().map(Frame::getScore).reduce(Integer::sum);
        return score.orElse(0);
    }
}
