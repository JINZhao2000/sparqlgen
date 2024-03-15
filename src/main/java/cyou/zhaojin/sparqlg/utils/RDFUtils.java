package cyou.zhaojin.sparqlg.utils;

import cyou.zhaojin.sparqlg.bean.RDFGraph;
import cyou.zhaojin.sparqlg.bean.SelectConf;
import cyou.zhaojin.sparqlg.bean.Triple;
import cyou.zhaojin.sparqlg.bean.TripleText;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

public class RDFUtils {
    public static RDFGraph load(InputStream in) {
        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, in, Lang.RDFXML);
        StmtIterator iter = model.listStatements();
        RDFGraph graph = new RDFGraph();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            graph.add(new Triple(stmt.getSubject(), stmt.getPredicate(), stmt.getObject()));
        }
        return graph;
    }

    public static String nodeToString(RDFNode node) {
        if (node.isResource()) {
            return "&lt;" + node.toString() + "&gt;";
        }
        return "\"" + node.toString() + "\"";
    }

    public static SelectConf parseConf(Document doc) {
        doc.getDocumentElement().normalize();
        SelectConf conf = new SelectConf();
        NodeList selectList = doc.getElementsByTagName("select");
        for (int i = 0; i < selectList.getLength(); i++) {
            Element e = (Element) selectList.item(i);
            conf.addSelect(new TripleText(e.getElementsByTagName("subject").item(0).getTextContent(), e.getElementsByTagName("predicate").item(0).getTextContent(), e.getElementsByTagName("object").item(0).getTextContent()));
        }
        Element order = (Element) doc.getElementsByTagName("order").item(0);
        NodeList orderList = order.getElementsByTagName("name");
        for (int i = 0; i < orderList.getLength(); i++) {
            conf.addOrder(orderList.item(i).getTextContent());
        }
        Element descorder = (Element) doc.getElementsByTagName("descorder").item(0);
        NodeList descorderList = descorder.getElementsByTagName("name");
        for (int i = 0; i < descorderList.getLength(); i++) {
            conf.addDescorder(descorderList.item(i).getTextContent());
        }
        Element limit = (Element) doc.getElementsByTagName("limit").item(0);
        conf.setLimit(limit.getTextContent());
        Element offset = (Element) doc.getElementsByTagName("offset").item(0);
        conf.setOffset(offset.getTextContent());
        return conf;
    }

    public static String nodeToStringText(RDFNode node) {
        if (node.isResource()) {
            return "<" + node.toString() + ">";
        }
        return "\"" + node.toString() + "\"";
    }
}
