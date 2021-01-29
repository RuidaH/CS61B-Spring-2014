package player;


import java.util.ArrayList;

import static player.Board.DIMENSION;
import static player.ColorDefinition.BLACK;
import static player.ColorDefinition.WHITE;

/**
 * Created by Yun on 8/16/2017.
 */
class Evaluator {

    // Given a certain board and the color of the machine player, return a score.
    // The higher the score, the more possible the machine player will win.
    // If the machine player wins, return Integer.MAX_VALUE.
    // If the opponent wins, return Integer.MIN_VALUE.
    public static int evaluate(Board board, int machinePlayerColor){
        int opponentColor = machinePlayerColor == WHITE ? BLACK : WHITE;

        if(NetworkValidator.networkExist(board, machinePlayerColor)){
            return Integer.MAX_VALUE;
        }
        else if(NetworkValidator.networkExist(board, opponentColor)){
            return Integer.MIN_VALUE;
        }

        ArrayList<Pair> pairOfMachine = listOfPairs(board, machinePlayerColor);
        ArrayList<Pair> pairOfOpponent = listOfPairs(board, opponentColor);

        double scoreOfMachine = 0;
        double scoreOfOpponent = 0;

        for(Pair pair : pairOfMachine){
            scoreOfMachine += pair.score;
        }

        for (Pair pair : pairOfOpponent){
            scoreOfOpponent += pair.score;
        }

        scoreOfMachine = scoreOfMachine - 80 * scoreOnVar(pairOfMachine) + scoreOfChips(board, machinePlayerColor);
        scoreOfOpponent = scoreOfOpponent - 80 * scoreOnVar(pairOfOpponent) + scoreOfChips(board, opponentColor);
        return (int)(scoreOfMachine - scoreOfOpponent);
    }

    // Calculate the score depending on the position of the chips of a given color.
    public static int scoreOfChips(Board board, int color){
        double score = 0;
        double central = 3.5;
        int numOfChipsInGoalA1 = 0;
        int numOfChipsInGoalA2 = 0;
        for(int x = 0; x < DIMENSION; x++){
            for(int y = 0; y < DIMENSION; y++){
                if(board.getBoard()[x][y] == color){
                    if(inGoalArea(new Position(x, y))){
                        score += 40;
                    }
                    else {
                        double chipScore = 50 -
                                (java.lang.Math.abs(x - central) + java.lang.Math.abs(y - central))
                                * (java.lang.Math.abs(x - central) + java.lang.Math.abs(y - central));
                        score += chipScore;
                    }

                    if((color == BLACK && y == 0)
                            ||(color == WHITE && x == 0)){
                        numOfChipsInGoalA1++;
                    }
                    if((color == BLACK && y == 7)
                            ||(color == WHITE && x == 7)){
                        numOfChipsInGoalA2++;
                    }
                }
            }
        }

        if(numOfChipsInGoalA1 > 0 && numOfChipsInGoalA2 > 0){
            score += 100;
        }

        return (int)score;
    }

    // Determine whether a position is in goal area.
    public static boolean inGoalArea(Position position){
        return position.getX() == 0
                || position.getX() == DIMENSION - 1
                || position.getY() == 0
                || position.getY() == DIMENSION - 1;
    }

    // Calculate the variance of an int array.
    public static double variance(int[] directions, int numOfPairs){
        double var = 0;
        double mean = numOfPairs / directions.length;

        for(int i: directions){
            var += (i - mean) * (i - mean);
        }

        var = java.lang.Math.sqrt(var / directions.length);

        return var;
    }

    // Given a list of connected pairs, calculate the score depending on the distribution of the different directions of the pairs.
    // The larger the variance, the higher the score is.
    public static double scoreOnVar( ArrayList<Pair> listOfConnectedPairs){
        if (listOfConnectedPairs.size() == 0) {
            return 0;
        }

        int[] distributionOfDirections = new int[4];
        double score = 0;

        for(Pair p: listOfConnectedPairs){
            if(p.direction == 0){
                distributionOfDirections[0]++;
            }
            else if(p.direction == 1){
                distributionOfDirections[1]++;
            }
            else if(p.direction == 2){
                distributionOfDirections[2]++;
            }
            else {
                distributionOfDirections[3]++;
            }
        }

        score = variance(distributionOfDirections, listOfConnectedPairs.size());

        return score;
    }

    // Given the board, determine whether the given two positions can form a legal pair.
    // If they can form a legal pair, add the pair to the list of connected pairs.
    public static void addPair(Position p1, Position p2, Board board, ArrayList<Pair> listOfConnectedPairs){
        if(!p1.equals(p2) && Pair.connected(board, p1, p2)){
            int score = (inGoalArea(p1) || inGoalArea(p2)) ? 40 : 20;
            Pair pair = new Pair(p1, p2, score);

            if(!listOfConnectedPairs.contains(pair)) {
                listOfConnectedPairs.add(pair);
            }
        }
    }

    // Given the board, return the list of connected pairs of the given color.
    public static ArrayList<Pair> listOfPairs(Board board, int color){
        ArrayList<Pair> listOfConnectedPairs = new ArrayList<Pair>();

        for(int x = 0; x < DIMENSION; x++){
            for(int y = 0; y < DIMENSION; y++){

                if(board.getBoard()[x][y] == color) {
                    Position p1 = new Position(x, y);

                    for(int x1 = 0; x1 < DIMENSION; x1++){
                        Position p2 = new Position(x1, y);
                        addPair(p1, p2, board, listOfConnectedPairs);
                    }

                    for(int y1 = 0; y1 < DIMENSION; y1++){
                        Position p2 = new Position(x, y1);
                        addPair(p1, p2, board, listOfConnectedPairs);
                    }

                    int x1, y1;
                    int min = x < y ? x : y;
                    x1 = x - min;
                    y1 = y - min;
                    while (true){
                        x1++;
                        y1++;
                        if(!withinRange(x1) || !withinRange(y1)){
                            break;
                        }
                        Position p2 = new Position(x1, y1);
                        addPair(p1, p2, board, listOfConnectedPairs);
                    }

                    int distanceToEdge = Math.min(y, DIMENSION - 1 - x);
                    x1 = x + distanceToEdge;
                    y1 = y - distanceToEdge;
                    while (true){
                        x1--;
                        y1++;
                        if(!withinRange(x1) || !withinRange(y1)){
                            break;
                        }
                        Position p2 = new Position(x1, y1);
                        addPair(p1, p2, board, listOfConnectedPairs);
                    }
                }
            }
        }

        return listOfConnectedPairs;
    }

    private static boolean withinRange(int i){
        return (i >= 0 && i < DIMENSION);
    }
}
