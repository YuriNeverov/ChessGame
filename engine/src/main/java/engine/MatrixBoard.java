package engine;

import java.util.ArrayList;
import java.util.List;

class MatrixBoard implements Board {
    private final int BoardSize = 8;

    public Color color = Color.WHITE;

    public Color turn() {
        return color;
    }

    private ChessPiece[][] board = new ChessPiece[BoardSize][BoardSize];
    private Color[][] colors = new Color[BoardSize][BoardSize];

    private Cell blackKing = new Cell(4, 7);
    private Cell whiteKing = new Cell(4, 0);

    private Move prevMove = null;

    private ChessPiece prevMovePiece = null;

    private Color prevMoveColor = null;

    public MatrixBoard() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < BoardSize; j++) {
                colors[j][i] = Color.WHITE;
                colors[j][BoardSize - i - 1] = Color.BLACK;
            }
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[j][i] = ChessPiece.EMPTY;
            }
        }
        for (int i = 0; i < 8; i++) {
            board[i][1] = board[i][6] = ChessPiece.PAWN;
        }
        board[0][0] = board[7][0] = board[0][7] = board[7][7] = ChessPiece.ROOK;
        board[1][0] = board[6][0] = board[1][7] = board[6][7] = ChessPiece.KNIGHT;
        board[2][0] = board[5][0] = board[2][7] = board[5][7] = ChessPiece.BISHOP;
        board[3][0] = board[3][7] = ChessPiece.QUEEN;
        board[4][0] = board[4][7] = ChessPiece.KING;
    }

    @Override
    public Color getPieceColor(Cell cell) {
        if (getPiece(cell) == ChessPiece.EMPTY) {
            return Color.NOCOLOR;
        }
        return colors[cell.x][cell.y];
    }

    @Override
    public ChessPiece getPiece(Cell cell) {
        return board[cell.x][cell.y];
    }

    @Override
    public boolean isValidMove(Move move) {
        return getValidMoves().contains(move);
    }

    @Override
    public boolean makeMove(Move move) {
        if (isValidMove(move)) {
            if (board[move.from.x][move.from.y] == ChessPiece.KING) {
                if (colors[move.from.x][move.from.y] == Color.WHITE) {
                    whiteKing = move.to;
                } else {
                    blackKing = move.to;
                }
            }
            prevMove = move;
            prevMovePiece = board[move.to.x][move.to.y];
            prevMoveColor = colors[move.to.x][move.to.y];
            board[move.to.x][move.to.y] = board[move.from.x][move.from.y];
            board[move.from.x][move.from.y] = ChessPiece.EMPTY;
            colors[move.to.x][move.to.y] = colors[move.from.x][move.from.y];
            colors[move.from.x][move.from.y] = ((move.from.x + move.from.y) % 2 == 0 ? Color.WHITE : Color.BLACK);
            if (color == Color.WHITE) {
                color = Color.BLACK;
            } else {
                color = Color.WHITE;
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean isCheck() {
        return kingUnderAttack(color);
    }

    @Override
    public boolean isCheckmate() {
        return kingUnderAttack(color) && getValidMoves().isEmpty();
    }

    @Override
    public boolean isStalemate() {
        return !kingUnderAttack(color) && getValidMoves().isEmpty();
    }

    @Override
    public List<Move> getValidMoves() {
        List<Move> moves = new ArrayList<>();

        for (int x = 0; x < BoardSize; x++) {
            for (int y = 0; y < BoardSize; y++) {
                Cell cell = new Cell(x, y);

                moves.addAll(getValidMovesFrom(cell));
            }
        }

        return moves;
    }

    @Override
    public List<Move> getValidMovesFrom(Cell cell) {
        if (getPieceColor(cell) != color) {
            return new ArrayList<>();
        }

        List<Move> moves = new ArrayList<>();

        switch (board[cell.x][cell.y]) {
            case PAWN -> moves = getPawnValidMovesPawn(cell);
            case BISHOP -> moves = getBishopValidMoves(cell);
            case KING -> moves = getKingValidMoves(cell);
            case KNIGHT -> moves = getKnightValidMoves(cell);
            case QUEEN -> moves = getQueenValidMoves(cell);
            case ROOK -> moves = getRookValidMoves(cell);
        };

        Color kingColor = color;

        if (kingUnderAttack(color)) {
            List<Move> checkedMoves = new ArrayList<>();

            for (Move move: moves) {
                makeMove(move);
                if (!kingUnderAttack(color)) {
                    checkedMoves.add(move);
                }
                undoMove();
            }

            moves = checkedMoves;
        }

        return moves;
    }

    @Override
    public Color getCurrentPlayerColor() {
        return color;
    }

    private List<Move> getRookValidMoves(Cell cell) {
        int x = cell.x, y = cell.y;

        List<Move> moves = new ArrayList<>();

        for (int sign1 = -1; sign1 < 2; sign1++) {
            for (int sign2 = -1; sign2 < 2; sign2++) {
                if (sign1 == 0 ^ sign2 == 0) {
                    for (int i = 0; i < BoardSize; i++) {
                        Cell to = new Cell(x + sign1 * i, y + sign2 * i);

                        if (!checkValidness(to, false)) {
                            if (checkValidness(to, true)) {
                                moves.add(new Move(cell, to));
                            }
                            break;
                        }

                        moves.add(new Move(cell, to));
                    }
                }
            }
        }

        return moves;
    }

    private List<Move> getQueenValidMoves(Cell cell) {

        List<Move> moves = new ArrayList<>();

        moves.addAll(getBishopValidMoves(cell));

        moves.addAll(getRookValidMoves(cell));

        return moves;
    }

    private List<Move> getKnightValidMoves(Cell cell) {
        int x = cell.x, y = cell.y;

        List<Move> moves = new ArrayList<>();

        Cell to = new Cell(x + 1, y + 2);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x + 2, y + 1);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x + 2, y - 1);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x + 1, y - 2);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x - 1, y - 2);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x - 2, y - 1);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x - 2, y + 1);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x - 1, y + 2);

        if (checkValidness(to, false) || checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        return moves;
    }

    private List<Move> getKingValidMoves(Cell cell) {
        int x = cell.x, y = cell.y;

        List<Move> moves = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                Cell to = new Cell(x + i, y + i);

                if (checkValidness(to, false) || checkValidness(to, true)) {
                    moves.add(new Move(cell, to));
                }
            }
        }

        return moves;
    }

    private List<Move> getBishopValidMoves(Cell cell) {
        int x = cell.x, y = cell.y;

        List<Move> moves = new ArrayList<>();

        for (int sign1 = -1; sign1 < 2; sign1 += 2) {
            for (int sign2 = -1; sign2 < 2; sign2 += 2) {
                for (int i = 0; i < 8; i++) {
                    Cell to = new Cell(x + sign1 * i, y + sign2 * i);

                    if (!checkValidness(to, false)) {
                        if (checkValidness(to, true)) {
                            moves.add(new Move(cell, to));
                        }
                        break;
                    }

                    moves.add(new Move(cell, to));
                }
            }
        }

        return moves;
    }

    private List<Move> getPawnValidMovesPawn(Cell cell) {
        int x = cell.x, y = cell.y;

        List<Move> moves = new ArrayList<>();

        Cell to = new Cell(x, y + (colors[x][y] == Color.WHITE ? 1 : -1));

        if (checkValidness(to, false)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x + 1, y + (colors[x][y] == Color.WHITE ? 1 : -1));

        if (checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        to = new Cell(x - 1, y + (colors[x][y] == Color.WHITE ? 1 : -1));

        if (checkValidness(to, true)) {
            moves.add(new Move(cell, to));
        }

        if (colors[x][y] == Color.WHITE && y == 1) {
            to = new Cell(x, y + 2);
            if (checkValidness(to, false)) {
                moves.add(new Move(cell, to));
            }
        }

        if (colors[x][y] == Color.BLACK && y == 6) {
            to = new Cell(x, y - 2);
            if (checkValidness(to, false)) {
                moves.add(new Move(cell, to));
            }
        }

        return moves;
    }

    private boolean checkValidness(Cell cell, boolean canBeat) {
        return cell.x >= 0 && cell.x < 8 && cell.y >= 0 && cell.y < 8 &&
                ((!canBeat && board[cell.x][cell.y] == ChessPiece.EMPTY) ||
                        (canBeat && board[cell.x][cell.y] != ChessPiece.EMPTY && colors[cell.x][cell.y] != color));
    }

    private void undoMove() {
        if (prevMove == null) {
            return;
        }

        Cell from = prevMove.from, to = prevMove.to;

        if (board[to.x][to.y] == ChessPiece.KING) {
            if (colors[to.x][to.y] == Color.WHITE) {
                whiteKing = from;
            } else {
                blackKing = to;
            }
        }

        board[from.x][from.y] = board[to.x][to.y];
        colors[from.x][from.y] = colors[to.x][to.y];
        board[to.x][to.y] = prevMovePiece;
        colors[to.x][to.y] = prevMoveColor;
        prevMove = null;
    }

    private boolean kingUnderAttack(Color kingColor) {

        Cell kingCell = (kingColor == Color.WHITE ? whiteKing : blackKing);

        List<Move> moves = new ArrayList<>();

        moves = getKnightValidMoves(kingCell);

        for (Move move: moves) {
            if (board[move.to.x][move.to.y] == ChessPiece.KNIGHT && colors[move.to.x][move.to.y] != kingColor) {
                return true;
            }
        }

        moves = getRookValidMoves(kingCell);

        for (Move move: moves) {
            if (colors[move.to.x][move.to.y] != kingColor) {
                if (board[move.to.x][move.to.y] == ChessPiece.ROOK || board[move.to.x][move.to.y] == ChessPiece.QUEEN) {
                    return true;
                }
            }
        }

        moves = getBishopValidMoves(kingCell);

        for (Move move: moves) {
            if (colors[move.to.x][move.to.y] != kingColor) {
                if (board[move.to.x][move.to.y] == ChessPiece.BISHOP || board[move.to.x][move.to.y] == ChessPiece.QUEEN) {
                    return true;
                }
            }
        }

        int x = kingCell.x, y = kingCell.y;

        Cell to = new Cell(x + 1, y + (colors[x][y] == kingColor ? 1 : -1));

        if (checkValidness(to, false) && board[to.x][to.y] == ChessPiece.PAWN && colors[to.x][to.y] != kingColor) {
            return true;
        }

        to = new Cell(x - 1, y + (colors[x][y] == kingColor ? 1 : -1));

        return checkValidness(to, false) && board[to.x][to.y] == ChessPiece.PAWN && colors[to.x][to.y] != kingColor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                hash = (hash + colors[i][j].getValue()) * board[i][j].getValue();
            }
        }
        return hash;
    }
}