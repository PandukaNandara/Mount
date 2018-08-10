package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.SuperDAO;
import lk.ijse.mountCalvary.entity.Student;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/4/2018
 * Time: 9:08 PM
 */
public interface MSStudentDAO extends SuperDAO {
    ArrayList<Student> getAllStudentFromExternalDB(String dbPath,
                                                   String tableName, String SID,
                                                   String[] sName, String gender,
                                                   String DOB, String class_,
                                                   String fatherName, String motherName,
                                                   String note,
                                                   String[] sAddress, String quit, String BCID)
            throws Exception;
}
