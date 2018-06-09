package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Student;

import java.util.ArrayList;

public interface StudentDAO extends CrudDAO<Student, Integer> {

    ArrayList<Student> search(String name) throws Exception;

    Student get(int SID) throws Exception;

    ArrayList<Student> getAllStudentNameAndNumber() throws Exception;

}
