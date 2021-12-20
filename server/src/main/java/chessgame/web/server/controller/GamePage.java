package chessgame.web.server.controller;

import chessgame.web.server.domain.Game;
import chessgame.web.server.domain.Move;
import chessgame.web.server.service.GameService;
import chessgame.web.server.service.MoveService;
import engine.Board;
import engine.Cell;
import engine.MatrixBoard;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

public class GamePage extends Page {
    private final GameService gameService;
    private final MoveService moveService;

    public GamePage(GameService gameService, MoveService moveService) {
        this.gameService = gameService;
        this.moveService = moveService;
    }

    @GetMapping("GamePage/{id}")
    public String postPageGet(@PathVariable String id, Model model, HttpSession httpSession) {
        Game game;
        try {
            game = gameService.findById(Long.parseLong(id));
        }
        catch (NumberFormatException e) {
            putMessage(httpSession, "This game does not exist");
            return "redirect:/";
        }

        List<Move> moves = moveService.findAllMovesByGameId(game.getId());
        Board board = new MatrixBoard();
        for (Move move : moves) {
            board.makeMove(moveToEngineMove(move));
        }

        model.addAttribute("isFlip", game.getLoginBlack().equals(getUser(httpSession).getLogin()));
        model.addAttribute("FEN", getFEN(board));
        model.addAttribute("currentPlayerColor", board.getCurrentPlayerColor());

        return "GamePage/" + id;
    }
}
