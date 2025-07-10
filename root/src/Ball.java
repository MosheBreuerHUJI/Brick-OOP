/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.awt.Color;

import biuoop.DrawSurface;

/**
 * @author Moshe Breuer
 * @version 3.2
 * @since 2021-04-27
 * The class implements the Ball object: a circle with a center point and a radius size.
 * A Ball also has a color and a velocity. The ball knows the game environment it wanders in and the obstacles in it.
 * Ball is a Sprite, it appears in the game and changes with time.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    ///////////////// Constructors /////////////////////////////////////////////////////////////////////////////////////

    /**
     * A constructor.
     *
     * @param center is the ball's center point
     * @param radius is it's size
     * @param color  is the ball's color
     */
    public Ball(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * An alternative constructor that calls the first one as detailed for every parameter.
     *
     * @param x      is the x value of the center point
     * @param y      is the y value of the center point
     * @param radius as in the original constructor
     * @param color  as in the original constructor
     */

    public Ball(double x, double y, int radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    /**
     * An alternative constructor that calls the first one as detailed for every parameter.
     * (just like the one before, only with ints instead of doubles for the center point values):
     *
     * @param x      is the x value of the center point
     * @param y      is the y value of the center point
     * @param radius as in the original constructor
     * @param color  as in the original constructor
     */
    public Ball(int x, int y, int radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    ///////////////// Setters //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * A setter.
     *
     * @param v is the desired velocity for the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Another velocity setter, defined by the velocity values as detailed for every parameter.
     *
     * @param dx is the x-axis element of the desired velocity
     * @param dy is the y-axis element of the desired velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * A setter.
     * Sets the game environment the ball will be in.
     *
     * @param gameEnvironment is the environment we want to set for the ball.
     */
    public void setEnvironment(GameEnvironment gameEnvironment) {
        this.environment = gameEnvironment;
    }

    ///////////////// Getters //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * A getter.
     *
     * @return the x value of the center point of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * A getter.
     *
     * @return the y value of the center point of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * A getter.
     *
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * A getter.
     *
     * @return the Color object that represents the ball color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * A getter.
     *
     * @return the ball's velocity field (a Velocity object).
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    ///////////////// Behavior management //////////////////////////////////////////////////////////////////////////////

    /**
     * A method that handles the change in the ball's location in the animation.
     */
    public void moveOneStep() {
        /* Up to three updates of the velocity,
         * for cases where the ball meets two or three close blocks that are very near,
         * and almost won't move between the hits.
         */
        for (int i = 0; i < 3; i++) {
            updateVelocity(getCollisionInfo());
        }
        this.center = this.velocity.applyToPoint(this.center); // actually moving the ball
        this.doNotBeInsideThePaddle(); // making sure it won't get stack there
    }

    /**
     * A helper for the moveOneStep method. It finds the new velocity needed after a hit, if there is any,
     * and applies it to the point.
     *
     * @param collision is information about the closest collision that is about to occur (if any).
     */
    private void updateVelocity(CollisionInfo collision) {
        if (collision == null) {
            return;
        }
        this.velocity = collision.collisionObject().hit(this, collision.collisionPoint(), this.velocity);
    }

    /**
     * This method gives information about the closest collision of the ball with objects,
     * on its movement path expected (based on its movement details).
     *
     * @return that information as an CollisionInfo object.
     */
    private CollisionInfo getCollisionInfo() {
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        return this.environment.getClosestCollision(trajectory);
    }

    /**
     * This method comes after "moveOneStep" and verifies the balls location isn't errored in any way.
     * It is needed since when the player moves the paddle into the ball, the trajectory can not foresee this and
     * avoid a situation of the ball inside the paddle.
     */
    private void doNotBeInsideThePaddle() {
        Rectangle paddle = this.environment.getPaddle();
        if (this.center.isIn(paddle)) {
            double xCoordinate = this.getX(), yCoordinate = this.getY();
            if (this.velocity.getDx() < 0) { // moves right
                xCoordinate = paddle.getLowerRight().getX() + this.radius; // be outside of the paddle, right next to it
            } else if (this.velocity.getDx() > 0) { // moves left
                xCoordinate = paddle.getUpperLeft().getX() - this.radius; // be outside of the paddle, right next to it
            } else { // ball moves up and down only
                yCoordinate = Game.FRAME_WIDTH - (Game.BLOCK_HEIGHT * 2 + this.radius); // be right above the paddle
            }
            // in case it's too close to the sides borders:
            if (xCoordinate <= Game.BLOCK_HEIGHT
                    || xCoordinate >= (Game.FRAME_WIDTH - Game.BLOCK_HEIGHT)) {
                xCoordinate = this.getX();
                yCoordinate = paddle.getUpperLeft().getY() + this.radius; // be right above the paddle
            }
            if (yCoordinate >= (Game.FRAME_HEIGHT - Game.BLOCK_HEIGHT - this.radius)) {
                yCoordinate = Game.FRAME_HEIGHT - Game.BLOCK_HEIGHT - this.radius - Utilities.A_LITTLE;
            }
            this.center = new Point(xCoordinate, yCoordinate); // updating the balls location
            this.velocity.negateDX(); // updating the balls direction, so it won't move back in
        }
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    @Override
    public void drawOn(DrawSurface drawSurface) {
        drawSurface.setColor(this.color); // to print the ball in its color
        drawSurface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * A method that adds the ball to a given game object (its spite collection).
     *
     * @param game the given game.
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    /**
     * Remove our Ball from the game so that it would not appear anymore.
     *
     * @param g is the Game object our Sprite is being removed from.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}