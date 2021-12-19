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

    ChessPiece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return switch (this) {
            case PAWN -> "PAWN";
            case BISHOP -> "BISHOP";
            case KING -> "KING";
            case KNIGHT -> "KNIGHT";
            case QUEEN -> "QUEEN";
            case ROOK -> "ROOK";
            case EMPTY -> "EMPTY";
        };
    }
}

