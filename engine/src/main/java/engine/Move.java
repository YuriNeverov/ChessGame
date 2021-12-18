package engine;

public class Move {
    Cell from;
    Cell to;

    Move(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[" + from.toString() + ", " + to.toString() + "]";
    }
}
