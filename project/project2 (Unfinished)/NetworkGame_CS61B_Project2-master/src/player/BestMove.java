package player;

/**
 * Created by Yun on 8/18/2017.
 */
public class BestMove extends Move {

    private int score;

    public void updateMove(Move move){
        this.moveKind = move.moveKind;
        this.x1 = move.x1;
        this.x2 = move.x2;
        this.y1 = move.y1;
        this.y2 = move.y2;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
