package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.AgeGroup;

public interface AgeGroupDAO extends CrudDAO<AgeGroup, Integer> {

    boolean saveWithoutPKey(AgeGroup ag) throws Exception;
}
