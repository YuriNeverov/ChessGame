package engine;

public class Move {
    Cell from;
    Cell to;

    public Move(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[" + from.toString() + ", " + to.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Move)) {
            return false;
        }

        Move move = (Move)o;

        return move.from.equals(this.from) && move.to.equals(this.to);
    }
}
