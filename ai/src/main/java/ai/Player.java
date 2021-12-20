package ai;

import engine.*;

public interface Player {
    public Move getMove(Board board);

    public Move getMove(Board board, double timeout);
}
