package chessgame.web.server.service;

import chessgame.web.server.domain.Game;
import chessgame.web.server.form.GameCredentials;
import chessgame.web.server.reprository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game create(GameCredentials gameCredentials, String userLogin) {
        Game game = new Game();
        if (gameCredentials.isWhiteColor()) {
            game.setLoginWhite(userLogin);
            game.setLoginBlack(gameCredentials.getOpponentLogin());
        } else {
            game.setLoginWhite(gameCredentials.getOpponentLogin());
            game.setLoginBlack(userLogin);
        }
        gameRepository.save(game);
        return game;
    }

    public Game findById(Long id) {
        return id == null ? null : gameRepository.findById(id).orElse(null);
    }
}
