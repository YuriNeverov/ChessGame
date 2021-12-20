package chessgame.web.server.form.validator;

import chessgame.web.server.form.UserCredentials;
import chessgame.web.server.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCredentialsRegisterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCredentials.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserCredentials registerForm = (UserCredentials) target;
            if (!userService.isLoginVacant(registerForm.getLogin())) {
                errors.rejectValue("login", "login.is-in-use", "login is in use already");
            }
        }
    }
}