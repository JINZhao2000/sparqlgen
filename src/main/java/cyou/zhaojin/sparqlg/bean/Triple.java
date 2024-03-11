package cyou.zhaojin.sparqlg.bean;

import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.apache.jena.rdf.model.RDFNode;

public class Triple {
    private final RDFNode subject;
    private final RDFNode predicate;
    private final RDFNode object;

    public Triple(RDFNode subject, RDFNode predicate, RDFNode object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public RDFNode getSubject() {
        return subject;
    }

    public RDFNode getPredicate() {
        return predicate;
    }

    public RDFNode getObject() {
        return object;
    }

    @Override
    public String toString() {
        return RDFUtils.nodeToString(subject) + " " + RDFUtils.nodeToString(predicate) + " " + RDFUtils.nodeToString(object);
    }
}
