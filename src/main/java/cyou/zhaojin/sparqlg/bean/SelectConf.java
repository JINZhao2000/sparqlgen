package cyou.zhaojin.sparqlg.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectConf {
    private List<Variable> selects;
    private Set<String> orders;
    private Set<String> descorders;
    private String limit;
    private String offset;

    public SelectConf() {
        selects = new ArrayList<>();
        orders = new HashSet<>();
        descorders = new HashSet<>();
    }

    public boolean addSelect(Variable variable) {
        return selects.add(variable);
    }

    public boolean addOrder(String order) {
        if (!hasVariable(order)) {
            return false;
        }
        return orders.add(order);
    }

    public boolean addDescorder(String descorder) {
        if (orders.contains(descorder) || !hasVariable(descorder)) {
            return false;
        }
        return descorders.add(descorder);
    }

    public List<Variable> getSelects() {
        return selects;
    }

    public Set<String> getOrders() {
        return orders;
    }

    public Set<String> getDescorders() {
        return descorders;
    }

    public String getLimit() {
        return limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setLimit(String limit){
        this.limit = limit;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public boolean hasVariable(String order) {
        for (Variable v : selects) {
            if (v.getName().equals(order)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "SelectConf{" +
                "selects=" + selects +
                ", orders=" + orders +
                ", descorders=" + descorders +
                ", limit='" + limit + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}
