package chessgame.web.server.service;

import chessgame.web.server.domain.Move;
import chessgame.web.server.domain.User;
import chessgame.web.server.form.MoveCredentials;
import chessgame.web.server.form.UserCredentials;
import chessgame.web.server.reprository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveService {
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public Move ConvertToMove(MoveCredentials moveCredentials, long moveNumber) {
        Move move = new Move();
        move.setMoveNumber(moveNumber);
        move.setGameId(moveCredentials.getGameId());
        move.setXFrom(moveCredentials.getXFrom());
        move.setYFrom(moveCredentials.getYFrom());
        move.setXTo(moveCredentials.getXTo());
        move.setYTo(moveCredentials.getYTo());
        return move;
    }

    public void makeMove(Move move) {
        moveRepository.save(move);
    }

    public List<Move> findAllMovesByGameId(long gameId) {
        return moveRepository.findAllMovesByGameId(gameId);
    }
}
