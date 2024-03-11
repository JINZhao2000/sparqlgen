package cyou.zhaojin.sparqlg.service.impl;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.service.ConstructService;
import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.springframework.stereotype.Service;

@Service
public class ConstructServiceImpl implements ConstructService {
    @Override
    public String construct(RDFGraph rdf) {
        StringBuffer buffer = new StringBuffer();
        rdf.getNodes().forEach(e -> {
            buffer.append("CONSTRUCT { ").
                    append(RDFUtils.nodeToString(e.getSubject())).append(" ").
                    append(RDFUtils.nodeToString(e.getPredicate())).append(" ").
                    append(RDFUtils.nodeToString(e.getObject())).append(" . } <br>");
        });
        return buffer.toString();
    }
}
