package chessgame.web.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPage extends Page {
    @GetMapping({"", "/"})
    public String index() {
        return "IndexPage";
    }


}
