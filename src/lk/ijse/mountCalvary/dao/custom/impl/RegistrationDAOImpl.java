package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.RegistrationDAO;
import lk.ijse.mountCalvary.entity.Registration;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public boolean save(Registration reg) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Registration VALUES (?, ?, ?, ?)",
                reg.getRID(),
                reg.getSID(),
                reg.getAID(),
                reg.getJoinedDate()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Registration reg) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Registration(SID, AID, Joined_date) VALUES (?, ?, ?)",
                reg.getSID(),
                reg.getAID(),
                reg.getJoinedDate()
        ) > 0;
    }

    @Override
    public boolean update(Registration reg) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Registration set RID = ?, SID = ?, AID = ?, Joined_date = ? where RID = ?",
                reg.getRID(),
                reg.getSID(),
                reg.getAID(),
                reg.getJoinedDate(),
                reg.getRID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from Registration where RID = ?", id) > 0;
    }

    @Override
    public Registration search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Registration where RID = ?", id);
        if (rst.next()) {
            return new Registration(
                    rst.getInt("RID"),
                    rst.getInt("SID"),
                    rst.getInt("AID"),
                    rst.getDate("Joined_date")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Registration> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Registration");
        ArrayList<Registration> regId = new ArrayList<>();
        while (rst.next()) {
            regId.add(new Registration(
                    rst.getInt("RID"),
                    rst.getInt("SID"),
                    rst.getInt("AID"),
                    rst.getDate("Joined_date")
            ));
        }
        return regId;
    }

    @Override
    public Integer lastIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT max(RID) From Registration");
        return rst.getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'registration'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }

    @Override
    public ArrayList<Integer> getDistinctYears() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select distinct (year(joined_date))\n" +
                "from registration;");
        ArrayList<Integer> years = new ArrayList<>();
        while (rst.next()) {
            years.add(rst.getInt(1));
        }
        return years;
    }
}
