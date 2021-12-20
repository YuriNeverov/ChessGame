package chessgame.web.server.controller;

import chessgame.web.server.domain.Game;
import chessgame.web.server.domain.Move;
import chessgame.web.server.form.MoveCredentials;
import chessgame.web.server.service.GameService;
import chessgame.web.server.service.MoveService;
import engine.Board;
import engine.Cell;
import engine.MatrixBoard;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.servlet.http.HttpSession;
import java.util.List;

public class GamePage extends Page {
    private final GameService gameService;
    private final MoveService moveService;

    public GamePage(GameService gameService, MoveService moveService) {
        this.gameService = gameService;
        this.moveService = moveService;
    }

    @GetMapping("game/{id}")
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
        model.addAttribute("game", game);

        return "game/" + id;
    }

    @MessageMapping("/sendMove")
    @SendTo("/topic/public")
    public String sendMessage(@Payload MoveCredentials moveCredentials, HttpSession httpSession) {
        Game game;
        try {
            game = gameService.findById(moveCredentials.getGameId());
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
        long moveNumber = moves.size();
        Move nextMove = moveService.ConvertToMove(moveCredentials, moveNumber);

        if (board.makeMove(moveToEngineMove(nextMove))) {
            moveService.makeMove(nextMove);
        } else {
            putMessage(httpSession, "This move is wrong");
        }

        return getFEN(board);
    }
}
