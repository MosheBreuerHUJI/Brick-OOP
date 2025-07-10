/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import biuoop.DrawSurface;

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * This class is for the object Block: a rectangle appears on the screen and changes with time and activity (sprite),
 * for the ball to hit (collidable).
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle surface;
    private Color color;
    private List<HitListener> hitListeners;

    /**
     * A constructor.
     *
     * @param surface is a given rectangle that tells the shape and location of the block.
     * @param color   is the block's color.
     */
    public Block(Rectangle surface, Color color) {
        this.surface = surface;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * A constructor.
     * It gives the block a random color.
     *
     * @param surface is a given rectangle that tells the shape and location of the block.
     */
    public Block(Rectangle surface) {
        this(surface, Utilities.randomColor());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.surface;
    }

    /**
     * Change the X value of the block's location.
     *
     * @param xChange is the amount of change, positive or negative
     */
    public void moveHorizontallyBy(double xChange) {
        Rectangle s = this.surface;
        Point location = s.getUpperLeft();
        this.surface =
                new Rectangle(new Point(location.getX() + xChange, location.getY()), s.getWidth(), s.getHeight());
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = collisionPoint.getX(), y = collisionPoint.getY();
        double x1 = this.surface.getUpperLeft().getX(), y1 = this.surface.getUpperLeft().getY();
        double x2 = this.surface.getLowerRight().getX(), y2 = this.surface.getLowerRight().getY();

        if (Utilities.doubleEquals(x, x1) || Utilities.doubleEquals(x, x2)) {
            currentVelocity.negateDX();
        }
        if (Utilities.doubleEquals(y, y1) || Utilities.doubleEquals(y, y2)) {
            currentVelocity.negateDY();
        }

        this.notifyHit(hitter);

        return currentVelocity;
    }

    @Override
    public void drawOn(DrawSurface d) { // This
        d.setColor(this.color);
        Rectangle rec = this.surface;
        d.fillRectangle((int) rec.getUpperLeft().getX(), (int) rec.getUpperLeft().getY(), (int) rec.getWidth(),
                (int) rec.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) rec.getUpperLeft().getX(), (int) rec.getUpperLeft().getY(), (int) rec.getWidth(),
                (int) rec.getHeight());
    }

    @Override
    public void timePassed() {
    }

    /**
     * A method that adds the block to a given game object (its spite collection and its collidable collection).
     *
     * @param game the given game.
     */
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Remove our Block from the game so that it would not appear anymore.
     *
     * @param game is the Game object our Sprite is being removed from.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Let all the "listeners" of our Block that a hit occurred so each would respond according to its specific purpose.
     *
     * @param hitter is the Ball that hit our Block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * This method turns our Block to be the ball's death-region in the game.
     * It means every time a ball will hit it, it will be removed from the game.
     *
     * @param game           is the Game object which in it we want a death-zone.
     * @param remainingBalls is the number of balls exist in that game (a Counter object).
     */
    public void makeKiller(Game game, Counter remainingBalls) {
        BallRemover ballRemover = new BallRemover(game, remainingBalls);
        this.addHitListener(ballRemover);
    }
}