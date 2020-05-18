package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.LogIn;

public interface LogInDAO extends CrudDAO<LogIn, Integer> {

    boolean saveWithoutPKey(LogIn li) throws Exception;

    LogIn search(String username) throws Exception;
}
