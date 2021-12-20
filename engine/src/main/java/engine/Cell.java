package engine;

public class Cell {
    int x;
    int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        int i = (x) + 'a';
        char i1 = (char) i;
        return "(" + String.valueOf(i1) + ", " + String.valueOf(y + 1) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Cell)) {
            return false;
        }

        Cell cell = (Cell)o;

        return cell.x == this.x && cell.y == this.y;
    }
}
