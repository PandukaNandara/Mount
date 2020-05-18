package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    boolean addStudentWithActivity(StudentDTO st) throws Exception;

    ArrayList<StudentDTO> getAll() throws Exception;

    int getNewIndex() throws Exception;

    ObservableList<StudentDTO> getAllStudentNameAndNumber() throws Exception;

    ObservableList<StudentDTO> getAllStudentNameAndNumberButLeft() throws Exception;

    boolean updateStudent(StudentDTO studentDTO) throws Exception;

    StudentDTO getStudent(int SID) throws Exception;

    ObservableList<StudentDTO> getStudentNotDoThisActivity(int AID) throws Exception;

    boolean isUniqueBCID(int BCID) throws Exception;

    boolean isUniqueStudentID(int SID) throws Exception;

    ObservableList<String> getAllDistinctClasses() throws Exception;

    ObservableList<StudentDTO> getStudentWhoIsNotInContributionListOfThisCompetition(int CID) throws Exception;

    ObservableList<StudentDTO> getAllStudentFromExternalDB(String dbPath,
                                                           String tableName, String SID,
                                                           String sName, String gender,
                                                           String DOB, String class_,
                                                           String fatherName, String motherName,
                                                           String note,
                                                           String sAddress, String quit, String BCID)
            throws Exception;

    boolean addUpdateStudentList(ObservableList<StudentDTO> items) throws Exception;

    boolean isLeftStudent(int SID) throws Exception;

    boolean isLeftStudent(String name) throws Exception;

    ArrayList<String> showTablesAndDescTables(String filePath, String tableName) throws Exception;

    boolean checkTableName(String filePath, String tableName) throws Exception;

    boolean checkColumnName(String filePath, String tableName, String columnName) throws Exception;
}
