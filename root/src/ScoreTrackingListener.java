/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * This object is responsible for updating the player's score according to ots progress in the game.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * A constructor.
     * @param scoreCounter is a given Counter received that will be in our ScoreTrackingListener field.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }
}
