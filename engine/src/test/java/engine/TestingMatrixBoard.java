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
        StringBuilder strBuilder = new StringBuilder();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                strBuilder.append(String.format("%s ", getBiChar(new Cell(y, 7 - x))));
            }
            strBuilder.append("\n");
        }
        strBuilder.append(
            String.format(
                "White King: (%d, %d), Black King: (%d, %d)\n",
                whiteKing.x, whiteKing.y, blackKing.x, blackKing.y
            )
        );
        strBuilder.append(
            String.format(
                "Current player color: %s\n",
                color
            )
        );
        return strBuilder.toString();
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
