package cyou.zhaojin.sparqlg.service.impl;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.bean.SelectConf;
import cyou.zhaojin.sparqlg.bean.Triple;
import cyou.zhaojin.sparqlg.bean.Variable;
import cyou.zhaojin.sparqlg.service.SelectService;
import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SelectServiceImpl implements SelectService {

    @Override
    public String selectWithoutConf(RDFGraph graph) {
        StringBuffer buffer = new StringBuffer();
        graph.getNodes().forEach(e -> buffer.
                append("SELECT ?X { WHERE ?X ").
                append(RDFUtils.nodeToString(e.getPredicate())).append(" ").
                append(RDFUtils.nodeToString(e.getObject())).append(" ").
                append(". } <br>"));
        return buffer.toString();
    }

    @Override
    public String selectWithConf(RDFGraph graph, SelectConf conf) {
        Map<String, String> variables = new HashMap<>();
        List<Triple> conformedTriples = new ArrayList<>();
        for (Triple node : graph.getNodes()) {
            boolean conform = true;
            for (Variable select : conf.getSelects()) {
                if (select.getType().equals("s")) {
                    if (!node.getSubject().toString().equals(select.getName())) {
                        conform = false;
                        break;
                    }
                }
                if (select.getType().equals("p")) {
                    if (!node.getPredicate().toString().equals(select.getName())) {
                        conform = false;
                        break;
                    }
                }
                if (select.getType().equals("o")) {
                    if (!node.getObject().toString().equals(select.getName())) {
                        conform = false;
                    }
                }
            }
            if (conform) {
                conformedTriples.add(node);
            }
        }
        System.out.println(conformedTriples);
        for (int i = 0; i < conf.getSelects().size(); i++) {
            String key = conf.getSelects().get(i).getName();
            if (!variables.containsKey(key)) {
                variables.put(key, "?X"+i);
            }
        }
        StringBuffer buffer = new StringBuffer();
        conformedTriples.forEach(e -> {
            buffer.append("SELECT ");
            List<String> vs = new ArrayList<>();
            String s = e.getSubject().toString();
            String p = e.getPredicate().toString();
            String o = e.getObject().toString();
            boolean hasS = variables.containsKey(s);
            boolean hasP = variables.containsKey(p);
            boolean hasO = variables.containsKey(o);
            if (hasS) {
                vs.add(variables.get(s));
            }

            if (hasP) {
                vs.add(variables.get(p));
            }

            if (hasO) {
                vs.add(variables.get(o));
            }
            for (String v : vs) {
               buffer.append(v).append(" ");
            }
            buffer.append(" { WHERE ");
            if (hasS) {
                buffer.append(variables.get(s));
            } else {
                buffer.append(RDFUtils.nodeToString(e.getSubject()));
            }
            buffer.append(" ");
            if (hasP) {
                buffer.append(variables.get(p));
            } else {
                buffer.append(RDFUtils.nodeToString(e.getPredicate()));
            }
            buffer.append(" ");
            if (hasO) {
                buffer.append(variables.get(o));
            } else {
                buffer.append(RDFUtils.nodeToString(e.getObject()));
            }
            buffer.append(" ");
            buffer.append(". } ");
            if (! (conf.getOrders().isEmpty() && conf.getDescorders().isEmpty())) {
                buffer.append("ORDER BY ");
                for (String order : conf.getOrders()) {
                    buffer.append(variables.get(order)).append(" ");
                }
                for (String descorder : conf.getDescorders()) {
                    buffer.append("desc(").append(variables.get(descorder)).append(") ");
                }
            }
            if (! "-1".equals(conf.getLimit())) {
                buffer.append("LIMIT ").append(conf.getLimit()).append(" ");
            }
            if (! "-1".equals(conf.getOffset())) {
                buffer.append("OFFSET ").append(conf.getOffset()).append(" ");
            }
            buffer.append("<br>");

        });
        return buffer.toString();
    }
}
