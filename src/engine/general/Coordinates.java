package engine.general;

/**
 * The coordinates class holds the necessary information for each
 * object, action, etc.
 *
 * @author Jerry Huang
 */
public class Coordinates {
    private String name;
    private Object object;
    private Class<?> objectClass;

    public Coordinates(String name, Object o, Class<?> oc) {
        this.name = name;
        this.object = o;
        this.objectClass = oc;
    }

    public String getName() {
        return name;
    }
    public Object getObject() {
        return this.object;
    }
    public Class<?> getObjectClass() {
        return this.objectClass;
    }

    public void setName(String str) {
        this.name = str;
    }
    public void setObject(Object o) {
        this.object = o;
    }
    public void setObjectClass(Class<?> oc) {
        this.objectClass = oc;
    }
}
