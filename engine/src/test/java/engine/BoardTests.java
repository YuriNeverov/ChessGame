package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoardTests {
    TestingBoard board;

    @BeforeEach
    void init() {
        board = new MatrixBoard();
    }

    @Test
    void colorInitTest() {
        for (int i = 0; i < 8; i++) {
            assertEquals(Color.WHITE, board.getPieceColor(new Cell(i, 0)));
            assertEquals(Color.WHITE, board.getPieceColor(new Cell(i, 1)));
            assertEquals(Color.BLACK, board.getPieceColor(new Cell(i, 6)));
            assertEquals(Color.BLACK, board.getPieceColor(new Cell(i, 7)));
            for (int j = 2; j < 6; j++) {
                assertEquals(Color.NOCOLOR, board.getPieceColor(new Cell(i, j)));
            }
        }
    }

    Set<Cell> getSymmetricCells(Cell cell) {
        int x = cell.x, y = cell.y;
        int antiX = 7 - x, antiY = 7 - y;
        return Set.of(cell, new Cell(x, antiY), new Cell(antiX, y), new Cell(antiX, antiY));
    }

    @Test
    void chessPieceInitTest() {
        for (Cell cell : getSymmetricCells(new Cell(0, 0))) {
            assertEquals(ChessPiece.ROOK, board.getPiece(cell));
        }
        for (Cell cell : getSymmetricCells(new Cell(1, 0))) {
            assertEquals(ChessPiece.KNIGHT, board.getPiece(cell));
        }
        for (Cell cell : getSymmetricCells(new Cell(2, 0))) {
            assertEquals(ChessPiece.BISHOP, board.getPiece(cell));
        }
        for (int i = 0; i < 4; i++) {
            for (Cell cell : getSymmetricCells(new Cell(i, 1))) {
                assertEquals(ChessPiece.PAWN, board.getPiece(cell));
            }
            for (int j = 2; j < 4; j++) {
                for (Cell cell : getSymmetricCells(new Cell(i, j))) {
                    assertEquals(ChessPiece.EMPTY, board.getPiece(cell));
                }
            }
        }
        assertEquals(ChessPiece.QUEEN, board.getPiece(new Cell(3, 0)));
        assertEquals(ChessPiece.QUEEN, board.getPiece(new Cell(3, 7)));
        assertEquals(ChessPiece.KING, board.getPiece(new Cell(4, 0)));
        assertEquals(ChessPiece.KING, board.getPiece(new Cell(4, 7)));
    }

    boolean isInWorld(Cell cell) {
        return 0 <= cell.x && cell.x < 8 && 0 <= cell.y && cell.y < 8;
    }

    void assertMove(ChessPiece pieceFrom, Cell cellFrom, Color colorFrom,
                    ChessPiece pieceTo, Cell cellTo, Color colorTo, boolean shouldHappen
    ) {
        String message = String.format("piece %s from (i: %d, j: %d) color %s to piece %s in (x: %d, y: %d) color %s, should %s have been moved",
                pieceFrom.toString(), cellFrom.x, cellFrom.y, colorFrom == Color.WHITE ? "WHITE" : "BLACK", pieceTo.toString(),
                cellTo.x, cellTo.y, colorFrom == Color.WHITE ? "WHITE" : "BLACK", shouldHappen ? "" : "not");
        if (shouldHappen) {
            assertEquals(ChessPiece.EMPTY, board.getPiece(cellFrom), message);
            assertEquals(pieceFrom, board.getPiece(cellTo), message);

            assertEquals(Color.NOCOLOR, board.getPieceColor(cellFrom), message);
            assertEquals(colorFrom, board.getPieceColor(cellTo), message);
        } else {
            assertEquals(pieceFrom, board.getPiece(cellFrom), message);
            assertEquals(colorFrom, board.getPieceColor(cellFrom), message);

            if (isInWorld(cellTo)) {
                assertEquals(pieceTo, board.getPiece(cellTo), message);
                assertEquals(colorTo, board.getPieceColor(cellTo), message);
            }
        }
    }

    int getSector(Cell cell) {
        return 2 * (cell.y - 3 > 0 ? 1 : 0) + (cell.x - 3 > 0 ? 1 : 0);
    }

    void placeKings(Cell from, Cell to, Color fromColor) {
        HashSet<Integer> sectors = new HashSet<>(Set.of(0, 1, 2, 3));
        sectors.remove(getSector(from));
        sectors.remove(getSector(to));

        List<Integer> safeSectors = sectors.stream().toList();

        int fromSafeSector = safeSectors.get(0);
        Cell fromSafeCell = new Cell(fromSafeSector % 2 * 4, fromSafeSector / 2 * 4);

        int toSafeSector = safeSectors.get(1);
        Cell toSafeCell = new Cell(toSafeSector % 2 * 7, toSafeSector / 2 * 7);
        if (from.x == toSafeCell.x) {
            if (toSafeCell.x == 0) {
                toSafeCell = new Cell(toSafeCell.x + 1, toSafeCell.y);
            } else {
                toSafeCell = new Cell(toSafeCell.x - 1, toSafeCell.y);
            }
        } else if (from.y == toSafeCell.y) {
            if (toSafeCell.y == 0) {
                toSafeCell = new Cell(toSafeCell.x, toSafeCell.y + 1);
            } else {
                toSafeCell = new Cell(toSafeCell.x, toSafeCell.y - 1);
            }
        }

        if (fromColor == Color.WHITE) {
            board.setCell(fromSafeCell, ChessPiece.KING, Color.WHITE);
            board.setWhiteKing(fromSafeCell);
            board.setCell(toSafeCell, ChessPiece.KING, Color.BLACK);
            board.setBlackKing(toSafeCell);
        } else {
            board.setCell(fromSafeCell, ChessPiece.KING, Color.BLACK);
            board.setBlackKing(fromSafeCell);
            board.setCell(toSafeCell, ChessPiece.KING, Color.WHITE);
            board.setWhiteKing(toSafeCell);
        }
    }

    void testMove(ChessPiece piece, Color color, Cell from, Cell to, boolean free, boolean enemy) {
        ChessPiece otherPiece = ChessPiece.PAWN;
        Color otherColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        placeKings(from, to, color);

        board.clearPrevMove();
        board.setCurrentPlayer(color);
        board.setCell(from, piece, color);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        String message = String.format("State: \n%s\nPiece %s colored %s tries to move from (i: %d, j: %d) to (x: %d, y: %d), into free should %s move, into enemy should %s.",
                board.getState(),
                piece, color == Color.WHITE ? "WHITE" : "BLACK", from.x, from.y, to.x, to.y, free ? "" : "not", enemy ? "" : "not");
        assertEquals(free, board.isValidMove(new Move(from, to)), message);
        assertEquals(free, board.makeMove(new Move(from, to)), message);
        assertMove(piece, from, color, ChessPiece.EMPTY, to, Color.NOCOLOR, free);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(from, ChessPiece.EMPTY, Color.NOCOLOR);

        board.clearPrevMove();
        board.setCurrentPlayer(color);
        board.setCell(from, piece, color);
        board.setCell(to, otherPiece, color);
        message = String.format("State: \n%s\nPiece %s colored %s tries to move from (i: %d, j: %d) to (x: %d, y: %d), into free should %s move, into enemy should %s.",
                board.getState(),
                piece, color == Color.WHITE ? "WHITE" : "BLACK", from.x, from.y, to.x, to.y, free ? "" : "not", enemy ? "" : "not");
        assertFalse(board.isValidMove(new Move(from, to)), message);
        assertFalse(board.makeMove(new Move(from, to)), message);
        assertMove(piece, from, color, otherPiece, to, color, false);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(from, ChessPiece.EMPTY, Color.NOCOLOR);

        board.clearPrevMove();
        board.setCurrentPlayer(color);
        board.setCell(from, piece, color);
        message = String.format("State: \n%s\nPiece %s colored %s tries to move from (i: %d, j: %d) to (x: %d, y: %d), into free should %s move, into enemy should %s.",
                board.getState(),
                piece, color == Color.WHITE ? "WHITE" : "BLACK", from.x, from.y, to.x, to.y, free ? "" : "not", enemy ? "" : "not");
        assertEquals(enemy, board.isValidMove(new Move(from, to)), message);
        assertEquals(enemy, board.makeMove(new Move(from, to)), message);
        assertMove(piece, from, color, otherPiece, to, otherColor, enemy);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(from, ChessPiece.EMPTY, Color.NOCOLOR);

        board.setCell(board.getBlackKing(), ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(board.getWhiteKing(), ChessPiece.EMPTY, Color.NOCOLOR);
    }

    @Test
    void pawnTest() {
        board = new MatrixBoard();
        Color color = Color.WHITE;
        int direction = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.setCell(new Cell(i, j), ChessPiece.EMPTY, Color.NOCOLOR);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                for (int x = 0; x < 8; x++) {
                    for (int y = 1; y < 8; y++) {
                        if (x == i && y == j) continue;
                        boolean free = false;
                        boolean enemy = false;
                        if (j == 1 && y == j + 2 || y == j + 1) {
                            free = true;
                        }
                        if (y == i + 1 && (x == i - 1 || x == i + 1)) {
                            enemy = true;
                        }
                        testMove(ChessPiece.PAWN, color, new Cell(i, j), new Cell(x, y), free, enemy);
                    }
                }
            }
        }
    }

}
