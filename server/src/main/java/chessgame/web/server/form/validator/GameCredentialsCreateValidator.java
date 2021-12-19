package chessgame.web.server.form.validator;

import chessgame.web.server.form.GameCredentials;
import chessgame.web.server.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GameCredentialsCreateValidator implements Validator {
    private final UserService userService;

    public GameCredentialsCreateValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return GameCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            GameCredentials gameForm = (GameCredentials) target;
            if (!userService.isLoginVacant(gameForm.getOpponentLogin())) {
                errors.rejectValue("opponentLogin", "opponentLogin.user-is-not-exist", "user is not exist");
            }
        }
    }
}
