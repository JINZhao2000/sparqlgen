package cyou.zhaojin.sparqlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Zhao JIN
 */
@Controller
public class IndexController {
    @GetMapping({"/index", "/index.html"})
    public String indexGet(){
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
