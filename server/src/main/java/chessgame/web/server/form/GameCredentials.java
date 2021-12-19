package chessgame.web.server.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GameCredentials {
    @NotNull
    @NotEmpty
    private String opponentLogin;

    private boolean isWhiteColor;

    public String getOpponentLogin() {
        return opponentLogin;
    }

    public void setOpponentLogin(String opponentLogin) {
        this.opponentLogin = opponentLogin;
    }

    public boolean isWhiteColor() {
        return isWhiteColor;
    }

    public void setWhiteColor(boolean whiteColor) {
        this.isWhiteColor = whiteColor;
    }
}
