/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * A BallRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 */
public class BallRemover implements HitListener {

    private Game g;
    private Counter remainingBalls;

    /**
     * A constructor.
     * @param game is the Game object our BallRemover refers to.
     * @param remaining is the counter of the Game we get.
     */
    public BallRemover(Game game, Counter remaining) {
        this.g = game;
        this.remainingBalls = remaining;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.g);
        this.remainingBalls.decrease(1);
    }
}