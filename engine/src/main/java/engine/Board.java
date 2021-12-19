package engine;

import java.util.List;

/** интерфейс шахматной доски
 * @author денчик трибратович
 * */
public interface Board {
    /** функция возвращающая цвет клетки переданной в качестве аргумента */
    Color getPieceColor(Cell cell);

    /** функция возвращающая фигуру клетки переданной в качестве аргумента */
    ChessPiece getPiece(Cell cell);

    /** проверка хода на адекватность */
    boolean isValidMove(Move move);

    /** доска пытается сделать ход и возвращает релузьтат своей тщетной попытки */
    boolean makeMove(Move move);

    /** доска пытается отменить ход и возвращает релузьтат своей тщетной попытки */
    boolean undoMove();

    /** возвращает историю всех ходов для данной доски */
    List<Move> getAllMoves();

    /** проверка на шах игрока чей ход */
    boolean isCheck();

    /** проверка на шах и мат(за мат извини) игрока чей ход */
    boolean isCheckmate();

    /** проверка на пат */
    boolean isStalemate();

    /** возвращает список всех допустимых ходов для текущего игрока */
    List<Move> getValidMoves();

    /** возвращает список допустимых ходов для переданной как аргумент клетки */
    List<Move> getValidMovesFrom(Cell cell);

    /** возвращает цвет игрока который должен сейчас делать ход */
    Color getCurrentPlayerColor();
}
