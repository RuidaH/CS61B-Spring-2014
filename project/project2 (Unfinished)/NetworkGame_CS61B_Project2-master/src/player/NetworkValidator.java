package player;
import java.util.ArrayList;

import static player.ColorDefinition.*;

/**
 * Created by Yun on 8/20/2017.
 */
public class NetworkValidator {

    // Given a certain board, determine whether the board has a network for the given color.
    public static boolean networkExist(Board board, int color){
        ArrayList<Position> connectedChips = new ArrayList<Position>();
        return hasNetwork(board, color, -1, connectedChips);
    }

    // The search for a network start from the top if the color is black, otherwise, start from the left.
    // @direction: value choose from the set{ 1, 2, 3, 4, 5, 6, 7, 8}, which on behalf of the 8 different directions.
    // If @direction = -1, it means the former step has no direction.
    public static boolean hasNetwork(Board board, int color,
                                     int direction, ArrayList<Position> listOfPositions){

        if(listOfPositions.size() >= 6){
            Position temp = listOfPositions.get(listOfPositions.size() - 1);
            if((color == BLACK && temp.getX() >=1 && temp.getX() <= 6 && temp.getY() == 7 )
                    || (color == WHITE && temp.getY() >= 1 && temp.getY() <= 6 && temp.getX() == 7)){

                // TODO: delete after finishing the project.
                //for(Position p: listOfPositions){
            }
        }

        boolean reply = false;

        // Start the search in the goal area.
        if(listOfPositions.size() == 0){
            if(color == BLACK){
                for(int x = 1; x <= 6; x++){
                    final int y = 0;
                    if((board.getBoard())[x][y] == color) {
                        Position nextPosition = new Position(x, y);
                        listOfPositions.add(nextPosition);
                        reply = hasNetwork(board, color, -1, listOfPositions);
                        listOfPositions.remove(nextPosition);

                        if (reply == true) {
                            return true;
                        }
                    }
                }
            }

            if(color == WHITE){
                for(int y = 1; y <= 6; y++){
                    final int x = 0;
                    if((board.getBoard())[x][y] == color) {
                        Position nextPosition = new Position(x, y);
                        listOfPositions.add(nextPosition);
                        reply = hasNetwork(board, color, -1, listOfPositions);
                        listOfPositions.remove(nextPosition);
                        if (reply == true) {
                            return true;
                        }
                    }
                }
            }
        }
        else {
            for(int x = 1; x <= 7; x++){
                for(int y = 1; y <= 7; y++){
                    Position nextPosition = new Position(x, y);
                    Position currentPosition = listOfPositions.get(listOfPositions.size() - 1);
                    int nextDirection = determineDirection(currentPosition, nextPosition);

                    // The conditions to become a valid nextPosition:
                    //     1. the color of the position is the same as the color in checking;
                    //     2. the position has not been included as a connected chip;
                    //     3. the direction going to the position from the previous one is different from the previous direction;
                    //     4. there is no enemy chip placed between the chip in the position and the chip in the previous position;
                    //     5. the position and the previous position formed a straight line.
                    if((board.getBoard())[x][y] == color
                            && !listOfPositions.contains(nextPosition)
                            && direction != nextDirection
                            && formedStraightLine(currentPosition, nextPosition)
                            && !hasEnemyInBetween(board, color, currentPosition, nextPosition)){
                        listOfPositions.add(nextPosition);
                        reply = hasNetwork(board, color, nextDirection, listOfPositions);
                        listOfPositions.remove(nextPosition);

                        if (reply == true) {
                            return true;
                        }
                    }

                }
            }
        }

        return false;
    }

    // Determine whether two positions are in a straight line.
    public static boolean formedStraightLine(Position p1, Position p2){
        return p1.getX() == p2.getX()
                || p1.getY() == p2.getY()
                || Math.abs(p1.getX() - p2.getX()) == Math.abs(p1.getY() - p2.getY());
    }

    // Given the board and the color of the machine the player, determine whether there is an enemy chip between two positions.
    public static boolean hasEnemyInBetween(Board board, int color, Position currentPosition, Position nextPosition){
        Position checkPos = new Position(currentPosition.getX(), currentPosition.getY());

        while(!checkPos.equals(nextPosition)){
            if(checkPos.getX() < nextPosition.getX()){
                checkPos.setX(checkPos.getX() + 1);
            }
            else if(checkPos.getX() > nextPosition.getX()){
                checkPos.setX(checkPos.getX() - 1);
            }

            if(checkPos.getY() < nextPosition.getY()){
                checkPos.setY(checkPos.getY() + 1);
            }
            else if(checkPos.getY() > nextPosition.getY()){
                checkPos.setY(checkPos.getY() - 1);
            }

            int checkColor = board.getBoard()[checkPos.getX()][checkPos.getY()];
            if(checkColor != EMPTY && checkColor != color){
                return true;
            }
        }

        return false;
    }

    // Determine the direction of from the current position to the next position.
    public static int determineDirection(Position currentPosition, Position nextPosition){
        if(nextPosition.getX() > currentPosition.getX() && nextPosition.getY() > currentPosition.getY()){
            return 1;
        }
        else if(nextPosition.getX() == currentPosition.getX() && nextPosition.getY() > currentPosition.getY()){
            return 2;
        }
        else if(nextPosition.getX() < currentPosition.getX() && nextPosition.getY() > currentPosition.getY()){
            return 3;
        }
        else if(nextPosition.getX() > currentPosition.getX() && nextPosition.getY() == currentPosition.getY()){
            return 4;
        }
        else if(nextPosition.getX() < currentPosition.getX() && nextPosition.getY() == currentPosition.getY()){
            return 5;
        }
        else if(nextPosition.getX() > currentPosition.getX() && nextPosition.getY() < currentPosition.getY()){
            return 6;
        }
        else if(nextPosition.getX() == currentPosition.getX() && nextPosition.getY() < currentPosition.getY()){
            return 7;
        }
        else {
            return 8;
        }
    }
}
