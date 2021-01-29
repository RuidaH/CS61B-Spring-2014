/* MachinePlayer.java */

package player;

import java.util.concurrent.ThreadLocalRandom;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
    
    int color;
    int chipUsed;
    int searchDepth;
    int opponentColor;
    Board myBoard;

    /**
     * Creates a machine player with the given color and search depth.
     * Color is either 0 (black) or 1 (white).
     * (White has the first move.)
     */
    public MachinePlayer(int color, int searchDepth) {
        
        this.color = color;
        this.chipUsed = 0;
        this.searchDepth = searchDepth;
        myBoard = new Board();
        
        if (color == myBoard.WHITE) opponentColor = myBoard.BLACK;
        else opponentColor = myBoard.WHITE;
        
    }
    
    
    public MachinePlayer(int color) {
        this(color, 5);
    }

    
    /**
     * Returns a new move by "this" player.  Internally records the move
     * (updates the internal game board) as a move by "this" player.
     */
    public Move chooseMove() {
        int idx1, idx2;
        Move m;
        do {
            idx1 = ThreadLocalRandom.current().nextInt(0, 8);
            idx2 = ThreadLocalRandom.current().nextInt(0, 8);
            m = new Move(idx1, idx2);
        } while (!isLegalMove(m) && chipUsed < 10);
        
        chipUsed++;
        myBoard.addMove(m, color);
        System.out.println("++ The initial number : " + m.x1 + ", " + m.y1);
         
        return m;
    }

    
    // If the Move m is legal, records the move as a move by the opponent
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of
    // "this" player.  This method allows your opponents to inform you of
    //  their moves.
    public boolean opponentMove(Move m) {
      return false;
    }

    
    /**
     * If the Move m is legal, records the move as a move by "this" player
     * (updates the internal game board) and returns true.
     * If the move is illegal, returns false without modifying the internal state of "this" player.
     * This method is used to help set up "Network problems" for your player to solve.
     */
    public boolean forceMove(Move m) {
      return false;
    }
    
    
    /**
     * Check if the move m is a legal move or not
     *
     * @param m the given move m
     */
    protected boolean isLegalMove(Move m) {
        
        if ((chipUsed >= 10 && m.moveKind == m.ADD) ||
            (chipUsed < 10 && m.moveKind == m.STEP) ||
            illegalPlace(m, color) || isSeatTaken(m)) {
            return false;
        } else {
            SList chipsList;
            // Assume the move has been made and check all the connected chips
            chipsList = connectedChips(m, color);

            if (chipsList.length() >= 3) {
                return false;
            }
        }
        
        return true;

    }
    
    
    /**
     * Find all the connected chips based on your current situation on the board
     *
     * @param x the x-coordinate of the board
     * @param y the y-coordinate of the board
     */
    private SList connectedChips(Move m, int color) {
        SList sl = new SList();
        boolean[][] visited = new boolean[8][8];
        connectedChips(m.x1, m.y1, color, sl, visited);
        return sl;
    }
    
    /**
     * Inner algorithm of finding all connected chips
     */
    private void connectedChips(int x, int y, int color, SList sl, boolean[][] visited) {
        
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // Just use Move to store the location in the SList
                System.out.println("@@ " + i + ", " + j);
                Move m = new Move(i, j);
                if (illegalPlace(m, color) || visited[i][j]) {
                    System.out.println("## Out of space / Visited: " + i + ", " + j);
                    continue;
                }
                if (myBoard.board[i][j] == opponentColor && !visited[i][j]) {
                    visited[i][j] = true;
                }
                
                if (myBoard.board[i][j] == color && !visited[i][j]){
                    sl.insertBack(m);
                    System.out.println(sl.toString());
                    System.out.println("**** " + i + ", " + j);
                    visited[i][j] = true;
                    connectedChips(i, j, color, sl, visited);
                }
                
            }
        }
        
    }
    

    /**
     * Check if the move m is made to the wrong place
     *
     * @param m the given move
     * @param color the player on the board (Black or White)
     * @return true if it is moved to the wrong goal area, falser otherwise
     */
    private boolean illegalPlace(Move m, int color) {
        
        // Check if the move is made to the corner of the board
        if ( (m.x1 == 0 && (m.y1 == 0 || m.y1 == 7)) ||
            (m.x1 == 7 && (m.y1 == 0 || m.y1 == 7)) ) {
            return true;
        }

        // Check if the move m is made to the wrong goal area
        if ((color == myBoard.WHITE) &&
            ( (m.y1 == 0 && (m.x1 > 0 && m.x1 < 7)) ||
              (m.y1 == 7 && (m.x1 > 0 && m.x1 < 7)) )) {
            return true;
        }
        if ((color == myBoard.BLACK) &&
            ( (m.x1 == 0 && (m.y1 > 0 && m.y1 < 7)) ||
              (m.x1 == 7 && (m.y1 > 0 && m.y1 < 7)) )) {
            return true;
        }
        
        // If the location outside the board
        if ( (m.x1 < 0) || (m.y1 < 0) || (m.x1 > 7) || (m.y1 > 7) ) {
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Check if the move m is move to a place that's been taken
     *
     * @param m the given move
     * @return true if it is moved to a taken place, falser otherwise
     */
    private boolean isSeatTaken(Move m) {
        if (myBoard.board[m.x1][m.y1] != myBoard.UNVISITED) {
            return true;
        }
        return false;
    }

    
//    /**
//     * Obtain all the valid moves based on the current situation of the gameBoard
//     *
//     * @param m the given move m
//     */
//    protected SList allValidMoves() {
//
//    }

}
