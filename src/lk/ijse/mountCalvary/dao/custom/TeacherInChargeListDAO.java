package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.TeacherInChargeList;

public interface TeacherInChargeListDAO extends CrudDAO<TeacherInChargeList, Integer> {
    boolean saveWithoutPKey(TeacherInChargeList tinch) throws Exception;
}
