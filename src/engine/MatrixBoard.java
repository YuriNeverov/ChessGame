package engine;

import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper;

import java.util.ArrayList;
import java.util.List;

class MatrixBoard implements Board {
    private final int BoardSize = 8;

    public Color color = Color.WHITE;

    private ChessPiece[][] board = new ChessPiece[BoardSize][BoardSize];
    private Color[][] colors = new Color[BoardSize][BoardSize];

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

                        }

                        case BISHOP -> {
                            for (int i = 0; i < 8; i++) {
                                Cell to = new Cell(x + i, y + i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x - i, y + i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x + i, y - i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x - i, y - i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
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
                            for (int i = 0; i < 8; i++) {
                                Cell to = new Cell(x + i, y);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x - i, y);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x, y + i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x, y - i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x + i, y + i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x - i, y + i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x + i, y - i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x - i, y - i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }
                            }
                        }
                        case ROOK -> {
                            for (int i = 0; i < 8; i++) {
                                Cell to = new Cell(x + i, y);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x - i, y);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x, y + i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
                                }

                                to = new Cell(x, y - i);

                                if (checkValidness(to, false) || checkValidness(to, true)) {
                                    moves.add(new Move(from, to));
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
            colors[move.to.x][move.to.y] = colors[move.from.x][move.from.y];
            colors[move.from.x][move.from.y] = ((move.from.x + move.from.y) % 2 == 0 ? Color.WHITE : Color.BLACK);
            color = (color == Color.BLACK ? Color.WHITE : Color.BLACK);
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
                        (canBeat && colors[cell.x][cell.y] != color));
    }

}
