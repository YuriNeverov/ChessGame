package chessgame.web.server.controller;

import chessgame.web.server.form.UserCredentials;
import chessgame.web.server.form.validator.UserCredentialsRegisterValidator;
import chessgame.web.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegisterPage extends Page {
    private final UserService userService;
    private final UserCredentialsRegisterValidator userCredentialsRegisterValidator;

    public RegisterPage(UserService userService, UserCredentialsRegisterValidator userCredentialsRegisterValidator) {
        this.userService = userService;
        this.userCredentialsRegisterValidator = userCredentialsRegisterValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsRegisterValidator);
    }


    @GetMapping("/register")
    public String registerGet(Model model, HttpSession httpSession) {
        model.addAttribute("registerForm", new UserCredentials());
        return "RegisterPage";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("registerForm") UserCredentials registerForm,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "RegisterPage";
        }

        setUser(httpSession, userService.register(registerForm));
        putMessage(httpSession, "Congrats, you have been registered!");

        return "redirect:/";
    }
}
