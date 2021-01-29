/* MachinePlayer.java */

package player;

import static player.ColorDefinition.BLACK;
import static player.ColorDefinition.EMPTY;
import static player.ColorDefinition.WHITE;
import static player.Board.DIMENSION;
import static player.Evaluator.evaluate;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    // If "this" player is of color black, color = 0, if "this" player is of
    // color white, color = 1;
    private int color;
    private int searchDepth;
    private Board currentBoard;

    // Creates a machine player with the given color.  Color is either 0 (black)
    // or 1 (white).  (White has the first move.)
    public MachinePlayer(int color) {
        this.color = color;
        currentBoard = new Board();
        searchDepth = 3;
    }

    // Creates a machine player with the given color and search depth.  Color is
    // either 0 (black) or 1 (white).  (White has the first move.)
    public MachinePlayer(int color, int searchDepth) {
        this.color = color;
        this.searchDepth = searchDepth;
        currentBoard = new Board();
    }

    // TODO: Remove after debugging
    public void printBoard() {
        this.currentBoard.printBoard();
    }

    // Returns a new move by "this" player.  Internally records the move (updates
    // the internal game board) as a move by "this" player.
    public Move chooseMove() {
        BestMove bestMove = decideMove(true, currentBoard, 0);
        currentBoard.addMove(bestMove, color);

        return (Move)bestMove;
    }

    private BestMove decideMove(boolean machinePlayer, Board board, int searchDepth){
        BestMove myBest = new BestMove();

        if(searchDepth == this.searchDepth){
            myBest.setScore(evaluate(board, color));
            return myBest;
        }

        boolean isDefaultMoveInitialized = false;
        myBest.setScore(machinePlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE);

        int currentColor = color;
        if(!machinePlayer){
            currentColor = color == WHITE ? BLACK : WHITE;
        }

        // When the further legal move type is ADD.
        if(board.getNumOfChips(currentColor) < 10) {
            for (int x = 0; x < DIMENSION; x++) {
                for (int y = 0; y < DIMENSION; y++) {
                    Move move = new Move(x, y);
                    if(board.addMove(move, currentColor)){
                        if(!isDefaultMoveInitialized) {
                            myBest.updateMove(move);
                            isDefaultMoveInitialized = true;
                        }
                        BestMove reply = decideMove(!machinePlayer, board, searchDepth + 1);
                        board.revertMove(move, currentColor);
                        if((machinePlayer && reply.getScore() > myBest.getScore())
                                || (!machinePlayer && reply.getScore() < myBest.getScore())){
                            myBest.updateMove(move);
                            myBest.setScore(reply.getScore());
                        }
                        if((machinePlayer && myBest.getScore() == Integer.MAX_VALUE )
                                || (!machinePlayer && myBest.getScore() == Integer.MIN_VALUE)){
                            return myBest;
                        }
                    }
                }
            }
        }

        // When the further legal move type is STEP.
        if(board.getNumOfChips(currentColor) >= 10){
            for(int x1 = 0; x1 < DIMENSION; x1++){
                for(int y1 = 0; y1 < DIMENSION; y1++){
                    for(int x2 = 0; x2 < DIMENSION; x2++){
                        for(int y2 = 0; y2 < DIMENSION; y2++){
                            Move move = new Move(x1, y1, x2, y2);
                            if(board.addMove(move, currentColor)){
                                if(!isDefaultMoveInitialized) {
                                    myBest.updateMove(move);
                                    isDefaultMoveInitialized = true;
                                }
                                BestMove reply = decideMove(!machinePlayer, board,searchDepth + 1);
                                board.revertMove(move, currentColor);
                                if((machinePlayer && reply.getScore() > myBest.getScore())
                                        || (!machinePlayer && reply.getScore() < myBest.getScore())) {
                                    myBest.updateMove(move);
                                    myBest.setScore(reply.getScore());
                                }
                                if((machinePlayer && myBest.getScore() == Integer.MAX_VALUE )
                                        || (!machinePlayer && myBest.getScore() == Integer.MIN_VALUE)){
                                    return myBest;
                                }
                            }
                        }
                    }
                }
            }
        }
        return myBest;
    }

    // If the Move m is legal, records the move as a move by the opponent
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method allows your opponents to inform you of their moves.
    public boolean opponentMove(Move m) {
        int opponentColor = EMPTY;
        if(color == WHITE){
            opponentColor = BLACK;
        }
        else {
            opponentColor = WHITE;
        }
        if(currentBoard.isLegal(m, opponentColor)){
            currentBoard.addMove(m, opponentColor);
            return true;
        }
        else {
            return false;
        }
    }

    // If the Move m is legal, records the move as a move by "this" player
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method is used to help set up "Network problems" for your
    // player to solve.
    public boolean forceMove(Move m) {
        if(currentBoard.isLegal(m, color)){
            currentBoard.addMove(m, color);
            return true;
        }
        else {
            return false;
        }
    }
}