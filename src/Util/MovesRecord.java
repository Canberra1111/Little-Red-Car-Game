package src.Util;

/**
 * This class represents a utility for recording moves during gameplay.
 * @author Gong Yihang
 * @since 1.0
 */
public class MovesRecord {
    /**
     * The initially set best moves for the level.
     */
    private int setBestMoves;

    /**
     * The recorded best moves made by the user.
     */
    private int recordedBestMoves;
    /**
     * The current best moves achieved.
     */
    private int bestMoves;
    /**
     * Constructs a MovesRecord object with the specified set best moves and recorded best moves.
     *
     * @param setBestMoves      The initially set best moves for the level.
     * @param recordedBestMoves The recorded best moves made by the user.
     */
    public MovesRecord(int setBestMoves, int recordedBestMoves) {
        this.setBestMoves = setBestMoves;
        this.recordedBestMoves = recordedBestMoves;
        this.bestMoves=setBestMoves;
    }
    /**
     * Gets the current best moves achieved.
     *
     * @return An integer representing the current best moves achieved.
     */
    public int getBestMoves() {
        return bestMoves;
    }
}