/* ass5
 * Moshe Breuer
 * ID: 316331354
 */

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Moshe Breuer
 * @version 1.0
 * @since 2021-05-30
 * ScoreIndicator will be in charge of displaying the current score.
 * The ScoreIndicator will hold a reference to the scores counter,
 * and will be added to the game as a sprite positioned at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    private static final int HORIZONTAL_FIXATION = 35;
    private static final int VERTICAL_FIXATION = 6;
    private static final int FONT_SIZE = 16;

    /**
     * A constructor.
     *
     * @param scoreCounter is the counter of score from the Game.
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.score = scoreCounter;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.drawText(Game.FRAME_WIDTH / 2 - HORIZONTAL_FIXATION, Game.BLOCK_HEIGHT - VERTICAL_FIXATION,
                "Score: " + this.score.getValue(), FONT_SIZE);
    }

    @Override
    public void timePassed() {
        // does nothing
    }

    /**
     * A method that adds the ScoreIndicator to a given game object (its spite collection).
     *
     * @param game the given game.
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}