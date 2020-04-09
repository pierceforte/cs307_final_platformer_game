
public interface file {

    /**
     * Pass in object you are trying to save
     * @param saveObject
     * @throws UnableToSyncWOnlineDatabase
     */
    void save(Object saveObject) throws UnableToSyncWOnlineDatabase;

    /**
     * Returns the class for the target you specify
     * @param target
     * @return
     * @throws UnableToSyncWOnlineDatabase
     */
    Object load(String target) throws UnableToSyncWOnlineDatabase;
}