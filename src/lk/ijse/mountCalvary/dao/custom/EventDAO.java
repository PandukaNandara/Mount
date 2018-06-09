package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Event;

import java.util.ArrayList;

public interface EventDAO extends CrudDAO<Event, Integer> {

    boolean saveWithoutPKey(Event evt) throws Exception;

    ArrayList<Event> getEventsForThisActivity(int aid) throws Exception;
}
