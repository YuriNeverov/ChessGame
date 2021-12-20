package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {
    TestingBoard board;

    @BeforeEach
    void init() {
        board = new TestingMatrixBoard();
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

    boolean isOnBoard(Cell cell) {
        return 0 <= cell.x && cell.x < 8 && 0 <= cell.y && cell.y < 8;
    }

    boolean isOnBoard(int x, int y) {
        return 0 <= x && x < 8 && 0 <= y && y < 8;
    }

    void assertMove(ChessPiece pieceFrom, Cell cellFrom, Color colorFrom,
                    ChessPiece pieceTo, Cell cellTo, Color colorTo,
                    boolean shouldHappen
    ) {
        String message = String.format(
            """
            State:
            %s
            piece %s from (%d, %d) color %s to
            piece %s in (%d, %d) color %s,
            should %s have been moved""",
            board.getState(),
            pieceFrom, cellFrom.x, cellFrom.y, colorFrom,
            pieceTo, cellTo.x, cellTo.y, colorFrom,
            shouldHappen ? "" : "not"
        );
        if (shouldHappen) {
            assertEquals(ChessPiece.EMPTY, board.getPiece(cellFrom), message);
            if (pieceFrom == ChessPiece.PAWN && (cellTo.y == 0 || cellTo.y == 7)) {
                assertEquals(ChessPiece.QUEEN, board.getPiece(cellTo), message);
            } else {
                assertEquals(pieceFrom, board.getPiece(cellTo), message);
            }
            assertEquals(Color.NOCOLOR, board.getPieceColor(cellFrom), message);
            assertEquals(colorFrom, board.getPieceColor(cellTo), message);
        } else {
            assertEquals(pieceFrom, board.getPiece(cellFrom), message);
            assertEquals(colorFrom, board.getPieceColor(cellFrom), message);

            if (isOnBoard(cellTo)) {
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
        String format = """
            State:
            %s
            Piece %s colored %s tries to move from (%d, %d) to (%d, %d),
            into free cell should %s move,
            into enemy cell should %s.""";

        board.clearPrevMove();
        board.setCurrentPlayer(color);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(from, piece, color);
        String message = String.format(
            format,
            board.getState(),
            piece, color, from.x, from.y, to.x, to.y,
            free ? "" : "not",
            enemy ? "" : "not"
        );
        assertEquals(free, board.isValidMove(new Move(from, to)), message);
        assertEquals(free, board.makeMove(new Move(from, to)), message);
        assertMove(piece, from, color, ChessPiece.EMPTY, to, Color.NOCOLOR, free);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(from, ChessPiece.EMPTY, Color.NOCOLOR);

        board.clearPrevMove();
        board.setCurrentPlayer(color);
        board.setCell(from, piece, color);
        board.setCell(to, otherPiece, color);
        message = String.format(
            format,
            board.getState(),
            piece, color, from.x, from.y, to.x, to.y,
            free ? "" : "not",
            enemy ? "" : "not"
        );
        assertFalse(board.isValidMove(new Move(from, to)), message);
        assertFalse(board.makeMove(new Move(from, to)), message);
        assertMove(piece, from, color, otherPiece, to, color, false);
        board.setCell(to, ChessPiece.EMPTY, Color.NOCOLOR);
        board.setCell(from, ChessPiece.EMPTY, Color.NOCOLOR);

        board.clearPrevMove();
        board.setCurrentPlayer(color);
        board.setCell(from, piece, color);
        board.setCell(to, otherPiece, otherColor);
        message = String.format(
            format,
            board.getState(),
            piece, color, from.x, from.y, to.x, to.y,
            free ? "" : "not",
            enemy ? "" : "not"
        );
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
        board.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                for (int x = -1; x < 9; x++) {
                    for (int y = -1; y < 9; y++) {
                        if (x == i && y == j) continue;
                        for (Color color : List.of(Color.WHITE, Color.BLACK)) {
                            int direction = color == Color.WHITE ? 1 : -1;
                            boolean free = false;
                            boolean enemy = false;
                            if (
                                isOnBoard(x, y) &&
                                (j == (direction + 7) % 7 && y == j + 2 * direction || y == j + direction) &&
                                x == i
                            ) {
                                free = true;
                            }
                            if (
                                isOnBoard(x, y) &&
                                y == j + direction &&
                                (x == i - 1 || x == i + 1)
                            ) {
                                enemy = true;
                            }
                            testMove(ChessPiece.PAWN, color, new Cell(i, j), new Cell(x, y), free, enemy);
                        }
                    }
                }
            }
        }
    }

    @Test
    void rookTest() {
        board.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int x = -1; x < 9; x++) {
                    for (int y = -1; y < 9; y++) {
                        if (x == i && y == j) continue;
                        for (Color color : List.of(Color.WHITE, Color.BLACK)) {
                            boolean free = false;
                            boolean enemy = false;
                            if (isOnBoard(x, y) && (x == i || y == j)) {
                                free = true;
                                enemy = true;
                            }
                            testMove(ChessPiece.ROOK, color, new Cell(i, j), new Cell(x, y), free, enemy);
                        }
                    }
                }
            }
        }
    }

    @Test
    void knightTest() {
        board.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int x = -1; x < 9; x++) {
                    for (int y = -1; y < 9; y++) {
                        if (x == i && y == j) continue;
                        for (Color color : List.of(Color.WHITE, Color.BLACK)) {
                            boolean free = false;
                            boolean enemy = false;
                            int deltaY = abs(y - j), deltaX = abs(x - i);
                            if (isOnBoard(x, y) && (deltaX == 1 || deltaY == 1) && deltaX + deltaY == 3) {
                                free = true;
                                enemy = true;
                            }
                            testMove(ChessPiece.KNIGHT, color, new Cell(i, j), new Cell(x, y), free, enemy);
                        }
                    }
                }
            }
        }
    }

    @Test
    void bishopTest() {
        board.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int x = -1; x < 9; x++) {
                    for (int y = -1; y < 9; y++) {
                        if (x == i && y == j) continue;
                        for (Color color : List.of(Color.WHITE, Color.BLACK)) {
                            boolean free = false;
                            boolean enemy = false;
                            if (isOnBoard(x, y) && (x - i == y - j || x - i == j - y)) {
                                free = true;
                                enemy = true;
                            }
                            testMove(ChessPiece.BISHOP, color, new Cell(i, j), new Cell(x, y), free, enemy);
                        }
                    }
                }
            }
        }
    }

    @Test
    void queenTest() {
        board.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int x = -1; x < 9; x++) {
                    for (int y = -1; y < 9; y++) {
                        if (x == i && y == j) continue;
                        for (Color color : List.of(Color.WHITE, Color.BLACK)) {
                            boolean free = false;
                            boolean enemy = false;
                            if (isOnBoard(x, y) && (x - i == y - j || x - i == j - y || x == i || y == j)) {
                                free = true;
                                enemy = true;
                            }
                            testMove(ChessPiece.QUEEN, color, new Cell(i, j), new Cell(x, y), free, enemy);
                        }
                    }
                }
            }
        }
    }

    @Test
    void movesHistoryTest() {
        List<Move> moves = List.of(
            new Move(new Cell(4, 1), new Cell(4, 2)),
            new Move(new Cell(3, 6), new Cell(3, 4)),
            new Move(new Cell(3, 0), new Cell(6, 3)),
            new Move(new Cell(2, 7), new Cell(6, 3))
        );
        for (Move move : moves) {
            assertTrue(board.makeMove(move));
        }
        assertEquals(moves, board.getMovesHistory());
    }

    @Test
    void undoMoveTest() {
        assertFalse(board.undoMove());
        List<Move> moves = List.of(
            new Move(new Cell(4, 1), new Cell(4, 2)),
            new Move(new Cell(3, 6), new Cell(3, 4)),
            new Move(new Cell(3, 0), new Cell(6, 3)),
            new Move(new Cell(2, 7), new Cell(6, 3))
        );
        List<Move> moves_undone = List.of(
            new Move(new Cell(4, 1), new Cell(4, 2)),
            new Move(new Cell(3, 6), new Cell(3, 4))
        );
        for (Move move : moves) {
            assertTrue(board.makeMove(move));
        }
        assertEquals(moves, board.getMovesHistory());
        assertTrue(board.undoMove());
        assertTrue(board.undoMove());
        assertEquals(moves_undone, board.getMovesHistory());
    }

    @Test
    void playerColorTest() {
        List<Move> moves = List.of(
            new Move(new Cell(4, 1), new Cell(4, 2)),
            new Move(new Cell(3, 6), new Cell(3, 4)),
            new Move(new Cell(3, 0), new Cell(6, 3)),
            new Move(new Cell(2, 7), new Cell(6, 3))
        );
        Color current = Color.WHITE;
        assertEquals(Color.WHITE, board.getCurrentPlayerColor());
        for (Move move : moves) {
            assertTrue(board.makeMove(move));
            current = current == Color.WHITE ? Color.BLACK : Color.WHITE;
            assertEquals(current, board.getCurrentPlayerColor());
        }
    }

    @Test
    void isCheckTest() {
        assertFalse(board.isCheck());
        board.clear();
        board.setWhiteKing(new Cell(0, 0));
        board.setBlackKing(new Cell(7, 7));
        board.setCell(board.getWhiteKing(), ChessPiece.KING, Color.WHITE);
        board.setCell(board.getBlackKing(), ChessPiece.KING, Color.BLACK);
        board.setCell(new Cell(2, 2), ChessPiece.BISHOP, Color.BLACK);
        board.setCurrentPlayer(Color.WHITE);
        assertTrue(board.isCheck(), String.format("State: \n%s", board.getState()));
    }

    @Test
    void isStalemateTest() {
        assertFalse(board.isStalemate());
        board.clear();
        board.setWhiteKing(new Cell(0, 0));
        board.setBlackKing(new Cell(7, 7));
        board.setCell(board.getWhiteKing(), ChessPiece.KING, Color.WHITE);
        board.setCell(board.getBlackKing(), ChessPiece.KING, Color.BLACK);
        board.setCell(new Cell(1, 2), ChessPiece.ROOK, Color.BLACK);
        board.setCell(new Cell(2, 1), ChessPiece.ROOK, Color.BLACK);
        board.setCurrentPlayer(Color.WHITE);
        assertTrue(board.isStalemate(), String.format("State: \n%s", board.getState()));
    }

    @Test
    void isCheckmateTest() {
        assertFalse(board.isCheckmate());
        board.clear();
        board.setWhiteKing(new Cell(0, 0));
        board.setBlackKing(new Cell(7, 7));
        board.setCell(board.getWhiteKing(), ChessPiece.KING, Color.WHITE);
        board.setCell(board.getBlackKing(), ChessPiece.KING, Color.BLACK);
        board.setCell(new Cell(1, 2), ChessPiece.ROOK, Color.BLACK);
        board.setCell(new Cell(2, 1), ChessPiece.ROOK, Color.BLACK);
        board.setCell(new Cell(3, 3), ChessPiece.BISHOP, Color.BLACK);
        board.setCurrentPlayer(Color.WHITE);
        assertTrue(board.isCheckmate(), String.format("State: \n%s", board.getState()));
    }
}
