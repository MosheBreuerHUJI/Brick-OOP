/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

/**
 * @author Moshe Breuer
 * @version 2.0
 * @since 2021-04-27
 * This is the main class, that runs our whole program, the Arkanoid game.
 */
public class Ass5Game {

    /**
     * Tha main method. It initializes the game and runs it.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}