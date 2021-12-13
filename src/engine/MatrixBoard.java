package engine;

import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper;

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
    public List<Move> getValidMoves() {
        List<Move> moves = new ArrayList<>();

        for (char x = 0; x < BoardSize; x++) {
            for (int y = 0; y < BoardSize; y++) {
                if (board[x][y] != ChessPiece.EMPTY && colors[x][y] == color) {
                    Cell from = new Cell(x, y);

                    switch (board[x][y]) {
                        case PAWN -> {
                            Cell to = new Cell(x, y + (colors[x][y] == Color.WHITE ? 1 : -1));

                            if (checkValidness(to, false)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x + 1, y + (colors[x][y] == Color.WHITE ? 1 : -1));

                            if (checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x - 1, y + (colors[x][y] == Color.WHITE ? 1 : -1));

                            if (checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            if (colors[x][y] == Color.WHITE && y == 1) {
                                to = new Cell(x, y + 2);
                                if (checkValidness(to, false)) {
                                    moves.add(new Move(from, to));
                                }
                            }

                            if (colors[x][y] == Color.BLACK && y == 6) {
                                to = new Cell(x, y - 2);
                                if (checkValidness(to, false)) {
                                    moves.add(new Move(from, to));
                                }
                            }
                        }

                        case BISHOP -> {
                            for (int sign1 = -1; sign1 < 2; sign1 += 2) {
                                for (int sign2 = -1; sign2 < 2; sign2 += 2) {
                                    for (int i = 0; i < 8; i++) {
                                        Cell to = new Cell(x + sign1 * i, y + sign2 * i);

                                        if (!checkValidness(to, false)) {
                                            if (checkValidness(to, true)) {
                                                moves.add(new Move(from, to));
                                            }
                                            break;
                                        }

                                        moves.add(new Move(from, to));
                                    }
                                }
                            }
                        }

                        case KING -> {
                            for (int i = -1; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (i == 0 && j == 0) {
                                        continue;
                                    }

                                    Cell to = new Cell(x + i, y + i);

                                    if (checkValidness(to, false) || checkValidness(to, true)) {
                                        moves.add(new Move(from, to));
                                    }
                                }
                            }
                        }

                        case KNIGHT -> {
                            Cell to = new Cell(x + 1, y + 2);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x + 2, y + 1);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x + 2, y - 1);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x + 1, y - 2);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x - 1, y - 2);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x - 2, y - 1);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x - 2, y + 1);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }

                            to = new Cell(x - 1, y + 2);

                            if (checkValidness(to, false) || checkValidness(to, true)) {
                                moves.add(new Move(from, to));
                            }
                        }
                        case QUEEN -> {
                            for (int sign1 = -1; sign1 < 2; sign1 += 2) {
                                for (int sign2 = -1; sign2 < 2; sign2 += 2) {
                                    for (int i = 0; i < 8; i++) {
                                        Cell to = new Cell(x + sign1 * i, y + sign2 * i);

                                        if (!checkValidness(to, false)) {
                                            if (checkValidness(to, true)) {
                                                moves.add(new Move(from, to));
                                            }
                                            break;
                                        }

                                        moves.add(new Move(from, to));
                                    }
                                }
                            }
                            for (int sign1 = -1; sign1 < 2; sign1++) {
                                for (int sign2 = -1; sign2 < 2; sign2++) {
                                    if (sign1 == 0 ^ sign2 == 0) {
                                        for (int i = 0; i < 8; i++) {
                                            Cell to = new Cell(x + sign1 * i, y + sign2 * i);

                                            if (!checkValidness(to, false)) {
                                                if (checkValidness(to, true)) {
                                                    moves.add(new Move(from, to));
                                                }
                                                break;
                                            }

                                            moves.add(new Move(from, to));
                                        }
                                    }
                                }
                            }
                        }
                        case ROOK -> {
                            for (int sign1 = -1; sign1 < 2; sign1++) {
                                for (int sign2 = -1; sign2 < 2; sign2++) {
                                    if (sign1 == 0 ^ sign2 == 0) {
                                        for (int i = 0; i < 8; i++) {
                                            Cell to = new Cell(x + sign1 * i, y + sign2 * i);

                                            if (!checkValidness(to, false)) {
                                                if (checkValidness(to, true)) {
                                                    moves.add(new Move(from, to));
                                                }
                                                break;
                                            }

                                            moves.add(new Move(from, to));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public boolean doTurn(Move move) {
        if (checkTurn(move)) {
            board[move.to.x][move.to.y] = board[move.from.x][move.from.y];
            board[move.from.x][move.from.y] =  ChessPiece.EMPTY;
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

    private boolean checkTurn(Move move) {
        return true;
    }

    private boolean checkValidness(Cell cell, boolean canBeat) {
        return cell.x >= 0 && cell.x < 8 && cell.y >= 0 && cell.y < 8 &&
                ((!canBeat && board[cell.x][cell.y] == ChessPiece.EMPTY) ||
                        (canBeat && board[cell.x][cell.y] != ChessPiece.EMPTY && colors[cell.x][cell.y] != color));
    }

}
