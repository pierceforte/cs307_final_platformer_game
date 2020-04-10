package player.saveprogress;

import player.saveprogress.UnableToSyncWOnlineDatabaseException;

public interface file {

    /**
     * Pass in object you are trying to save
     * @param saveObject
     * @throws UnableToSyncWOnlineDatabaseException
     */
    void save(Object saveObject) throws UnableToSyncWOnlineDatabaseException;

    /**
     * Returns the class for the target you specify
     * @param target
     * @return
     * @throws UnableToSyncWOnlineDatabaseException
     */
    Object load(String target) throws UnableToSyncWOnlineDatabaseException;


}
