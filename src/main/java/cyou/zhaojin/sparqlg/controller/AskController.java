package cyou.zhaojin.sparqlg.controller;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.service.AskService;
import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zhao JIN
 */
@Controller
public class AskController {
    private AskService askService;

    @Autowired
    public void setAskService(AskService askService) {
        this.askService = askService;
    }

    @PostMapping("/ask")
    public String askForm(@RequestParam("rdf") MultipartFile rdf, Model model) {
        if (rdf.isEmpty()) {
            model.addAttribute("msg", "File is empty");
            return "ask";
        }
        try {
            RDFGraph graph = RDFUtils.load(rdf.getInputStream());
            askService.askDecomposition(graph, model);
            model.addAttribute("msg", "");
            return "ask2";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "ask";
        }
    }

    @PostMapping("/ask2")
    public String ask2Form(@RequestParam("s") String s, @RequestParam("p") String p, @RequestParam("o") String o, Model model) {
        model.addAttribute("msg", askService.construct(s, p, o));
        return "ask";
    }

    @GetMapping("/ask")
    public String ask(Model model){
        model.addAttribute("msg", "");
        return "ask";
    }
}
