package chessgame.web.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexPage extends Page {
    @GetMapping({"", "/"})
    public String index() {
        return "IndexPage";
    }

    @GetMapping(path = "/logout")
    public String index(HttpSession httpSession) {
        unsetUser(httpSession);
        return "redirect:/";
    }
}
