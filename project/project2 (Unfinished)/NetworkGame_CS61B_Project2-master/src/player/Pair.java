package player;

/**
 * Created by Yun on 8/20/2017.
 */
public class Pair {
    Position p1, p2;
    int score;
    int direction = 0;

    public Pair(Position p1, Position p2, int score){
        this.p1 = p1;
        this.p2 = p2;
        this.score = score;
        if(p1.getX() == p2.getX()){
            direction = 0;
        }
        else if(p1.getY() == p2.getY()){
            direction = 1;
        }
        else if((p1.getX() < p2.getX() && p1.getY() < p2.getY())
                || (p1.getX() > p2.getX() && p1.getY() > p2.getY())){
            direction = 2;
        }
        else {
            direction = 3;
        }
    }

    public boolean equals(Object pair){
        if(pair instanceof Pair) {
            pair = (Pair) pair;
            if (this.score == ((Pair) pair).score
                    && (((Pair) pair).p1.equals(this.p1) && ((Pair) pair).p2.equals(this.p2)
                    || ((Pair) pair).p1.equals(this.p2) && ((Pair) pair).p2.equals(this.p1))) {
                return true;
            }
        }

        return false;
    }

    static boolean withinGoalArea(Position p){
        if(p.getX() == 0 || p.getY() == 0 || p.getX() == 7 || p.getY() == 7){
            return true;
        }
        return false;
    }

    // If the chips on the positions p1 and p2 are connected on the given board, return true. Otherwise, return false;
    public static boolean connected(Board board, Position p1, Position p2){
        int p1Color = board.getBoard()[p1.getX()][p1.getY()];
        int p2Color = board.getBoard()[p2.getX()][p2.getY()];
        if(NetworkValidator.formedStraightLine(p1, p2)
                && p1Color == p2Color
                && !NetworkValidator.hasEnemyInBetween(board, p1Color, p1, p2)
                && (!withinGoalArea(p1) || !withinGoalArea(p2))){
            return true;
        }
        return false;
    }

}
