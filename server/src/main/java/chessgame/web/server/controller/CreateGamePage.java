package chessgame.web.server.controller;

import chessgame.web.server.domain.Game;
import chessgame.web.server.form.GameCredentials;
import chessgame.web.server.form.validator.GameCredentialsCreateValidator;
import chessgame.web.server.service.GameService;
import chessgame.web.server.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

public class CreateGamePage extends Page {
    private final GameService gameService;
    private final UserService userService;
    private final GameCredentialsCreateValidator gameCredentialsCreateValidator;

    public CreateGamePage(GameService gameService, UserService userService, GameCredentialsCreateValidator gameCredentialsCreateValidator) {
        this.gameService = gameService;
        this.userService = userService;
        this.gameCredentialsCreateValidator = gameCredentialsCreateValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(gameCredentialsCreateValidator);
    }

    @GetMapping("/create_game")
    public String createGame(Model model, HttpSession httpSession) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("createGame", new GameCredentials());
        return "CreateGamePage";
    }

    @PostMapping("/create_game")
    public String createGame(@Valid @ModelAttribute("gameForm") GameCredentials gameForm,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "CreateGamePage";
        }

        Game game = gameService.create(gameForm, getUser(httpSession).getLogin());
        setGame(httpSession, game);
        putMessage(httpSession, "Congrats, you have created the game!");

        return "GamePage/" + game.getId();
    }
}
