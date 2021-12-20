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
    private int xFrom;

    @NotNull
    @NotEmpty
    private int yFrom;

    @NotNull
    @NotEmpty
    private int xTo;

    @NotNull
    @NotEmpty
    private int yTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getXFrom() {
        return xFrom;
    }

    public void setXFrom(int xFrom) {
        this.xFrom = xFrom;
    }

    public int getYFrom() {
        return yFrom;
    }

    public void setYFrom(int yFrom) {
        this.yFrom = yFrom;
    }

    public int getXTo() {
        return xTo;
    }

    public void setXTo(int xTo) {
        this.xTo = xTo;
    }

    public int getYTo() {
        return yTo;
    }

    public void setYTo(int yTo) {
        this.yTo = yTo;
    }
}
