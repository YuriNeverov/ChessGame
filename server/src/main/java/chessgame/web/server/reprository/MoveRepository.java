package chessgame.web.server.reprository;

import chessgame.web.server.domain.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Long> {

    @Query(value = "SELECT * FROM move WHERE game_id=?1", nativeQuery = true)
    List<Move> findAllMovesByGameId(long gameId);
}
