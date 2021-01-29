import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class Maze_Generator2 {
    
    protected int row;
    protected int column;
    protected boolean[][] hWalls;
    protected boolean[][] vWalls;
    protected boolean[][] cellVisited;

    private static final int LEFT  = 1;
    private static final int RIGHT = 2;
    private static final int UP    = 3;
    private static final int DOWN  = 4;
    
    public Maze_Generator2(int x, int y) {
        row = x;
        column = y;
        hWalls = new boolean[row - 1][column];
        vWalls = new boolean[row][column - 1];
        cellVisited = new boolean[row][column];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cellVisited[i][j] = false;
                if (i < row - 1)  hWalls[i][j] = true;
                if (j < column - 1) vWalls[i][j] = true;
            }
        }
        
        
    }
    
    protected void Start_Generation() {
        
        Random rand = new Random();
        
        int startX = rand.nextInt(row);
        int startY = rand.nextInt(column);

        depthFirstSearch(startX, startY);
        
    }
    
    private void depthFirstSearch(int x, int y) {
        
        cellVisited[x][y] = true;
        
        Integer[] randomDirection = generateRandomDirection();
        
        for (int i = 0; i < randomDirection.length; i++) {
            
            switch(randomDirection[i]) {
                case UP:
                    if (x - 1 < 0) continue;
                    if (!cellVisited[x - 1][y]) {
                        hWalls[x - 1][y] = false;  // Break the wall
                        depthFirstSearch(x - 1, y);
                    }
                    break;
                    
                case DOWN:
                    if (x + 1 >= row) continue;
                    if (!cellVisited[x + 1][y]) {
                        hWalls[x][y] = false;
                        depthFirstSearch(x + 1, y);
                    }
                    break;
                    
                case LEFT:
                    if (y - 1 < 0) continue;
                    if (!cellVisited[x][y - 1]) {
                        vWalls[x][y - 1] = false;
                        depthFirstSearch(x, y - 1);
                    }
                    break;
                    
                case RIGHT:
                    if (y + 1 >= column) continue;
                    if (!cellVisited[x][y + 1]) {
                        if (y + 1 != column - 1) {
                            vWalls[x][y + 1] = false;
                        }
                        depthFirstSearch(x, y + 1);
                    }
                    break;
                    
            }
            
        }
        
    }
    
    private Integer[] generateRandomDirection() {
        
        ArrayList<Integer> x = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++)
            x.add(i);
        
        // Shuffle the directions
        Collections.shuffle(x);
        
        return x.toArray(new Integer[4]);
        
    }
    
    /**
     *  toString() returns a string representation of the maze.
     **/
    public String toString() {
        
        int i, j;
        String s = "";
        
        // Print the top exterior wall
        for (i = 0; i < column; i++) {
            s = s + "--";
        }
        s = s + "-\n|";
        
        // Print the maze interior
        for (i = 0; i < row; i++) {
            // Print a row of cells and vertical walls
            for (j = 0; j < column - 1; j++) {
                if (vWalls[i][j]) {
                    s = s + " |";
                } else {
                    s = s + "  ";
                }
            }
            s = s + " |\n+";
            
            if (i < row - 1) {
                for (j = 0; j < column; j++) {
                    if (hWalls[i][j]) {
                        s = s + "-+";
                    } else {
                        s = s + " +";
                    }
                }
                s = s + "\n|";
            }
            
        }
        
        // Print the bottom exterior wall
        // (Note that the first corner has akready been printed)
        for (i = 0; i < column; i++) {
            s = s + "--";
        }
        
        return s + "\n";
        
    }
    
    public static void main(String[] args) {
        int x = (args.length >= 1) ? Integer.parseInt(args[0]) : 8;
        int y = (args.length == 2) ? Integer.parseInt(args[1]) : 8;
        
        Maze_Generator2 maze = new Maze_Generator2(x, y);
        maze.Start_Generation();
        System.out.println(maze);
        
        
    }
    
    
}

