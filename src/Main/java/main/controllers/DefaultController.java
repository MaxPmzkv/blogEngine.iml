package main.controllers;

import lombok.AllArgsConstructor;
import main.api.response.InitResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@AllArgsConstructor
@Controller
public class DefaultController {
    private final InitResponse initResponse;

    @RequestMapping("/")
    public String index (Model model)
    { //model.addAttribute("home", "main page");
        return "index";
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "/**/{path:[^\\\\.]*}")
    public String redirectToIndex(){
        return "forward:/";
    }


}
