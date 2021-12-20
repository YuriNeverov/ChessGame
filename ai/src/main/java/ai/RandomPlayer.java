package ai;

import java.util.List;
import java.util.Random;
import engine.*;

public class RandomPlayer implements Player {

    @Override
    public Move getMove(Board board) {
        Random random = new Random();

        List<Move> moves = board.getAllValidMoves();

        if (moves.isEmpty()) {
            return null;
        }

        return moves.get(random.nextInt(moves.size()));
    }

    @Override
    public Move getMove(Board board, double timeout) {
        return getMove(board);
    }

    public static void main(String[] args) {
//        Board board = new MatrixBoard();
//        Player player = new RandomPlayer();
//        Move move;
//
//        move = new Move(new Cell(5, 1), new Cell(5, 3));
//        System.out.println(move);
//        board.makeMove(move);
//
//        move = new Move(new Cell(4, 6), new Cell(4, 4));
//        System.out.println(move);
//        board.makeMove(move);
//
//        move = new Move(new Cell(6, 1), new Cell(6, 3));
//        System.out.println(move);
//        board.makeMove(move);
//
//        move = new Move(new Cell(3, 7), new Cell(7, 3));
//        System.out.println(move);
//        board.makeMove(move);

        int cnt = 0;
        for (int j = 0; j < 10000; j++) {
            Board board = new MatrixBoard();
            Player player = new RandomPlayer();
            for (int i = 0; i < 1000; i++) {

                Move move = player.getMove(board, 1.0);
                if (move == null) {
//                    if (i < 5) {
//                        System.out.println("Game is over on " + String.valueOf(i));
//                        List<Move> moves = board.getMovesHistory();
//                        for (Move curMove: moves) {
//                            int x = curMove.getFrom().getX();
//                            int y = curMove.getFrom().getY();
//                            System.out.print("move = new Move(new Cell(" + x + ", " + y);
//                            x = curMove.getTo().getX();
//                            y = curMove.getTo().getY();
//                            System.out.println("), new Cell(" + x + ", " + y + "));");
//                            System.out.println("System.out.println(move);");
//                            System.out.println("board.makeMove(move);");
//                            System.out.println();
//                        }
//                        return;
//                    }
                    cnt += i + 1;
                    break;
                }
//                System.out.println(move.toString());
                board.makeMove(move);
            }
//            if (board.getCurrentPlayerColor() == Color.WHITE) {
//                cnt++;
//            }
        }
        System.out.println(cnt);
    }
}
