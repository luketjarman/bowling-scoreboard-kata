import spock.lang.Specification
import spock.lang.Unroll

class FrameSpec extends Specification {
    @Unroll
    def "After entering #enteredPoints into a frame the score is #expectedScore"() {
        expect:
        Frame frame = new Frame()
        for (int points : enteredPoints) {
            frame.addPoints(points)
        }
        def returnedScore = frame.getScore()
        def returnedState = frame.getState()
        returnedScore == expectedScore
        returnedState == expectedState
        where:
        enteredPoints       || expectedScore    | expectedState
        []                  || 0                | Frame.State.ACTIVE
        [1]                 || 1                | Frame.State.ACTIVE
        [9, 1]              || 10               | Frame.State.AWAITING_NEXT_FRAME_SCORE
        [10]                || 10               | Frame.State.AWAITING_NEXT_FRAME_SCORE
        [10, 1]             || 11               | Frame.State.AWAITING_NEXT_FRAME_SCORE
        [1, 1]              || 2                | Frame.State.COMPLETE
        [9, 1, 1]           || 11               | Frame.State.COMPLETE
        [10, 1, 1]          || 12               | Frame.State.COMPLETE
        [10, 10, 10]        || 30               | Frame.State.COMPLETE
    }
}
