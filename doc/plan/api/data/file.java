
public interface file {

    public save(Object saveObject);

    public pushToDatabase(Object saveObject);

    public pullFromDatabase(String target);

    public load(String target);

}