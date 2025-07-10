/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import java.awt.Color;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;

/**
 * @author Moshe Breuer
 * @version 2.2
 * @since 2021-04-27
 * This class holds all the info of an entire game:
 * Collections of the items that participate in it and the objects that allow it to run.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;
    private static final int BLOCK_WIDTH = 50;
    public static final int BLOCK_HEIGHT = BLOCK_WIDTH / 2;
    public static final int PADDLE_WIDTH = BLOCK_WIDTH * 4;
    private static final int ROWS = 6;
    private static final int MOST_BLOCKS = 10;

    /**
     * A constructor.
     *
     * @param sprites     is the sprite collection the game would hold.
     * @param environment is the collidables collection the game would hold.
     */
    public Game(SpriteCollection sprites, GameEnvironment environment) {
        this.sprites = sprites;
        this.environment = environment;
        this.gui = new GUI("Arkanoid", FRAME_WIDTH, FRAME_HEIGHT);
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
    }

    /**
     * Another constructor that initializes the fields by itself.
     */
    public Game() {
        this(new SpriteCollection(), new GameEnvironment());
    }

    /**
     * A method that adds a given collidable object to the game's collection.
     *
     * @param c is the given collidable object.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * A method that adds a given sprite object to the game's collection.
     *
     * @param s is the given sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        addBorders();
        addBlocks();
        addPaddle();
        addBalls();
        addScoreIndicator();
    }

    /**
     * Initialize a ScoreIndicator and add it to the game.
     */
    private void addScoreIndicator() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Initialize a paddle and add it to the game.
     */
    private void addPaddle() {
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(
                new Rectangle(new Point(((FRAME_WIDTH / 2) - (PADDLE_WIDTH / 2)), FRAME_HEIGHT - BLOCK_HEIGHT * 1.5),
                        PADDLE_WIDTH, BLOCK_HEIGHT), Utilities.DARK_GREEN, keyboardSensor);
        paddle.addToGame(this);
    }

    /**
     * Initialize the blocks and add them to the game.
     */
    private void addBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        // setting the space needed from the edges of the frames to the blocks' location
        double heightGap = BLOCK_HEIGHT * 5;
        double widthGap = (((FRAME_WIDTH - 2 * BLOCK_HEIGHT) / BLOCK_WIDTH) - MOST_BLOCKS) * BLOCK_WIDTH - BLOCK_HEIGHT;
        Color[] piazzaColorsArray = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.LIGHT_GRAY};
        for (int row = 0; row < ROWS; row++) {
            for (int blockIndex = 1; blockIndex <= MOST_BLOCKS - row; blockIndex++) {
                Block b = new Block(
                        new Rectangle(new Point(widthGap + blockIndex * BLOCK_WIDTH, heightGap + row * BLOCK_HEIGHT),
                                BLOCK_WIDTH, BLOCK_HEIGHT), piazzaColorsArray[row]);
                b.addToGame(this);
                b.addHitListener(blockRemover);
                b.addHitListener(scoreTrackingListener);
                this.remainingBlocks.increase(1);
            }
            widthGap += BLOCK_WIDTH;
        }
    }

    /**
     * Initialize the balls and add them to the game.
     */
    private void addBalls() {
        Point startPoint = new Point(FRAME_WIDTH / 2, (FRAME_HEIGHT / 4) * 3);
        int radius = 5;
        Color[] colors = {Utilities.DARK_PURPLE, Utilities.PURPLE, Utilities.BRIGHT_PURPLE};

        Ball[] balls = new Ball[3];

        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(startPoint, radius, colors[i]);
            ball.setVelocity(Velocity.fromAngleAndSpeed(-15 + (15 * i), 5 + i));
            ball.setEnvironment(this.environment);
            ball.addToGame(this);
            this.remainingBalls.increase(1);
            balls[i] = ball;
        }
    }

    /**
     * Initialize the blocks the limit the frame and add them to the game.
     */
    private void addBorders() {
        Block top = new Block(new Rectangle(new Point(0, 0), FRAME_WIDTH, BLOCK_HEIGHT), Utilities.GREEN);
        Block bottom =
                new Block(new Rectangle(new Point(0, FRAME_HEIGHT + BLOCK_HEIGHT * 2), FRAME_WIDTH, BLOCK_HEIGHT),
                        Utilities.GREEN);
        Block left =
                new Block(new Rectangle(new Point(0, BLOCK_HEIGHT), BLOCK_HEIGHT, FRAME_HEIGHT + BLOCK_HEIGHT),
                        Utilities.GREEN);
        Block right = new Block(new Rectangle(new Point(FRAME_WIDTH - BLOCK_HEIGHT, BLOCK_HEIGHT), BLOCK_HEIGHT,
                FRAME_HEIGHT + BLOCK_HEIGHT), Utilities.GREEN);

        bottom.makeKiller(this, this.remainingBalls);

        Block[] borders = {top, bottom, left, right};

        for (Block b : borders) {
            b.addToGame(this);
        }
    }

    /**
     * This method executes our program. It runs an animation loop.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            executeOneFrame();

            // adding a bonus when there are no more Blocks left, and ending the game:
            if (this.remainingBlocks.getValue() == 0) {
                this.score.increase(100);
                win();
                return;
            }

            // ending the game when there are no more Balls left:
            if (this.remainingBalls.getValue() == 0) {
                lose();
                return;
            }

            sleepUntilNextFrame(sleeper, millisecondsPerFrame, startTime);
        }
    }

    /**
     * Stall animation for the needed time that's being calculated here.
     * This method allows the animation to be coherent.
     *
     * @param sleeper              is the object executes the stall.
     * @param millisecondsPerFrame is the desired millisecondsPerFrame number.
     * @param startTime            is the system time in which this frame started.
     */
    private void sleepUntilNextFrame(Sleeper sleeper, int millisecondsPerFrame, long startTime) {
        long usedTime = System.currentTimeMillis() - startTime;
        long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
        if (milliSecondLeftToSleep > 0) {
            sleeper.sleepFor(milliSecondLeftToSleep);
        }
    }

    /**
     * This method is a part of "run()". It handles animation of a single frame and preparation fot the next.
     */
    private void executeOneFrame() {
        DrawSurface d = this.gui.getDrawSurface();
        this.sprites.drawAllOn(d);
        this.gui.show(d);

        this.sprites.notifyAllTimePassed();
    }

    /**
     * This method ends the game.
     * It might have extra functionality in future versions, such as printing an announcement on the GUI screen.
     */
    private void lose() {
        gui.close();
    }

    /**
     * This method ends the game.
     * It might have extra functionality in future versions, such as printing an announcement on the GUI screen.
     */
    private void win() {
        gui.close();
    }

    /**
     * This method removes a given Collidable object from the game's collection.
     *
     * @param c is the given Collidable object.
     */
    public void removeCollidable(Collidable c) {
        this.environment.remove(c);
    }

    /**
     * This method removes a given Sprite object from the game's collection.
     *
     * @param s is the given Sprite object.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}