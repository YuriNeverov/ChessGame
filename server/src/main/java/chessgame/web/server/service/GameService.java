package chessgame.web.server.service;

import chessgame.web.server.domain.Game;
import chessgame.web.server.domain.User;
import chessgame.web.server.form.AcceptCredentials;
import chessgame.web.server.form.GameCredentials;
import chessgame.web.server.reprository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game create(GameCredentials gameCredentials, String userLogin) {
        Game game = new Game();
        game.setCreatorIsWhile(gameCredentials.isWhiteColor());
        if (gameCredentials.isWhiteColor()) {
            game.setLoginWhite(userLogin);
            game.setLoginBlack(gameCredentials.getOpponentLogin());
        } else {
            game.setLoginWhite(gameCredentials.getOpponentLogin());
            game.setLoginBlack(userLogin);
        }
        if (gameCredentials.isGameWithAI()) {
            game.setGameWithAI(true);
            game.setAccepted(true);
        }
        gameRepository.save(game);
        return game;
    }

    public void accept(AcceptCredentials acceptForm) {
        gameRepository.acceptGame(acceptForm.getIdGame());
    }

    public Game findById(Long id) {
        return id == null ? null : gameRepository.findById(id).orElse(null);
    }

    public List<Game> findAllByUserLogin(String login) {
        return gameRepository.findAllByUserLogin(login);
    }

}
