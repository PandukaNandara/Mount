package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.EventList;

public interface EventListDAO extends CrudDAO<EventList, Integer> {
    boolean saveWithoutPKey(EventList evtlist) throws Exception;
}
