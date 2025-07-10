/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * This is an interface for all the object that can exist on a game surface and other objects might
 * hit (collide with them).
 * It defines the required behaviour for such objects.
 */
public interface Collidable {

    /**
     * A getter.
     *
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param hitter          is a reference to the Ball object that hit the collidable.
     * @param collisionPoint  is where the hit happens on our object.
     * @param currentVelocity is the velocity which in it another object hits ours.
     * @return is the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}