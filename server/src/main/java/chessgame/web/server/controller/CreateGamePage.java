package chessgame.web.server.controller;

import chessgame.web.server.domain.Game;
import chessgame.web.server.form.GameCredentials;
import chessgame.web.server.form.UserCredentials;
import chessgame.web.server.service.GameService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

public class CreateGamePage extends Page {
    private final GameService gameService;

    public CreateGamePage(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/create_game")
    public String createGame(Model model, HttpSession httpSession) {
        model.addAttribute("createGame", new GameCredentials());
        return "CreateGamePage";
    }

    @PostMapping("/create_game")
    public String createGame(@ModelAttribute("gameForm") GameCredentials gameFrom,
                             HttpSession httpSession) {

        Game game = gameService.create(gameFrom, getUser(httpSession).getLogin());
        setGame(httpSession, game);
        putMessage(httpSession, "Congrats, you have created the game!");

        return "GamePage/" + game.getId();
    }
}
