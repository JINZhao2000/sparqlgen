package cyou.zhaojin.sparqlg.service;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import org.springframework.ui.Model;

public interface AskService {
    void askDecomposition(RDFGraph rdf, Model model);

    String construct(String s, String p, String o);
}
