package chessgame.web.server.reprository;

import chessgame.web.server.domain.Game;
import chessgame.web.server.domain.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query(value = "SELECT * FROM game WHERE login_white=?1 or login_black=?1 ORDER BY creation_time", nativeQuery = true)
    List<Game> findAllByUserLogin(String login);

    @Transactional
    @Modifying
    @Query(value = "UPDATE game SET accepted=true WHERE id=?1", nativeQuery = true)
    void acceptGame(long gameId);
}
