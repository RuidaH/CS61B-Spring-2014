package player;

/**
 * Created by Yun on 8/16/2017.
 */
class Position implements Comparable<Position>{

    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int compareTo(Position p) {
        if (this.x == p.x && this.y == p.y) {
            return 0;
        }
        if (this.x == p.x) {
            return this.y < p.y ? -1 : 1;
        }
        return this.x < p.x ? -1 : 1;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position)obj;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }
}
