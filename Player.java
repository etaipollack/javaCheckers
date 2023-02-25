import java.util.Scanner;

/**
 * <code>Player</code> class represents the information regarding the player and the piece they selected.
 *
 * @author (Etai Pollack, Griffin Miller)
 * @version (2023-2-17)
 */
public class Player 
{

    private String playerName;
    private char piece;

    /**
     * Creates a new player with a user-specified name and piece type.
     * @param name the name of the player.
     * @param pieceType the selected piece of the player.
     */
    public Player(String name, char pieceType) 
    {
        playerName = name;
        piece = pieceType;
    }


    /**
     * Returns the name of the player.
     * @return the name of the player.
     */
    public String getPlayerName() 
    {
        return playerName;
    }

    /**
     * Returns the selected piece of the player.
     * @return the selected piece of the player.
     */
    public char getPlayerPiece() 
    {
        return piece;
    }
}
