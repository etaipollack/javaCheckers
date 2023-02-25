import java.util.Scanner;

/**
 * <code>Checkers</code> class contains methods that consist of front-end game logic for a game of checkers, including creating a new game and querying the player on where they would want to move their piece.
 *
 * @author (Etai Pollack, Griffin Miller)
 * @version (2023-2-17)
 */
public class Checkers 
{
    private Board mainBoard;
    private boolean gameIsOver;
    
    private Player[] listOfPlayers;

    /**
     * Creates a board and two blank players for a new game of checkers.
     */
    public Checkers() 
    {

        mainBoard = new Board();
        gameIsOver = false;
        listOfPlayers = new Player[2];
        
        startGame();

    }
    /**
     * Starts the game by first recieving both player names for pieces X and O, and then starting a new move if the game is not over yet.
     */
    public void startGame() 
    {
        
        Scanner in = new Scanner(System.in);
        System.out.println("Starting new game of Hyper-Realistic Epic Checkers 3... ");
        
        System.out.println("What is the name of Player 1, who will be represented by o?");
        String player1Name = in.nextLine();
        Player player1 = new Player(player1Name, 'o');
        System.out.println("Hello  " + player1Name + "!");
        System.out.println();
        System.out.println("What is the name of Player 2, who will be represented by x?");
        String player2Name = in.nextLine();
        Player player2 = new Player(player2Name, 'x');
        System.out.println("What's up " + player2Name + "!");
        System.out.println();
        
        listOfPlayers[0] = player1;
        listOfPlayers[1] = player2;
        
        instructions();
        while(gameIsOver == false) 
        {
            newMove();
        } 

    }
    
    /**
     * Gives the players instructions on how to play the game.
     */
    public void instructions() 
    {

        System.out.println("Hello, this is a game of checkers\n" + listOfPlayers[0].getPlayerName() + " is represented by the 'o', and " + listOfPlayers[1].getPlayerName() + " is represented by the 'x' ");
        System.out.println("To select a piece or a space, type in the coordinates (such as A4) to select the piece/space at column A and row 4");
        System.out.println("Then you will be asked to chose a move. You will say the coordinates of where you want to move (B5)");
        System.out.println("If the move is invalid you will be asked to try again");
        System.out.println();
    }
    
    /**
     * Handles each move for the player, asking them what piece they want to move and where, while also checking if they made a winning move or not.
     */
    public void newMove() 
    {

        Scanner strInput = new Scanner(System.in);
        Scanner intInput = new Scanner(System.in);
        
        for(Player currentPlayer : listOfPlayers) 
        {
            Boolean playerSelectedPieceIsValid = false;
            Boolean playerSelectedSpotIsValid = false;

            if(mainBoard.getAmtO() == 0) 
            {
                System.out.println("----- PIECE X WINS -----");
                gameIsOver = true;
                break;

            } 
            else if(mainBoard.getAmtX() == 0) 
            {
                System.out.println("----- PIECE O WINS -----");
                gameIsOver = true;
                break;

            }
            //check if current player selected their proper piece
            while(playerSelectedPieceIsValid == false) 
            {
                playerSelectedSpotIsValid = false;
                mainBoard.printBoard();
                System.out.println(currentPlayer.getPlayerName() + ", select a piece by typing in its coordinates, or type in 'instr' recieve the instructions.");
                String recievedInput = strInput.nextLine();

                if(recievedInput.equals("instr")) 
                {
                    instructions();
                    continue;
                }
                else if(recievedInput.length() == 2 ) 
                {
                    if(mainBoard.convertPiece(mainBoard.getSpace(recievedInput)) == currentPlayer.getPlayerPiece()) 
                    {
                        //piece is valid
                        System.out.println();
                        playerSelectedPieceIsValid = true;
                        //check if player selected a proper spot to move to
                        while(playerSelectedSpotIsValid == false) 
                        {

                            System.out.println("What spot would you like to move to? (type in 'redo' to select a new piece if needed)");
                            
                            String recievedInputMove = strInput.nextLine();
                            if(recievedInputMove.length() == 2 && mainBoard.isValidMoveSpot(mainBoard.getSpace(recievedInput), recievedInputMove, recievedInput)) 
                            { 
                                playerSelectedSpotIsValid = true;
                            } 
                            else if(recievedInputMove.equals("redo")) 
                            {
                                playerSelectedSpotIsValid = true;
                                playerSelectedPieceIsValid = false;
                            }
                            else 
                            {
                                System.out.println("Not a valid spot to move to, please try again.");
                                continue;

                            }
                        }
                    }
                    // * represents a null space
                    else if(mainBoard.getSpace(recievedInput) == '*') 
                    {
                        System.out.println("Space is not valid, please try again.");
                        System.out.println();
                        continue;
                    } 
                    else 
                    {
                        System.out.println("Selected space is not your piece! You selected " + mainBoard.getSpace(recievedInput) + " but your piece is " + currentPlayer.getPlayerPiece());
                        System.out.println();
                        continue;
                    }
                } 
                else 
                {
                    System.out.println("Inputted space must be two characters, pertaining to the piece that you want to select");
                    System.out.println();
                    
                    continue;

                }
            }   
        }
    }
}
