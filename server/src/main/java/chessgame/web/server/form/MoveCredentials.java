package chessgame.web.server.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MoveCredentials {
    @NotNull
    @NotEmpty
    private long gameId;

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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
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
