/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * A BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game g;
    private Counter remainingBlocks;

    /**
     * A constructor.
     * @param game is the Game object our BlockRemover refers to.
     * @param removedBlocks should actually be called "remaining Blocks". It is the counter of the Game we get.
     */
    public BlockRemover(Game game, Counter removedBlocks) {
        this.g = game;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.g);
        this.remainingBlocks.decrease(1);
    }
}