package engine;

import java.util.List;
import java.util.Random;

public class RandomPlayer implements Player {

    @Override
    public Move getMove(Board board) {
        Random random = new Random();

        List<Move> moves = board.getValidMoves();

        if (moves.isEmpty()) {
            return null;
        }

        return moves.get(random.nextInt(0, moves.size()));
    }

    public static void main(String[] args) {
        int cnt = 0;
        for (int j = 0; j < 10000; j++) {
            Board board = new MatrixBoard();
            Player player = new RandomPlayer();
            for (int i = 0; i < 10000; i++) {
                Move move = player.getMove(board);
                if (move == null) {
                    System.out.println("Game is over on " + String.valueOf(i));
                    break;
                }
                //System.out.println(move.toString());
                board.doTurn(move);
            }
            if (board.turn() == Color.WHITE) {
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}
