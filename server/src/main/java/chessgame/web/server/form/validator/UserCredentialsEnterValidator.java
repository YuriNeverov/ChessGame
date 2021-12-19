package chessgame.web.server.form.validator;

import chessgame.web.server.domain.User;
import chessgame.web.server.form.UserCredentials;
import chessgame.web.server.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCredentialsEnterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsEnterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return UserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserCredentials enterForm = (UserCredentials) target;
            User user = userService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword());
            if (user == null) {
                errors.rejectValue("password", "password.invalid-login-or-password", "invalid login or password");
            } else if (user.isDisable()) {
                errors.rejectValue("login", "login.user-is-disabled", "user is disabled");
            }
        }
    }
}
