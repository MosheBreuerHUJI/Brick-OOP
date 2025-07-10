/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.util.LinkedList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * @author Moshe Breuer
 * @version 2.1
 * @since 2021-04-27
 * SpriteCollection is an object holds a collection of all sprites that exist in a certain game.
 * We use it to manage all of them in a concentrated way.
 */
public class SpriteCollection {

    private LinkedList<Sprite> collection;

    /**
     * A constructor.
     */
    public SpriteCollection() {
        this.collection = new LinkedList<Sprite>();
    }

    /**
     * Adding a given sprite to the collection.
     *
     * @param s is the given sprite.
     */
    public void addSprite(Sprite s) {
        this.collection.add(s);
    }

    /**
     * Removing a given sprite from the collection.
     *
     * @param s is the given sprite.
     */
    public void remove(Sprite s) {
        this.collection.remove(s);
    }

    /**
     * Call timePassed() on all sprites.
     * Helps managing all of them in a concentrated way.
     */
    public void notifyAllTimePassed() {
        // Make a copy of the sprites before iterating over them.
        List<Sprite> sprites = new LinkedList<>(this.collection);

        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     *
     * @param d is a given DrawSurface we draw all sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.collection) {
            s.drawOn(d);
        }
    }
}