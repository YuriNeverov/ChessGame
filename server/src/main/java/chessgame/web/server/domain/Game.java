package chessgame.web.server.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    private String loginWhite;

    @NotNull
    @NotEmpty
    private String loginBlack;

    @CreationTimestamp
    private Date creationTime;

    private boolean gameIsOver;

    private boolean accepted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginWhite() {
        return loginWhite;
    }

    public void setLoginWhite(String loginWhite) {
        this.loginWhite = loginWhite;
    }

    public String getLoginBlack() {
        return loginBlack;
    }

    public void setLoginBlack(String loginBlack) {
        this.loginBlack = loginBlack;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
