package chessgame.web.server.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        indexes = @Index(columnList = "gameId,moveNumber", unique = true)
)
public class Move {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    private long gameId;

    @NotNull
    @NotEmpty
    private long moveNumber;

    @NotNull
    @NotEmpty
    private long xFrom;

    @NotNull
    @NotEmpty
    private long yFrom;

    @NotNull
    @NotEmpty
    private long xTo;

    @NotNull
    @NotEmpty
    private long yTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(long moveNumber) {
        this.moveNumber = moveNumber;
    }

    public long getXFrom() {
        return xFrom;
    }

    public void setXFrom(long xFrom) {
        this.xFrom = xFrom;
    }

    public long getYFrom() {
        return yFrom;
    }

    public void setYFrom(long yFrom) {
        this.yFrom = yFrom;
    }

    public long getXTo() {
        return xTo;
    }

    public void setXTo(long xTo) {
        this.xTo = xTo;
    }

    public long getYTo() {
        return yTo;
    }

    public void setYTo(long yTo) {
        this.yTo = yTo;
    }
}
