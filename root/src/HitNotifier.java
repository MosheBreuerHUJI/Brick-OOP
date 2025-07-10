/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * This interface implements the "Listener" design pattern along with the "HitListener" interface.
 * They will allow us to follow hits and have the Game members synced and responding correctly to every event.
 */
public interface HitNotifier {

    /**
     * Add a HitListener as a listener to hit events.
     * @param hl is the listener being added.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove a HitListener from the list of listeners to hit events.
     * @param hl is the HitListener being removed.
     */
    void removeHitListener(HitListener hl);
}