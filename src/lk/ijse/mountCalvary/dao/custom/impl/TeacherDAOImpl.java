package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.TeacherDAO;
import lk.ijse.mountCalvary.entity.Teacher;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TeacherDAOImpl implements TeacherDAO {
    @Override
    public boolean save(Teacher teacher) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO TEACHER VALUES (?, ?)",
                teacher.getTID(),
                teacher.getTName()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Teacher teacher) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO TEACHER(tName) VALUES (?)",
                teacher.getTName()
        ) > 0;
    }

    @Override
    public boolean update(Teacher teacher) throws Exception {
        return CrudUtil.executeUpdate("UPDATE TEACHER set TID = ?, tName = ? where TID = ?",
                teacher.getTID(),
                teacher.getTName(),
                teacher.getTID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from  TEACHER where TID = ?", id) > 0;
    }

    @Override
    public Teacher search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Teacher where TID = ?", id);
        if (rst.next()) {
            return new Teacher(
                    rst.getInt("TID"),
                    rst.getString("tName")
            );
        } else {
            return null;
        }
    }

    @Override
    public Teacher search(String name) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Teacher where TID like ?%", name);
        if (rst.next()) {
            return new Teacher(
                    rst.getInt("TID"),
                    rst.getString("tName")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Teacher> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Teacher");
        ArrayList<Teacher> teacherList = new ArrayList<>();
        while(rst.next()){
            teacherList.add(new Teacher(
                    rst.getInt("TID"),
                    rst.getString("tName")
            ));
        }
        return teacherList;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(TID) From Teacher").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Teacher'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
