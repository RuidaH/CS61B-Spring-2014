/* Board.java */

package player;

class Board {
    
    protected int[][] board;
    protected int whiteChips;
    protected int blackChips;
    
    public final static int WHITE = 1;
    public final static int BLACK = 0;
    public final static int UNVISITED = -1;
    
    /**
     * Create a 8x8 board
     */
    public Board() {
        whiteChips = 0;
        blackChips = 0;
        board = new int[8][8];
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                board[x][y] = UNVISITED;
    }
    
    /**
     * Add the move on the board
     *
     * @param m Move class for making a movement on the board
     * @param color the player of the board (Black or White)
     */
    protected void addMove(Move m, int color) {
        board[m.x1][m.y1] = color;
        if (color == WHITE) whiteChips++;
        else blackChips++;
    }
    
    /**
     * Undo the move on the board
     *
     * @param m Move class for making a movement on the board
     * @param color the player of the board (Black or White)
     */
    protected void undoMove(Move m, int color) {
        board[m.x1][m.y1] = UNVISITED;
        if (color == WHITE) whiteChips--;
        else blackChips--;
    }
    
    /**
     * Move the chip from (fromX, fromY) to (toX, toY) on the board
     *
     * @param m Move class for making a movement on the board
     */
    protected void stepMove(Move m) {
        board[m.x2][m.y2] = board[m.x1][m.y1];
        board[m.x1][m.y1] = UNVISITED;
    }
     
//    /**
//     * Implement depth-first search of the board
//     * with specific player.
//     */
//    protected boolean findNetwork(int x, int y, int color){
//
//    }
//
//    /**
//     * Method to decide whether these chips can form something that is close to Network
//     * It's used for Minimax Algorithm
//     */
//    protected boolean evaluationOfBoard() {
//        // Not sure to put it in board.java or machinePlayer.java
//        // Decied later
//    }
//

    
}
