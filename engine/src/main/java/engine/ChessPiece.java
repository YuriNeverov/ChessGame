package engine;

public enum ChessPiece {
    PAWN(1),
    BISHOP(2),
    KING(3),
    KNIGHT(4),
    QUEEN(5),
    ROOK(6),
    EMPTY(7);

    private final int value;

    private ChessPiece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

