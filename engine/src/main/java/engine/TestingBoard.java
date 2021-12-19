package engine;

public interface TestingBoard extends Board {
    void setCell(Cell cell, ChessPiece piece, Color color);

    void setCurrentPlayer(Color color);

    void setWhiteKing(Cell cell);

    void setBlackKing(Cell cell);

    Cell getWhiteKing();

    Cell getBlackKing();

    String getState();

    void clearPrevMove();
}
