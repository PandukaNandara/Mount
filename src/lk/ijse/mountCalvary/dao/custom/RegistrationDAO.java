package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Registration;

public interface RegistrationDAO extends CrudDAO<Registration, Integer> {
    boolean saveWithoutPKey(Registration reg) throws Exception;
}
