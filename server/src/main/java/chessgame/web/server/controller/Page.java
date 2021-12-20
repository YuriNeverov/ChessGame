package chessgame.web.server.controller;

import chessgame.web.server.domain.Game;
import chessgame.web.server.domain.Move;
import chessgame.web.server.domain.User;
import chessgame.web.server.service.UserService;
import engine.Board;
import engine.Cell;
import engine.ChessPiece;
import engine.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

import static engine.ChessPiece.EMPTY;
import static engine.ChessPiece.KING;

public class Page {
    private static final String USER_ID_SESSION_KEY = "userId";
    private static final String MESSAGE_SESSION_KEY = "message";

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User getUser(HttpSession httpSession) {
        return userService.findById((Long) httpSession.getAttribute(USER_ID_SESSION_KEY));
    }

    void setUser(HttpSession httpSession, User user) {
        if (user != null) {
            httpSession.setAttribute(USER_ID_SESSION_KEY, user.getId());
        } else {
            unsetUser(httpSession);
        }
    }

    void unsetUser(HttpSession httpSession) {
        httpSession.removeAttribute(USER_ID_SESSION_KEY);
    }

    engine.Move moveToEngineMove(Move move) {
        return new engine.Move(
                new Cell(move.getXFrom(), move.getYFrom()),
                new Cell(move.getXTo(), move.getYTo())
        );
    }

    String getFEN(Board board) {
        StringBuilder FEN = new StringBuilder();
        for (int y = 7; y >= 0; y--) {
            int spaceLength = 0;

            for (int x = 0; x < 8; x++) {
                Cell cell = new Cell(x, y);
                ChessPiece chessPiece = board.getPiece(cell);
                if (chessPiece == EMPTY) {
                    spaceLength++;
                } else {
                    if (spaceLength != 0) {
                        FEN.append(spaceLength);
                        spaceLength = 0;
                    }
                    char partFen = switch (chessPiece) {
                        case PAWN -> 'p';
                        case ROOK -> 'r';
                        case KNIGHT -> 'n';
                        case BISHOP -> 'b';
                        case QUEEN -> 'q';
                        case KING -> 'k';
                        default -> throw new IllegalStateException("Unexpected value: " + chessPiece);
                    };
                    if (board.getPieceColor(cell) == Color.WHITE) {
                        partFen = Character.toUpperCase(partFen);
                    }
                    FEN.append(partFen);
                }
            }

            if (spaceLength != 0) {
                FEN.append(spaceLength);
            }

            FEN.append('/');
        }
        FEN.deleteCharAt(FEN.length() - 1);
        FEN.append(" ").append(board.getCurrentPlayerColor() == Color.WHITE ? 'w' : 'b').append(" - - 0 0");
        return FEN.toString();
    }

    void putMessage(HttpSession httpSession, String message) {
        httpSession.setAttribute(MESSAGE_SESSION_KEY, message);
    }
}
