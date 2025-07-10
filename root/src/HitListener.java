/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * This interface implements the "Listener" design pattern along with the "HitNotifier" interface.
 * They will allow us to follow hits and have the Game members synced and responding correctly to every event.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit. It will act accordingly due to its specific nature.
     * @param beingHit is the object that was hit.
     * @param hitter is the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}