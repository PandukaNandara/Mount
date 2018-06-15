package lk.ijse.mountCalvary.business.custom;

import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    boolean addStudent(StudentDTO st) throws Exception;

    ArrayList<StudentDTO> getAll() throws Exception;

    int getNewIndex() throws Exception;

    ArrayList<StudentDTO> getAllStudentNameAndNumber() throws Exception;

    boolean updateStudent(StudentDTO studentDTO) throws Exception;

    StudentDTO getStudent(int SID) throws Exception;
}
