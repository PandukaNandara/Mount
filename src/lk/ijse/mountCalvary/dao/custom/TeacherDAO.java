package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Teacher;

public interface TeacherDAO extends CrudDAO<Teacher, Integer> {
    boolean saveWithoutPKey(Teacher teacher) throws Exception;

    Teacher search(String id) throws Exception;
}
