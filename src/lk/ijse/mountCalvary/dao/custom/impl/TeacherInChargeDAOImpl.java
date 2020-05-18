package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.TeacherInChargeListDAO;
import lk.ijse.mountCalvary.entity.TeacherInChargeList;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TeacherInChargeDAOImpl implements TeacherInChargeListDAO {
    @Override
    public boolean save(TeacherInChargeList tinch) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO TEACHER_IN_CHARGE_LIST VALUES (?, ?, ?)",
                tinch.getTINCID(),
                tinch.getTID(),
                tinch.getCID()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(TeacherInChargeList tinch) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO TEACHER_IN_CHARGE_LIST(TID, CID) VALUES (?, ?)",
                tinch.getTID(),
                tinch.getCID()
        ) > 0;
    }

    @Override
    public boolean update(TeacherInChargeList tinch) throws Exception {
        return CrudUtil.executeUpdate("UPDATE TEACHER_IN_CHARGE_LIST set TINCID = ?, TID = ?, CID = ? where  TINCID = ?",
                tinch.getTINCID(),
                tinch.getTID(),
                tinch.getCID(),
                tinch.getTINCID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from TEACHER_IN_CHARGE_LIST where TINCID = ?", id) > 0;
    }

    @Override
    public TeacherInChargeList search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From TEACHER_IN_CHARGE_LIST where TINCID = ?", id);
        if (rst.next()) {
            return new TeacherInChargeList(
                    rst.getInt("TINCID"),
                    rst.getInt("TID"),
                    rst.getInt("CID")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<TeacherInChargeList> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From TEACHER_IN_CHARGE_LIST");
        ArrayList<TeacherInChargeList> allTeacherInCharge = new ArrayList<>();
        while (rst.next()) {
            new TeacherInChargeList(
                    rst.getInt("TINCID"),
                    rst.getInt("TID"),
                    rst.getInt("CID")
            );
        }
        return allTeacherInCharge;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(TINCID) From TEACHER_IN_CHARGE_LIST").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'TEACHER_IN_CHARGE_LIST'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
