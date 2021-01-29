package player;

/**
 * Created by Yun on 8/17/2017.
 */
public class TestCase {

    public static void main(String[] args){
        MachinePlayer player = new MachinePlayer(ColorDefinition.WHITE);
        //player.opponentMove(new Move(4, 2));
        Move move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();

        System.out.println("Starting step move");
        move = player.chooseMove();
        System.out.println(move.x1 + ", " + move.y1);
        player.printBoard();
        System.out.println();
    }

    public void TestValidator() {
        Board b = new Board();
        b.addMove(new Move(2,7), 0);
        b.addMove(new Move(3,1), 0);
        b.addMove(new Move(4,0), 0);
        b.addMove(new Move(4,5), 0);
        b.addMove(new Move(5,2), 0);
        b.addMove(new Move(5,5), 0);
        b.addMove(new Move(6,1), 0);
        //b.addMove(new Move(,2), 1);
        //b.addMove(new Move(3,3), 0);
        //b.addMove(new Move(6,7, 3,0), 0);

        b.printBoard();

        System.out.println(NetworkValidator.networkExist(b, 0));
    }
}
