package chessgame.web.server.controller;

import chessgame.web.server.domain.Game;
import chessgame.web.server.form.AcceptCredentials;
import chessgame.web.server.form.GameCredentials;
import chessgame.web.server.form.validator.GameCredentialsCreateValidator;
import chessgame.web.server.service.GameService;
import chessgame.web.server.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class AllGames extends Page {
    private final GameService gameService;

    public AllGames(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all_games")
    public String createGame(Model model, HttpSession httpSession) {
        String userLogin = getUser(httpSession).getLogin();
        List<Game> games = gameService.findAllByUserLogin(userLogin);

        List<Game> currentGames = new ArrayList<>();
        List<Game> myInvitation = new ArrayList<>();
        List<Game> sendInvitation = new ArrayList<>();

        for (Game game : games) {
            if (game.isAccepted()) {
                currentGames.add(game);
            } else if ((game.isCreatorIsWhile() && game.getLoginWhite().equals(userLogin))
                    || (!game.isCreatorIsWhile() && game.getLoginBlack().equals(userLogin))) {
                sendInvitation.add(game);
            } else {
                myInvitation.add(game);
            }
        }

        model.addAttribute("currentGames", currentGames);
        model.addAttribute("myInvitation", myInvitation);
        model.addAttribute("sendInvitation", sendInvitation);

        return "AllGames";
    }

    @PostMapping("/all_games")
    public String createGame(@ModelAttribute("acceptForm") AcceptCredentials acceptForm,
                             HttpSession httpSession) {

        gameService.accept(acceptForm);
        putMessage(httpSession, "Congrats, you have accepted the game!");

        return "AllGames";
    }
}
