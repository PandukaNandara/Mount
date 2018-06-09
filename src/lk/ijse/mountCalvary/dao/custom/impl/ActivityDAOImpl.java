package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.ActivityDAO;
import lk.ijse.mountCalvary.entity.Activity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ActivityDAOImpl implements ActivityDAO {
    @Override
    public boolean save(Activity act) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Activity VALUES (?, ?, ?)",
                act.getAID(),
                act.getaName(),
                act.getTID()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Activity act) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Activity(aName, TID) VALUES (?, ?)",
                act.getaName(),
                act.getTID()
        ) > 0;
    }

    @Override
    public boolean update(Activity act) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Activity set aName = ?, TID = ? where AID = ?",
                act.getaName(),
                act.getTID(),
                act.getAID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from Activity where id = ?", id) > 0;
    }

    @Override
    public Activity search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Activity where aName like ?%", id);
        if (rst.next()) {
            return new Activity(
                    rst.getInt("AID"),
                    rst.getString("aName"),
                    rst.getInt("TID")
            );
        } else {
            return null;
        }
    }

    @Override
    public Activity search(String name) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Activity where aName = ?", name);
        if (rst.next()) {
            return new Activity(
                    rst.getInt("AID"),
                    rst.getString("aName"),
                    rst.getInt("TID")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Activity> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Activity");
        ArrayList<Activity> actList = new ArrayList<>();
        while (rst.next()) {
            actList.add(new Activity(
                    rst.getInt("AID"),
                    rst.getString("aName"),
                    rst.getInt("TID")
            ));
        }
        return actList;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return null;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Activity'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }

}
