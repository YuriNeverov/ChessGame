package chessgame.web.server.service;

import chessgame.web.server.domain.Move;
import chessgame.web.server.reprository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveService {
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public List<Move> findAllMovesByGameId(long gameId) {
        return moveRepository.findAllMovesByGameId(gameId);
    }
}
