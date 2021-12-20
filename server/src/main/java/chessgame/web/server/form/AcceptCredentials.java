package chessgame.web.server.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AcceptCredentials {
    @NotNull
    @NotEmpty
    private long idGame;

    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
    }
}
