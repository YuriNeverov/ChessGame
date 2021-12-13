package engine;

import java.util.List;

public interface Board {

    List<Move> getValidMoves();

    boolean doTurn(Move move);
}
