javaCheckers

Written by Etai Pollack

WHAT IT IS
A fully playable game of checkers through the console

HOW TO PLAY
The console will take turns asking each player to select a piece, and then to select a valid spot to move to.
If either are invalid, the user will have to reselect a spot on the board until they select a valid spot.
First user to take all of the opponents pieces wins.

Ex: a full command would be 
B3
C4

HOW TO WRITE IT
1. Assamble an 8 by 8 Checkers board with three rows of black pieces ([x]) followed by 2 blank rows and three rows of white pieces ([o])
2. Create an input based way to move the pieces 
3. Make sure that the move is legal (no going into walls, no going where a piece already is)
4. Create code that allows a player to take another piece.
5. If a piece gets to the other side it gets "kinged" and can now move in more directions
6. Write code thats reads the input, changes the board, and reprints the board.
7. Check if amount of pieces on either side is 0, and if so, end the game and print the winner.
