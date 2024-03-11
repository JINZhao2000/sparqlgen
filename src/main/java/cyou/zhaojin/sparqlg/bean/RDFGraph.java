package cyou.zhaojin.sparqlg.bean;

import java.util.ArrayList;
import java.util.List;

public class RDFGraph {
    private final List<Triple> triples;

    public RDFGraph() {
        triples = new ArrayList<>();
    }

    public boolean add(Triple triple) {
        return triples.add(triple);
    }

    public List<Triple> getNodes() {
        return triples;
    }

    @Override
    public String toString() {
        return triples.toString();
    }
}
