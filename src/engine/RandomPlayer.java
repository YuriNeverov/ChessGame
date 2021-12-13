package engine;

import java.util.List;
import java.util.Random;

public class RandomPlayer implements Player {

    @Override
    public Move getMove(Board board) {
        Random random = new Random();

        List<Move> moves = board.getValidMoves();

        return moves.get(random.nextInt() % moves.size());
    }
}
