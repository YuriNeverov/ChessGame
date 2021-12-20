package chessgame.web.server.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GameCredentials {
    @NotNull
    @NotEmpty
    private String opponentLogin;

    private int isWhiteColor;

    public String getOpponentLogin() {
        return opponentLogin;
    }

    public void setOpponentLogin(String opponentLogin) {
        this.opponentLogin = opponentLogin;
    }

    public boolean isWhiteColor() {
        return isWhiteColor == 0;
    }

    public void setWhiteColor(boolean whiteColor) {
        this.isWhiteColor = whiteColor ? 1 : 0;
    }
}
