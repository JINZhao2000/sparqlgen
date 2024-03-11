package cyou.zhaojin.sparqlg.bean;

public class Variable {
    private final String type;
    private final String name;

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return type + ":" + name;
    }
}
