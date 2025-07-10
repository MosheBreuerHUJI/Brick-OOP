/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author Moshe Breuer
 * @version 4.0
 * @since 2021-04-27
 * Paddle is an object based on the block. It is the game "player", moving on the bottom of the screen and allowing
 * the user to bump the ball.
 */
public class Paddle implements Collidable, Sprite {

    private Block block;
    private KeyboardSensor keyboard;

    // the paddle linear velocity:
    private static final int SPEED = 5;

    /**
     * A constructor.
     *
     * @param surface  is the paddle's rectangle shape (and location)
     * @param color    is the paddle's desired color
     * @param keyboard is the sensor used to move the paddle according to the user's commands
     */
    public Paddle(Rectangle surface, Color color, KeyboardSensor keyboard) {
        this.block = new Block(surface, color);
        this.keyboard = keyboard;
    }

    /**
     * A constructor. (the same, random color).
     *
     * @param surface  is the paddle's rectangle shape (and location)
     * @param keyboard is the sensor used to move the paddle according to the user's commands
     */
    public Paddle(Rectangle surface, KeyboardSensor keyboard) {
        this(surface, Utilities.randomColor(), keyboard);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle surface = this.getCollisionRectangle();
        // if the ball doesn't hit the top of the paddle, it's just the same as a block hit
        Point start = surface.getUpperLeft();
        Point end = new Point(surface.getLowerRight().getX(), surface.getUpperLeft().getY());
        Line paddleTop = new Line(start, end);
        if (!collisionPoint.isIn(paddleTop)) { // if the ball hits anywhere the paddle anywhere else, then -
            return this.block.hit(hitter, collisionPoint, currentVelocity); // act just like regular block hit
        }

        // if the ball hits the paddle top, changing its direction depending on where it hit
        double zero = start.getX(), sectionLength = paddleTop.length() / 5, x = collisionPoint.getX() - zero;
        int sectionIndex = (int) (x / sectionLength);
        double angle = (300 + sectionIndex * 30) % 360;
        if (angle == 0) { // if the ball hit the center of the paddle top the impact was defined to be as a block's
            Velocity newVelocity = currentVelocity;
            newVelocity.negateDY();
            return newVelocity;
        }

        return Velocity.fromAngleAndSpeed(angle, currentVelocity.getSpeed());
    }

    /**
     * Change the paddle's location when getting a command to.
     */
    public void moveRight() {
        Rectangle surface = this.getCollisionRectangle();
        // keeping the paddle in the frame boundaries
        if (surface.getUpperLeft().getX() >= (Game.FRAME_WIDTH - Game.BLOCK_HEIGHT - surface.getWidth())) {
            return;
        }
        this.block.moveHorizontallyBy(SPEED);
    }

    /**
     * Change the paddle's location when getting a command to.
     */
    public void moveLeft() {
        Rectangle surface = this.getCollisionRectangle();
        // keeping the paddle in the frame boundaries
        if (surface.getUpperLeft().getX() <= Game.BLOCK_HEIGHT) {
            return;
        }
        this.block.moveHorizontallyBy((-1) * SPEED);
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    @Override
    public void timePassed() { // the right behaviour for the paddle is moving horizontally according to key hits
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * A method that adds the paddle to a given game object (its spite collection and its collidable collection).
     *
     * @param game the given game.
     */
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }
}