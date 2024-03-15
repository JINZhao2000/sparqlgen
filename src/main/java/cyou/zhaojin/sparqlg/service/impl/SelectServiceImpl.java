package cyou.zhaojin.sparqlg.service.impl;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.bean.SelectConf;
import cyou.zhaojin.sparqlg.bean.Triple;
import cyou.zhaojin.sparqlg.bean.TripleText;
import cyou.zhaojin.sparqlg.service.SelectService;
import cyou.zhaojin.sparqlg.utils.RDFUtils;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.awt.image.BandCombineOp;
import java.util.*;
import java.util.concurrent.Phaser;
import java.util.function.Predicate;

@Service
public class SelectServiceImpl implements SelectService {
    @Override
    public String selectWithoutConf(RDFGraph graph) {
        Set<String> qls = new HashSet<>();
        graph.getNodes().forEach(e -> qls.add("SELECT ?X1 ?X2 { WHERE ?X1 " +
                RDFUtils.nodeToString(e.getPredicate()) +
                " ?X2 . } <br>"));
        StringBuilder builder = new StringBuilder();
        qls.forEach(builder::append);
        return builder.toString();
    }

    @Override
    public String selectWithConf(RDFGraph graph, SelectConf conf) {
        StringBuilder builder = new StringBuilder();
        List<TripleText> selectList = conf.getSelects();
        Map<String, String> mappedVariables = new HashMap<>();
        Set<String> variables = mapVariables(selectList, mappedVariables);
        Set<String> subjects = new HashSet<>();
        Set<String> predicates = new HashSet<>();
        Set<String> objects = new HashSet<>();
        initSets(graph, subjects, predicates, objects);
        builder.append("SELECT ");
        variables.forEach(e -> {
            builder.append("?").append(e).append(" ");
        });
        builder.append(" { WHERE ");
        selectList.forEach(e -> {
            String subject = e.getSubject();
            String predicate = e.getPredicate();
            String object = e.getObject();
            if (variables.contains(subject)) {
                builder.append("?").append(subject).append(" ");
            } else {
                builder.append(match(subjects, subject)).append(" ");
            }
            if (variables.contains(predicate)) {
                builder.append("?").append(predicate).append(" ");
            } else {
                builder.append(match(predicates, predicate)).append(" ");
            }
            if (variables.contains(object)) {
                builder.append("?").append(object).append(" ");
            } else {
                builder.append(match(objects, object)).append(" ");
            }
        });
        builder.append(". } ");
        if (!(conf.getOrders().isEmpty() && conf.getDescorders().isEmpty())) {
            builder.append("ORDER BY ");
            for (String order : conf.getOrders()) {
                if (mappedVariables.containsKey(order)) {
                    builder.append("?").append(mappedVariables.get(order)).append(" ");
                    mappedVariables.remove(order);
                }
            }
            for (String descorder : conf.getDescorders()) {
                if (mappedVariables.containsKey(descorder)) {
                    builder.append("desc(?").append(mappedVariables.get(descorder)).append(") ");
                    mappedVariables.remove(descorder);
                }
            }
        }
        if (!"-1".equals(conf.getLimit())) {
            builder.append("LIMIT ").append(conf.getLimit()).append(" ");
        }
        if (!"-1".equals(conf.getOffset())) {
            builder.append("OFFSET ").append(conf.getOffset()).append(" ");
        }
        builder.append("<br>");
        return builder.toString();
    }

    private String match(Set<String> set, String key) {
        for (String s : set) {
            if (s.contains(key)) {
                return s;
            }
        }
        return key;
    }

    private void initSets(RDFGraph graph, Set<String> subjects, Set<String> predicates, Set<String> objects) {
        if (graph == null) {
            return;
        }
        graph.getNodes().forEach(e -> {
            subjects.add(RDFUtils.nodeToString(e.getSubject()));
            predicates.add(RDFUtils.nodeToString(e.getPredicate()));
            objects.add(RDFUtils.nodeToString(e.getObject()));
        });
    }

    private Set<String> mapVariables(List<TripleText> selectList, Map<String, String> map) {
        int anyNum = 1;
        int sameNum = 1;
        Set<String> variables = new HashSet<>();
        for (TripleText tripleText : selectList) {
            String subject = tripleText.getSubject();
            String predicate = tripleText.getPredicate();
            String object = tripleText.getObject();
            if (TripleText.ANY.equals(subject)) {
                String var = "Y"+anyNum++;
                variables.add(var);
                tripleText.setSubject(var);
            }
            if (TripleText.ANY.equals(predicate)) {
                String var = "Y"+anyNum++;
                variables.add(var);
                tripleText.setPredicate(var);
            }
            if (TripleText.ANY.equals(object)) {
                String var = "Y"+anyNum++;
                variables.add(var);
                tripleText.setObject(var);
            }
            if (subject != null && subject.length() == 1) {
                if (map.containsKey(subject)) {
                    tripleText.setSubject(map.get(subject));
                } else {
                    String value = "X" + sameNum++;
                    variables.add(value);
                    map.put(subject, value);
                    tripleText.setSubject(value);
                }
            }
            if (predicate != null && predicate.length() == 1) {
                if (map.containsKey(predicate)) {
                    tripleText.setPredicate(map.get(predicate));
                } else {
                    String value = "X" + sameNum++;
                    variables.add(value);
                    map.put(predicate, value);
                    tripleText.setPredicate(value);
                }
            }
            if (object != null && object.length() == 1) {
                if (map.containsKey(object)) {
                    tripleText.setObject(map.get(object));
                } else {
                    String value = "X" + sameNum++;
                    variables.add(value);
                    map.put(object, value);
                    tripleText.setObject(value);
                }
            }
        }
        return variables;
    }
}
