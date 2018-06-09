package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Activity;

public interface ActivityDAO extends CrudDAO<Activity, Integer> {

    boolean saveWithoutPKey(Activity act) throws Exception;

    Activity search(String name) throws Exception;
}
