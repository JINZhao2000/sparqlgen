package cyou.zhaojin.sparqlg.service.impl;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.service.AskService;
import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

@Service
public class AskServiceImpl implements AskService {
    @Override
    public void askDecomposition(RDFGraph rdf, Model model) {
        Set<String> subjects = new HashSet<>();
        Set<String> predicates = new HashSet<>();
        Set<String> objects = new HashSet<>();
        rdf.getNodes().forEach(e -> {
            subjects.add(RDFUtils.nodeToStringText(e.getSubject()));
            predicates.add(RDFUtils.nodeToStringText(e.getPredicate()));
            objects.add(RDFUtils.nodeToStringText(e.getObject()));
        });
        model.addAttribute("rsubjects", subjects);
        model.addAttribute("rpredicates", predicates);
        model.addAttribute("robjects", objects);
    }

    @Override
    public String construct(String s, String p, String o) {
        return "ASK { " + s + " " + p + " " + o + ". }";
    }
}
