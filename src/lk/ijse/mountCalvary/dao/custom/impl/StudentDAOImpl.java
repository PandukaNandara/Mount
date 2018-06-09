package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.StudentDAO;
import lk.ijse.mountCalvary.entity.Student;

import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student st) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Student VALUES (?,?,?,?,?,?,?,?,?,?)",
                st.getSID(),
                st.getsName(),
                st.isGender(),
                st.getDOB(),
                st.getsClass(),
                st.getFatherName(),
                st.getMotherName(),
                st.getNote(),
                st.getHouse(),
                st.getAddress()
        ) > 0;
    }

    @Override
    public boolean update(Student st) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Student set SID = ?, sName = ?, gender = ?, DOB = ?, class = ?, father_name = ?, mother_name = ?, note = ?, house = ?, address = ? where SID = ?",
                st.getSID(),
                st.getsName(),
                st.isGender(),
                st.getDOB(),
                st.getsClass(),
                st.getFatherName(),
                st.getMotherName(),
                st.getNote(),
                st.getHouse(),
                st.getAddress(),
                st.getSID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from Student where SID = ?", id) > 0;
    }

    @Override
    public Student search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("Select * From Student where SID=?", id);
        if (rst.next()) {
            return new Student(
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getBoolean("gender"),
                    rst.getDate("DOB"),
                    rst.getString("class"),
                    rst.getString("father_name"),
                    rst.getString("mother_name"),
                    rst.getString("note"),
                    rst.getString("house"),
                    rst.getString("address")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Student> search(String name) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("Select * From Student where sName like ?%", name);
        ArrayList<Student> stList = new ArrayList<>();
        while (rst.next()) {
            stList.add(new Student(
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getBoolean("gender"),
                    rst.getDate("DOB"),
                    rst.getString("class"),
                    rst.getString("father_name"),
                    rst.getString("mother_name"),
                    rst.getString("note"),
                    rst.getString("house"),
                    rst.getString("address")
            ));
        }
        return stList;
    }

    @Override
    public ArrayList<Student> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("Select * From Student");
        ArrayList<Student> all = new ArrayList<>();
        while (rst.next()) {
            all.add(new Student(
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getBoolean("gender"),
                    rst.getDate("DOB"),
                    rst.getString("class"),
                    rst.getString("father_name"),
                    rst.getString("mother_name"),
                    rst.getString("note"),
                    rst.getString("house"),
                    rst.getString("address")
            ));
        }
        return all;
    }

    //This method is used to get details of particular student
    @Override
    public Student get(int SID) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("Select * From Student where SID = ?", SID);
        ArrayList<Student> all = new ArrayList<>();
        if (rst.next())
            return new Student(
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getBoolean("gender"),
                    rst.getDate("DOB"),
                    rst.getString("class"),
                    rst.getString("father_name"),
                    rst.getString("mother_name"),
                    rst.getString("note"),
                    rst.getString("house"),
                    rst.getString("address")
            );
        return null;
    }

    @Override
    public ArrayList<Student> getAllStudentNameAndNumber() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select SID, sName From Student");
        ArrayList<Student> all = new ArrayList<>();
        while (rst.next()) {
            all.add(new Student(
                    rst.getInt("SID"),
                    rst.getString("sName")
                    )
            );
        }
        return all;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(SID) From Student").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Student'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
