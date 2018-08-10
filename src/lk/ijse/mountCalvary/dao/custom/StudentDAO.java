package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Student;

import java.util.ArrayList;

public interface StudentDAO extends CrudDAO<Student, Integer> {

    ArrayList<Student> search(String name) throws Exception;

    Student get(int SID) throws Exception;

    ArrayList<Student> getAllStudentNameAndNumber() throws Exception;

    ArrayList<Student> getAllStudentNameAndNumberButLeft() throws Exception;

    boolean isUniqueBCID(int bcid) throws Exception;

    boolean isUniqueStudentID(int SID) throws Exception;

    ArrayList<String> getAllDistinctClasses() throws Exception;

    boolean isLeftStudent(int sid) throws Exception;

    boolean isLeftStudent(String name) throws Exception;
}
