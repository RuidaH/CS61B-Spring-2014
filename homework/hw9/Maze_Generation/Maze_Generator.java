import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

/*
 * DFS - recursive backtracking algorithm
 */
public class Maze_Generator {
    
    private int row;
    private int column;
    private boolean[][] maze;
    
    private final static int UP    = 1;
    private final static int DOWN  = 2;
    private final static int LEFT  = 3;
    private final static int RIGHT = 4;
    
    public Maze_Generator(int x, int y) {
        row = x;
        column = y;
        maze = new boolean[x][y];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                maze[i][j] = false;
        }
        
    }
    
    private void Start_Generation() {
        
        int start_x, start_y;
        
        Random rand = new Random();
        
        start_x = rand.nextInt(row);
        start_y = rand.nextInt(column);
        
        maze[start_x][start_y] = true;  // The initial cell has been visited
        dfs(start_x, start_y);  // Recursive backtracking
        
    }
    
    /*
     * Recursive function using depth-first search
     * Each time we access 2 cells, so we don't need to check the hWalls
     * and vWalls.
     */
    private void dfs(int x, int y) {
        
        Integer[] randomDirections = generateRandomDirections();
        
        // Checking each directions
        for (int i = 0; i < randomDirections.length; i++) {
            
            switch (randomDirections[i]) {
                
                case UP:
                    // Check if the next cell is out or not
                    if (x - 2 <= 0)  continue;
                    if (!maze[x - 2][y]) {
                        maze[x - 2][y] = true;
                        maze[x - 1][y] = true;
                        dfs(x - 2, y);
                    }
                    break;
                    
                case DOWN:
                    if (x + 2 >= row - 1)  continue;
                    if (!maze[x + 2][y]) {
                        maze[x + 2][y] = true;
                        maze[x + 1][y] = true;
                        dfs(x + 2, y);
                    }
                    break;
                    
                case LEFT:
                    if (y - 2 <= 0)  continue;
                    if (!maze[x][y - 2]) {
                        maze[x][y - 2] = true;
                        maze[x][y - 1] = true;
                        dfs(x, y - 2);
                    }
                    break;
                    
                case RIGHT:
                    if (y + 2 >= column - 1)  continue;
                    if (!maze[x][y + 2]) {
                        maze[x][y + 2] = true;
                        maze[x][y + 1] = true;
                        dfs(x, y + 2);
                    }
                    break;
                    
            }

        }
        
    }
    
    /*
     * Generate an array with random directions 1-4
     * @return Array containing 4 directions in random order
     */
    private Integer[] generateRandomDirections() {
        ArrayList<Integer> randomDirs = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++)
            randomDirs.add(i);
        
        // Shuffle the directions
        Collections.shuffle(randomDirs);
        
        // Convert arrayList to int[]
        return randomDirs.toArray(new Integer[4]);
    }
    
    /*
     * Print the maze out
     */
    public void display() {
        int i, j;
        
        for (i = 0; i < column; i++) {
            for (j = 0; j < row; j++) {
                System.out.print((maze[j][i] == true) ? "* " : "  ");
            }
            System.out.println();
        }

    }
    
    
    public static void main(String[] args) {
        int x = 8;
        int y = 8;
        
        // Read the Input parameter
        if (args.length > 1) {
            try {
                x = Integer.parseInt(args[0]) + 1;
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
            
            try {
                y = Integer.parseInt(args[1]) + 1;
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
        }
        
        Maze_Generator maze = new Maze_Generator(x, y);
        maze.Start_Generation();
        maze.display();
    }
    
}

