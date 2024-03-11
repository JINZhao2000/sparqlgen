package cyou.zhaojin.sparqlg.controller;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.service.ConstructService;
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
public class ConstructController {
    private ConstructService constructService;

    @Autowired
    public void setConstructService(ConstructService constructService) {
        this.constructService = constructService;
    }

    @GetMapping("/construct")
    public String construct(Model model){
        model.addAttribute("msg", "");
        return "construct";
    }

    @PostMapping("/construct")
    public String constructForm(@RequestParam("rdf") MultipartFile rdf, Model model) {
        if (rdf.isEmpty()) {
            model.addAttribute("msg", "File is empty");
            return "construct";
        }
        try {
            RDFGraph graph = RDFUtils.load(rdf.getInputStream());
            model.addAttribute("msg", constructService.construct(graph));
            return "construct";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "construct";
        }
    }
}
