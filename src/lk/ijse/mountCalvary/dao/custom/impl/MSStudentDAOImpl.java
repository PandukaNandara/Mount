package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.MSCrudUtil;
import lk.ijse.mountCalvary.dao.custom.MSStudentDAO;
import lk.ijse.mountCalvary.db.MSDBConnection;
import lk.ijse.mountCalvary.entity.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/4/2018
 * Time: 9:09 PM
 */
public class MSStudentDAOImpl implements MSStudentDAO {

    @Override
    public ArrayList<Student> getAllStudentFromExternalDB(final String dbPath,
                                                          String tableName, String SID,
                                                          String[] sName, String gender,
                                                          String DOB, String class_,
                                                          String fatherName, String motherName,
                                                          String note,
                                                          String[] sAddress, String quit, String BCID)
            throws Exception {

        ArrayList<Student> allStudents = new ArrayList<>();
        Connection conn = MSDBConnection.getInstance().getConnection(dbPath);

        ResultSet rst = MSCrudUtil.executeQuery(conn,
                "select * from [" + tableName + "] order by [" + SID + "]");
//        ResultSetMetaData rsmd = rst.getMetaData();
//        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//            System.out.println(1 + " => "+ rsmd.getColumnName(i));
//        }

        while (rst.next()) {
            String name = "";
            for (String part : sName) {
                String secondaryName = rst.getString(part);
                name = String.format("%s %s", name,
                        secondaryName != null ?
                                secondaryName : ""
                );
            }
            name = name.trim().replaceAll(" +", " ");

            String address = "";
            for (String part : sAddress) {
                String secondaryAddress = rst.getString(part);
                address = String.format("%s %s", address,
                        secondaryAddress != null ?
                                secondaryAddress : ""
                );
            }
            address = address.trim().replaceAll(" +", " ");

            int sid = rst.getInt(SID);
            int bcid;
            try {
                bcid = Integer.parseInt(rst.getString(BCID));
            } catch (NumberFormatException e) {
                bcid = 0;
            }
            String class2;
            String class_1 = rst.getString(class_).trim();

            if (class_1.length() == 4)
                class2 = class_1.trim().substring(0, 4);
            else if (class_1.length() == 3)
                class2 = class_1.trim().substring(0, 3);
            else
                class2 = class_1;

            allStudents.add(new Student(
                    sid,
                    name,
                    rst.getInt(gender) == 2 ? Student.MALE : Student.FEMALE,
                    rst.getDate(DOB),
                    class2,
                    rst.getString(fatherName),
                    rst.getString(motherName),
                    rst.getString(note),
                    sid % 4,
                    address,
                    rst.getBoolean(quit),
                    bcid));
        }
        return allStudents;
    }
}
