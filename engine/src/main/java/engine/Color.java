package engine;

public enum Color {
    BLACK(1), WHITE(2), NOCOLOR(3);

    private final int value;

    private Color(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "BLACK";
            case WHITE -> "WHITE";
            case NOCOLOR -> "NOCOLOR";
        };
    }
}
