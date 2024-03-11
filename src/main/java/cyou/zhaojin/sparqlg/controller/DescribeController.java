package cyou.zhaojin.sparqlg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Zhao JIN
 */
@Controller
public class DescribeController {
    @RequestMapping("/describe")
    public String describe(Model model){
        model.addAttribute("msg", "");
        return "describe";
    }

    @GetMapping("/describe/generate")
    public String describe(@RequestParam String uri, Model model){
        model.addAttribute("msg", "DESCRIBE <"+uri+">");
        return "describe";
    }
}
