package engine;

public class Cell {
    int x;
    int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        int i = (x) + 'a';
        char i1 = (char) i;
        return "(" + String.valueOf(i1) + ", " + String.valueOf(y + 1) + ")";
    }
}
