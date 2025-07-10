/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import biuoop.DrawSurface;

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * This is an interface for all the objects that appear in the game and change through time and activity.
 * It defines the required behaviour for such objects.
 */
public interface Sprite {

    /**
     * Draw the sprite to the screen.
     *
     * @param d is the given DrawSurface that we draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed, so it will change (and act) accordingly.
     */
    void timePassed();
}