package cyou.zhaojin.sparqlg.bean;

public class TripleText {
    public static final String ANY = "Any";
    private String subject;
    private String predicate;

    private String object;

    public String getSubject() {
        return subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public TripleText(String subject, String predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }
}
