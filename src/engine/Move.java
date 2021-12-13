package engine;

import kotlin.Pair;

public class Move {
    Cell from;
    Cell to;

    Move(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }
}
