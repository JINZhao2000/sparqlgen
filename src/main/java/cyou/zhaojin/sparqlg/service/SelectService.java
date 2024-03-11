package cyou.zhaojin.sparqlg.service;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.bean.SelectConf;

public interface SelectService {
    String selectWithoutConf(RDFGraph rdf);

    String selectWithConf(RDFGraph rdf, SelectConf conf);
}
