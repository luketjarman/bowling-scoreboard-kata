import spock.lang.Specification
import spock.lang.Unroll

class ScorecardSpec extends Specification {
    @Unroll
    def "Adding the scores #pinsKnockedDown to a scorecard results in #expectedScore"() {
        expect:
            def scorecard = new Scorecard()
            for (int points : pinsKnockedDown) {
                scorecard.addPoints(points)
            }
            def scorecardScore = scorecard.getCurrentScore()
            scorecardScore == expectedScore
        where:
            // See rules for scoring in bowling here
            // https://en.wikipedia.org/wiki/Ten-pin_bowling#Scoring
            pinsKnockedDown                                                 || expectedScore
            []                                                              || 0
            [1]                                                             || 1
            [1,1]                                                           || 2
            [1,1,1]                                                         || 3
            // Strike tests
            [10,1]                                                          || 12
            [10,1,1]                                                        || 14
            [10, 10, 10]                                                    || 60
            // Spare tests
            [9,1,1]                                                         || 12
            [9,1,1,1]                                                       || 13
            [9,1,1,1]                                                       || 13
            // Complete games
            [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]    || 20
            // Perfect game
            // https://en.wikipedia.org/wiki/Perfect_game_(bowling)
            [10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10]                || 300
    }
}
