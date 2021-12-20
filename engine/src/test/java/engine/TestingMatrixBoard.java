package engine;

public class TestingMatrixBoard extends MatrixBoard implements TestingBoard {
    @Override
    public void setCell(Cell cell, ChessPiece piece, Color color) {
        if (cell.x >= 0 && cell.x < 8 && cell.y >= 0 && cell.y < 8) {
            board[cell.x][cell.y] = piece;
            colors[cell.x][cell.y] = color;
        }
    }

    @Override
    public void setCurrentPlayer(Color color) {
        this.color = color;
    }

    @Override
    public void setWhiteKing(Cell cell) {
        whiteKing = cell;
    }

    @Override
    public void setBlackKing(Cell cell) {
        blackKing = cell;
    }

    @Override
    public Cell getWhiteKing() {
        return whiteKing;
    }

    @Override
    public Cell getBlackKing() {
        return blackKing;
    }

    private String getBiChar(Cell cell) {
        ChessPiece piece = getPiece(cell);
        Color color = getPieceColor(cell);
        if (color == Color.WHITE) {
            return switch (piece) {
                case PAWN -> "WP";
                case BISHOP -> "WB";
                case KING -> "WK";
                case KNIGHT -> "WN";
                case QUEEN -> "WQ";
                case ROOK -> "WR";
                case EMPTY -> "00";
            };
        } else {
            return switch (piece) {
                case PAWN -> "BP";
                case BISHOP -> "BB";
                case KING -> "BK";
                case KNIGHT -> "BN";
                case QUEEN -> "BQ";
                case ROOK -> "BR";
                case EMPTY -> "00";
            };
        }
    }

    @Override
    public String getState() {
        return toString();
    }

    @Override
    public void clearPrevMove() {
        prevMoves.clear();
        prevMoveColor.clear();
        prevMovePieces.clear();
    }

    @Override
    public void clear() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = ChessPiece.EMPTY;
                colors[i][j] = Color.NOCOLOR;
            }
        }
    }
}
