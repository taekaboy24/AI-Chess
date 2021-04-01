import java.util.*;

public class AIAgent {

  Random rand;

  public AIAgent() {
    rand = new Random(); // creates rand method to generate random number
  }

  /*
  RANDOM MOVE
  This method is to produce a random move as the name proposes. It takes all possible moves in
  from a stack. Stack is last in first out but this does not influence the program as it randomly selects
  a move. It then utilizes a random number generator to choose which random move to execute
  It returns the randomly selected move to the method that called it.
   */
  public Move randomMove(Stack possibilities) { // start randomMove method
    int moveID =
        rand.nextInt(possibilities.size()); // assigns a random possibility to an int called moveID
    System.out.println(
        "Agent Randomly Selected Move: " + moveID); // Prints out which random move was selected
    for (int i = 1; i < (possibilities.size() - (moveID)); i++) { // start for loop
      possibilities.pop();
    } // end for loop
    Move selectedMove = (Move) possibilities.pop(); // creates Move for randomly selected move
    return selectedMove; // returns selected move
  } // end randomMove method

  /*
  NEXT BEST MOVE
  For this method I went with the "greediest move" approach where the AI doesn't consider about the consequences of taking a piece
  as long as it takes it the AI is requirements fulfilled. The AI looks to see if there is a possible black piece to be caught.
  If there is a piece that is possible to catch two pieces it then checks to see which piece is worth more. It will capture the
  piece that is worth more e.g. rook > pawn. If there is no other possible piece to catch, it will then do a random move.
   */
  public Move nextBestMove(Stack movesWhite, Stack movesBlack) { // start nextBestMove method
    // declare stacks
    Stack whiteStack = (Stack) movesWhite.clone();
    Stack blackStack = (Stack) movesBlack.clone();

    // declare moves
    Move bestMove = null;
    Move whiteMove;
    Move presentMove;

    // declare Square
    Square blackPosition; // gets the black piece position

    // declare ints
    int points = 0;
    int selectedPiecePoints = 0;

    while (!movesWhite.empty()) { // while the stack is not empty
      whiteMove = (Move) movesWhite.pop(); // the white move is = to popped move
      presentMove = whiteMove; // the current move is = white move

      /*This is where comparing white landing positions to black landing positions.
        If white is able to capture a black piece then it captures
        if it can capture two pieces, it selects best based on points
        if it can't capture it just returns randomMove instead
      */
      while (!blackStack.isEmpty()) { // while black stack is not empty
        points = 0; // initializing variable
        blackPosition = (Square) blackStack.pop();
        if ((presentMove.getLanding().getXC() == blackPosition.getXC())
            && (presentMove.getLanding().getYC() == blackPosition.getYC())) { // start if
          // Assigning each pieces points
          if (blackPosition.getName().equals("BlackQueen")) { // start if
            points = 9;
          } // end if
          else if (blackPosition.getName().equals("BlackRook")) { // start else if
            points = 5;
          } // end else if
          else if (blackPosition.getName().equals("BlackBishop")
              || blackPosition.getName().equals("BlackKnight")) { // start else if
            points = 3;
          } // end else if
          else if (blackPosition.getName().equals("BlackPawn")) { // start else if
            points = 1;
          } // end else if
          else { // start else
            points = 32;
          } // end else
        } // end if
        // Choosing best option to move based on piece points
        if (points > selectedPiecePoints) { // start if
          selectedPiecePoints = points;
          bestMove = presentMove;
        } // end if
      }
      // reloading the black squares
      blackStack = (Stack) movesBlack.clone();
    }

    // Make the best move then if its not available then make a random move.
    if (selectedPiecePoints > 0) {
      System.out.println("Selected AI Agent - Next best move: " + selectedPiecePoints);
      return bestMove;
    }

    return randomMove(whiteStack); // returns random move
  }
} // end class
