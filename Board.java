/**
 * <code>Board</code> class contains methods that consist of back-end game logic for the Checkers class, including handling if the piece was moved to a valid position or not.
 *
 * @author (Etai Pollack, Griffin Miller)
 * @version (2023-2-17)
 */
public class Board {

    private char[][] cBoard;

    private int amtOfPiecesX;
    private int amtOfPiecesO;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    /**
     * Creates a board with the default setup of 3 rows of alternating x pieces, followed by 2 blank rows, followed by 3 rows of alternating white pieces.
     */
    public Board() 
    {
        cBoard = new char[8][8];

        for (int r = 0; r < cBoard.length; r++) 
        {

            for (int c = 0; c < cBoard[0].length; c++) 
            {
                if (r <= 2 && (c + r) % 2 != 0) {
                    cBoard[r][c] = 'x'; // x represents a black piece, X is king
                } else if (r >= 5 && (c + r) % 2 != 0) {
                    cBoard[r][c] = 'o'; // o represents a white piece, O is king
                } else {
                    cBoard[r][c] = '-';
                }
            }
        }

        amtOfPiecesX = 12; //12 by default
        amtOfPiecesO = 12; //12 by default

    }

    /**
     * Prints the current setup of the board, along with a scoreboard of the remaining pieces left for each player.
     */
    public void printBoard() 
    {
        char columnLetter = 'A';
        int rowNumber = 1;
        for (int i = 0; i < cBoard[0].length; i++) 
        {
            System.out.print(columnLetter + " ");
            columnLetter++;
        }
        System.out.println();
        for (char[] curRow : cBoard) 
        {
            for (char current : curRow) 
            {
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + current + " " + ANSI_RESET);
            }
            System.out.print(rowNumber + " ");
            if(rowNumber == cBoard.length / 2 ) 
            {
                System.out.println("     AMOUNT OF O PIECES LEFT: " + amtOfPiecesO);
            }
            else if(rowNumber == cBoard.length / 2 + 1 ) 
            {
                System.out.println("     AMOUNT OF X PIECES LEFT: " + amtOfPiecesX);
            } else 
            {
                System.out.println("");
            }
            rowNumber++;
        }
    }

    /**
     * Gets the piece on the board for a specified space.
     * @param curSpace the current coordinates of the space that the piece is on
     * @return the char value of the piece that is on the specified space on the board
     */
    public char getSpace(String curSpace) 
    {
        char curChar = '_'; //placeholder
        String coord = translateLocation(curSpace);
        if (coord == null) 
        {
            curChar = '*'; //placeholder for a nonexistent/null character
        } 
        else 
        {
            int row = Integer.parseInt(coord.substring(0, 1));
            int col = Integer.parseInt(coord.substring(1, 2));
            curChar = cBoard[row][col];
        }

        return curChar;
    }

    /**
     * Replaces the user-specified place on the board with a user-specified value.
     * @param curSpace is the coordinate location on the board
     * @param input is what value the user-specified place is being changed to.
     */
    public void setSpace(String curSpace, char input) 
    {
        String coord = translateLocation(curSpace);

        int row = Integer.parseInt(coord.substring(0, 1));
        int col = Integer.parseInt(coord.substring(1, 2));

        cBoard[row][col] = input;
    }

    /**
     * Translates a string of coordinates on the checkers board to a row and column on the 2D array.
     * @param input is the coordinates on the checkers board
     * @return the location on the 2D array.
     */
    public String translateLocation(String input) 
    {
        int col = -1;
        int row = -1;
        if (input.length() != 2) 
        {
            return null;
        } 
        else if (input.length() == 2) 
        {
            if (input.substring(0, 1).equals("A")) 
                col = 0;
            else if (input.substring(0, 1).equals("B")) 
                col = 1;
            else if (input.substring(0, 1).equals("C")) 
                col = 2;
            else if (input.substring(0, 1).equals("D")) 
                col = 3;
            else if (input.substring(0, 1).equals("E")) 
                col = 4;
            else if (input.substring(0, 1).equals("F")) 
                col = 5;
            else if (input.substring(0, 1).equals("G")) 
                col = 6;
            else if (input.substring(0, 1).equals("H")) 
                col = 7;
            else 
                return null;
            try 
            {
                if (Integer.parseInt(input.substring(1, 2)) <= 8 && Integer.parseInt(input.substring(1, 2)) >= 1) {
                    row = Integer.parseInt(input.substring(1, 2)) - 1;
                } 
                else 
                {
                    return null;
                }
            } 
            catch (NumberFormatException nfe) 
            {
                return null; // If coordinate is a valid letter followed by an invalid character
            }
            if(row == -1 || col == -1) 
            {
                return null;
            }
        }
        return "" + row + col;
    }

    /**
     * Checks if move is valid
     * @param piece the piece selected
     * @param moveLocation the selected move location
     * @param currentLocation the current location of the selected piece
     * @return if the move is valid or not
     */
    public boolean isValidMoveSpot(char piece, String moveLocation, String currentLocation) 
    {
        if (translateLocation(moveLocation) == null) 
        {
            return false;
        }
        int rowMove = Integer.parseInt(translateLocation(moveLocation).substring(0, 1));
        int colMove = Integer.parseInt(translateLocation(moveLocation).substring(1, 2));
        int curRow = Integer.parseInt(translateLocation(currentLocation).substring(0, 1));
        int curCol = Integer.parseInt(translateLocation(currentLocation).substring(1, 2));
        if (piece == 'x' || piece == 'X' || piece == 'O') 
        {
            if (curRow + 1 == rowMove) 
            {
                if (curCol - 1 == colMove || curCol + 1 == colMove) 
                {
                    if (cBoard[rowMove][colMove] == '-') 
                    {
                        cBoard[curRow][curCol] = '-';
                        cBoard[rowMove][colMove] = piece;
                        checkEndOfBoard(rowMove, colMove, piece);
                        return true;
                    }
                }
            } 
            else if (curRow + 2 == rowMove) 
            {
                if (curCol - 2 == colMove || curCol + 2 == colMove) 
                {
                    if (cBoard[rowMove][colMove] == '-') 
                    {
                        if (rowMove >= 1 && colMove >= 1 && curRow < cBoard.length - 1 && curCol < cBoard[0].length - 1) 
                        {
                            if (cBoard[rowMove - 1][colMove - 1] == getOppositePiece(piece) && cBoard[curRow + 1][curCol + 1] == getOppositePiece(piece)) 
                            {
                                System.out.println("Success!\n " + piece + " has attacked the enemy piece");
                                if(convertPiece(piece) == 'x') {

                                    amtOfPiecesX--;

                                } else {
                                    amtOfPiecesO--;
                                }
                                cBoard[rowMove][colMove] = piece;
                                cBoard[rowMove - 1][colMove - 1] = '-';
                                cBoard[curRow][curCol] = '-';
                                checkEndOfBoard(rowMove, colMove, piece);
                                return true;
                            }
                        }
                        if (rowMove >= 1 && curCol >= 1 && colMove < cBoard[0].length - 1 && curRow < cBoard.length - 1) 
                        {
                            if (cBoard[rowMove - 1][colMove + 1] == 'o' && cBoard[curRow + 1][curCol - 1] == 'o') 
                            {
                                System.out.println("Success!\n " + piece + " has attacked the enemy piece");
                                if(convertPiece(piece) == 'x') {

                                    amtOfPiecesX--;

                                } else {
                                    amtOfPiecesO--;
                                }
                                cBoard[rowMove][colMove] = piece;
                                cBoard[rowMove - 1][colMove + 1] = '-';
                                cBoard[curRow][curCol] = '-';
                                checkEndOfBoard(rowMove, colMove, piece);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        if (piece == 'o' || piece == 'O' || piece == 'X') 
        {
            if (curRow - 1 == rowMove) 
            {
                if (curCol - 1 == colMove || curCol + 1 == colMove) 
                {
                    if (cBoard[rowMove][colMove] == '-') 
                    {
                        cBoard[curRow][curCol] = '-';
                        cBoard[rowMove][colMove] = piece;
                        checkEndOfBoard(rowMove, colMove, piece);
                        return true;
                    }
                }
            } else if (curRow - 2 == rowMove) 
            {
                if (curCol - 2 == colMove || curCol + 2 == colMove) 
                {
                    if (cBoard[rowMove][colMove] == '-') 
                    {
                        if (rowMove < cBoard.length - 1 && colMove >= 1 && curRow >= 0 && curCol < cBoard[0].length) 
                        {
                            if (cBoard[rowMove + 1][colMove - 1] == getOppositePiece(piece) && cBoard[curRow - 1][curCol + 1] == getOppositePiece(piece)) 
                            {
                                System.out.println("Success!\n " + piece + " has attacked the enemy piece");
                                if(convertPiece(piece) == 'x') {

                                    amtOfPiecesX--;

                                } else {
                                    amtOfPiecesO--;
                                }
                                cBoard[rowMove][colMove] = piece;
                                cBoard[rowMove + 1][colMove - 1] = '-';
                                cBoard[curRow][curCol] = '-';
                                checkEndOfBoard(rowMove, colMove, piece);
                                return true;
                            }
                        }
                        if (rowMove < cBoard.length - 1 && colMove < cBoard[0].length - 1 && curRow >= 1 && curCol >= 1) 
                        {
                            if (cBoard[rowMove + 1][colMove + 1] == getOppositePiece(piece) && cBoard[curRow - 1][curCol - 1] == getOppositePiece(piece)) 
                            {
                                System.out.println("Success!\n " + piece + " has attacked the enemy piece");
                                if(convertPiece(piece) == 'x') {

                                    amtOfPiecesX--;

                                } else {
                                    amtOfPiecesO--;
                                }
                                cBoard[rowMove][colMove] = piece;
                                cBoard[rowMove + 1][colMove + 1] = '-';
                                cBoard[curRow][curCol] = '-';
                                checkEndOfBoard(rowMove, colMove, piece);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    //Checks if piece is at the end of the board, and if it is, king that piece.
    private void checkEndOfBoard(int row, int col, char piece) {
        if(piece == 'x' && row == cBoard.length - 1) {
            cBoard[row][col] = 'X';
            System.out.println("An x piece has been kinged!");
        } else if (piece == 'o' && row == 0) {
            cBoard[row][col] = 'O';
            System.out.println("An o piece has been kinged!");
        }
    }

    /**
     * Returns the piece on the opposite team of the inputted piece.
     * @param piece the specified piece.
     * @return the piece on the opposite team of the inputted piece.
     */
    public char getOppositePiece(char piece) {
        if(piece == 'x' || piece == 'X') {
            return 'o';
        }
        else if (piece == 'o' || piece == 'O') {
            return 'x';
        }
        return '*'; //null placeholder piece
    } 

    /**
     * Converts a kinged piece or an unkinged piece an unkinged pieace on the same team, for use in other methods.
     * @param piece the specified piece.
     * @return the unkinged piece on the same team as the inputted piece
     */
    public static char convertPiece(char piece) {

        if(piece == 'x' || piece == 'X') {
            return 'x';
        }
        else if (piece == 'o' || piece == 'O') {
            return 'o';
        }
        return '*'; //null placeholder piece


    }

    /**
     * Returns the amount of pieces left on X team.
     * @return the amount of pieces left on X team.
     */
    public int getAmtX() 
    {
        return amtOfPiecesX;
    }
    
    /**
     * Returns the amount of pieces left on O team.
     * @return the amount of pieces left on O team.
     */
    public int getAmtO() 
    {
        return amtOfPiecesO;
    }
}
