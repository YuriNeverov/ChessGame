package ai;

import engine.*;


import java.util.List;

public class SmartPlayer implements Player {
    static int positionsCounted = 0;
    long startTime;
    double l_timeout;
    int maxDepth = 5;
    Color playerColor = Color.WHITE;

    @Override
    public Move getMove(Board board, double timeout) {
        playerColor = board.getCurrentPlayerColor();

        startTime = System.currentTimeMillis();

        l_timeout = timeout;

        return moveRecursiveSearchRoot(board);
    }

    @Override
    public Move getMove(Board board) {
        return getMove(board, 1.0);
    }

    private Move moveRecursiveSearchRoot(Board board) {
        int bestScore = -9999;
        Move bestMove = null;

        List<Move> moves = board.getAllValidMoves();

        if (moves.isEmpty()) {
            return null;
        }

        for (Move move: moves) {
            board.makeMove(move);
            int newScore = moveRecursiveSearch(board, 1);

            if (newScore > bestScore) {
                bestScore = newScore;
                bestMove = move;
            }

            board.undoMove();
        }

        return bestMove;
    }

    private int moveRecursiveSearch(Board board, int depth) {
        positionsCounted++;

        if (depth >= maxDepth || System.currentTimeMillis() - startTime > l_timeout * 1000.0) {
            return getPoints(board);
        }

        int bestScore = (depth % 2 == 0 ? -9999 : 9999);

        List<Move> moves = board.getAllValidMoves();

        if (moves.isEmpty()) {
            if (board.isStalemate()) {
                return 0;
            }
            if (board.isCheckmate()) {
                return bestScore;
            }
        }

        for (Move move: moves) {
            board.makeMove(move);
            int newScore = moveRecursiveSearch(board, depth + 1);
            if (depth % 2 == 0) {
                bestScore = Math.max(bestScore, newScore);
            } else {
                bestScore = Math.min(bestScore, newScore);
            }
            board.undoMove();
        }

        return bestScore;
    }

    private int getPoints(Board board) {
        int score = 0;

        if (board.isCheckmate()) {
            score += 10000;
        }

        Color curColor = board.getCurrentPlayerColor();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int figureScore = 0;
                switch (board.getPiece(new Cell(x, y))) {
                    case EMPTY -> figureScore = 0;
                    case PAWN -> figureScore = 10;
                    case BISHOP, KNIGHT -> figureScore = 30;
                    case ROOK -> figureScore = 50;
                    case QUEEN -> figureScore = 90;
                    case KING -> figureScore = 900;
                }
                if (playerColor == board.getPieceColor(new Cell(x, y))) {
                    score += figureScore;
                } else {
                    score -= figureScore;
                }
            }
        }

        return score;
    }

    public static void main(String[] args) {
        Board board = new MatrixBoard();
        Player player = new SmartPlayer();
        Move move;

        move = player.getMove(board, 1.0);
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(2, 6), new Cell(2, 5));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 0), new Cell(0, 1));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(3, 7), new Cell(0, 4));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 2), new Cell(0, 3));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 4), new Cell(3, 4));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 1), new Cell(0, 2));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(3, 4), new Cell(1, 2));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 2), new Cell(1, 2));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(6, 7), new Cell(7, 5));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(1, 2), new Cell(1, 6));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 6), new Cell(0, 4));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(1, 6), new Cell(1, 7));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(0, 7), new Cell(0, 6));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

        move = new Move(new Cell(1, 7), new Cell(2, 7));
        System.out.println(move);
        board.makeMove(move);
        System.out.println(board);
        System.out.println();

//        int cnt = 0;
//        for (int j = 0; j < 10000; j++) {
//            Board board = new MatrixBoard();
//            Player player = new SmartPlayer();
//            for (int i = 0; i < 30; i++) {
//                if (i % 2 == 0) {
//                    player = new SmartPlayer();
//                } else {
//                    player = new RandomPlayer();
//                }
//
//                Move move = player.getMove(board, 0.1);
//
//                if (move == null) {
//                    boolean check = board.isCheck();
//                    move = player.getMove(board, 0.1);
//                    if (i < 30) {
//                        System.out.println("Game is over on " + String.valueOf(i));
//                        List<Move> moves = board.getMovesHistory();
//                        Board newBoard = new MatrixBoard();
//
////                        System.out.println(newBoard);
//                        for (Move curMove: moves) {
////                            System.out.println(newBoard.getCurrentPlayerColor() + " making move " + curMove.toString());
////                            newBoard.makeMove(curMove);
////                            System.out.println(newBoard);
////                            System.out.println();
//                            int x = curMove.getFrom().getX();
//                            int y = curMove.getFrom().getY();
//                            System.out.print("move = new Move(new Cell(" + x + ", " + y);
//                            x = curMove.getTo().getX();
//                            y = curMove.getTo().getY();
//                            System.out.println("), new Cell(" + x + ", " + y + "));");
//                            System.out.println("System.out.println(move);");
//                            System.out.println("board.makeMove(move);");
//                            System.out.println("System.out.println(board);");
//                            System.out.println("System.out.println();");
//                            System.out.println();
//                        }
//                        System.out.println(board.getCurrentPlayerColor().toString());
//                        System.out.println(board.getAllValidMoves());
//                        System.out.println(board);
//                        return;
//                    }
//                    cnt += i + 1;
//                    break;
//                }
////                System.out.println(move);
//                board.makeMove(move);
//            }
////            System.out.println(board.toString());
//        }
//        System.out.println(cnt);
    }
}
