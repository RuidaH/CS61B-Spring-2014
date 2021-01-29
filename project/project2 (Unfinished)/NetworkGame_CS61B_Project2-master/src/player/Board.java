package player;

import static player.Move.ADD;
import static player.Move.QUIT;
import static player.Move.STEP;
import static player.ColorDefinition.BLACK;
import static player.ColorDefinition.WHITE;
import static player.ColorDefinition.EMPTY;

/**
 * Created by Yun on 8/16/2017.
 */
class Board {

    private int[][] board;
    public final static int DIMENSION = 8;
    private int numOfBlack = 0;
    private int numOfWhite = 0;

    // Construct a board in which every grid is empty.
    public Board(){
        board = new int[8][8];
        for(int x = 0; x < DIMENSION; x++){
            for(int y = 0; y < DIMENSION; y++){
                board[x][y] = EMPTY;
            }
        }
    }

    // Return the current board.
    public int[][] getBoard(){
        return board;
    }

    public int getNumOfChips(int color){
        return color == BLACK ? numOfBlack : numOfWhite;
    }

    // Update the board after a certain move.
    // Return true if the move is successful. Otherwise, return false.
    // If the move the made by the player of white, then color = 1. Otherwise, color = 0;
    public boolean addMove(Move move, int color){
        if(!isLegal(move, color)){
            return false;
        }
        else{
            takeMove(move, color);
            return true;
        }
    }

    // Revert the board back to the configuration before the move was taken.
    // Return true if the move is reverted successfully. Otherwise, return false.
    public boolean revertMove (Move move, int color){
        if(move.moveKind == ADD){
            if(board[move.x1][move.y1] != color){
                return false;
            }
            board[move.x1][move.y1] = EMPTY;
        }
        else if(move.moveKind == STEP){
            if(board[move.x1][move.y1] != color || board[move.x2][move.y2] != EMPTY){
                return false;
            }
            board[move.x1][move.y1] = EMPTY;
            board[move.x2][move.y2] = color;
        }
        if(color == BLACK){
            numOfBlack -= 1;
        }
        else {
            numOfWhite -= 1;
        }
        return true;
    }

    // Determine whether a move is legal or not;
    // If the move is legal, return true. Otherwise, return false.
    public boolean isLegal(Move move, int color){
        if(move.moveKind == QUIT){
            return false;
        }
        if( !withinRange(move.x1) || !withinRange(move.y1)){
            return false;
        }
        if(move.moveKind == STEP){
            if(!withinRange(move.x2) || !withinRange(move.y2)) {
                return false;
            }
            if(board[move.x2][move.y2] != color){
                return false;
            }
        }

        if(isCorner(move)
                || isOccupied(move.x1, move.y1)
                || isGoalOfTheOtherColor(move, color)
                || !moveTypeIsCorrect(move, color)
                || hasConnectedGroup(move, color)){
            return false;
        }

        return true;
    }

    // Determine whether a move's type is reasonable.
    private boolean moveTypeIsCorrect(Move move, int color){
        int num = (color == BLACK) ? numOfBlack : numOfWhite;
        if((move.moveKind == ADD && num >= 10)
                || (move.moveKind == STEP && num < 10)){
            return false;
        }
        return true;
    }

    // Determine whether a move is to any of the four corners.
    private boolean isCorner(Move move){
        return (move.x1 == 0 && move.y1 == 0)
                || (move.x1 == DIMENSION - 1 && move.y1 == 0)
                || (move.x1 == 0 && move.y1 == DIMENSION - 1)
                || (move.x1 == DIMENSION - 1 && move.y1 == DIMENSION - 1);
    }

    // Determine whether a move is to a position that is already occupied.
    public boolean isOccupied(int x, int y){
        return board[x][y] != EMPTY;
    }

    // Determine whether a move is to the goal area of the other color.
    private boolean isGoalOfTheOtherColor(Move move, int color){
        if(color == BLACK
                && ((move.x1 == 0 && withinRange(move.y1)) || (move.x1 == 7 && withinRange(move.y1)))){
            return true;
        }
        if(color == WHITE
                && ((move.y1 == 0 && withinRange(move.x1)) || (move.y1 == 7 && withinRange(move.x1)))){
            return true;
        }
        return false;
    }

    // Determine whether the player of the given color has a connected group after the given move.
    // This function first count the number of chips around the target chip of the same color.
    // The number is recorded by @numOfConnectedChips.
    // If numOfConnectedChips = 0, return false.
    // If numOfConnectedChips >= 2, return true.
    // If numOfConnectedChips = 1, further check the number of chips around the founded chip @p.
    // If there are two more adjacent chips has the same color as @p, return true.
    private boolean hasConnectedGroup(Move move, int color){
        takeMove(move, color);
        int numOfConnectedChips = 0;
        Position p = null;

        for(int x = move.x1 - 1; x <= move.x1 + 1; x++){
            for(int y = move.y1 - 1; y <= move.y1 + 1; y++){
                if(!withinRange(x) || !withinRange(y) || (x == move.x1 && y == move.y1)){
                    continue;
                }
                if(board[x][y] == color){
                    numOfConnectedChips += 1;
                    if(numOfConnectedChips == 2){
                        revertMove(move, color);
                        return true;
                    }
                    p = new Position(x, y);
                }
            }
        }

        if(numOfConnectedChips == 0){
            revertMove(move, color);
            return false;
        }
        else {
            for(int x = p.getX() - 1; x <= p.getX() + 1; x++){
                for(int y = p.getY() - 1; y <= p.getY() + 1; y++){
                    if(!withinRange(x) || !withinRange(y)
                            || (x == move.x1 && y == move.y1)
                            || (x == p.getX() && y == p.getY())){
                        continue;
                    }
                    if(board[x][y] == color){
                        revertMove(move, color);
                        return true;
                    }
                }
            }
        }
        revertMove(move, color);
        return false;
    }

    // Determine whether a coordinate(x or y) is within range from 0 to 7.
    private boolean withinRange(int i){
        return (i >= 0 && i < DIMENSION);
    }

    // Take the move anyways.
    private void takeMove(Move move, int color){
        if(move.moveKind == ADD){
            board[move.x1][move.y1] = color;
        }
        else if(move.moveKind == STEP){
            board[move.x1][move.y1] = color;
            board[move.x2][move.y2] = EMPTY;
        }
        if(color == BLACK){
            numOfBlack += 1;
        }
        else {
            numOfWhite += 1;
        }
    }

    // Given a certain board, return all the positions that already taken by the player of the given color.
    public Position[] getPositions(int color){
        Position[] p = null;
        return p;
    }

    // TODO: delete after finishing the project.
    public void printBoard(){
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                System.out.print(board[x][y] + " ");
            }
            System.out.print("\n");
        }
    }

}
