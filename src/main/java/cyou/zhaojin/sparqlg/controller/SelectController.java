package cyou.zhaojin.sparqlg.controller;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.service.SelectService;
import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Zhao JIN
 */
@Controller
public class SelectController {
    private SelectService selectService;

    @Autowired
    public void setSelectService(SelectService selectService) {
        this.selectService = selectService;
    }

    @PostMapping("/select")
    public String selectForm(@RequestParam("rdf")MultipartFile rdf, @RequestParam("conf") MultipartFile conf, Model model) {
        if (rdf.isEmpty()) {
            model.addAttribute("msg", "File is empty");
            return "select";
        }
        try {
            RDFGraph graph = RDFUtils.load(rdf.getInputStream());
            if (conf.isEmpty()) {
                model.addAttribute("msg", selectService.selectWithoutConf(graph));
                return "select";
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(conf.getInputStream());
            model.addAttribute("msg", selectService.selectWithConf(graph, RDFUtils.parseConf(doc)));
            return "select";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "select";
        }
    }

    @GetMapping("/select")
    public String select(Model model){
        model.addAttribute("msg", "");
        return "select";
    }
}
