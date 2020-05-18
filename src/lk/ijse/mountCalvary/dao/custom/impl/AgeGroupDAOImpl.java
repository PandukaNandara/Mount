package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.AgeGroupDAO;
import lk.ijse.mountCalvary.entity.AgeGroup;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AgeGroupDAOImpl implements AgeGroupDAO {
    @Override
    public boolean save(AgeGroup ag) throws Exception {
        return CrudUtil.executeUpdate("INSERT into age_group values(?, ?, ?, ?)",
                ag.getGID(),
                ag.getGroupName(),
                ag.getMin(),
                ag.getMax()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(AgeGroup ag) throws Exception {
        return CrudUtil.executeUpdate("INSERT into age_group(group_name, min, max) values(?, ?, ?)",
                ag.getGID(),
                ag.getGroupName(),
                ag.getMin(),
                ag.getMax()
        ) > 0;
    }

    @Override
    public boolean update(AgeGroup ag) throws Exception {
        return CrudUtil.executeUpdate("UPDATE age_group set GID = ?, group_name = ? , min = ?, max = ?",
                ag.getGID(),
                ag.getGroupName(),
                ag.getMin(),
                ag.getMax()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from age_group where GID = ?", id) > 0;
    }

    @Override
    public AgeGroup search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From age_group where GID = ?", id);
        if (rst.next()) {
            return new AgeGroup(
                    rst.getInt("GID"),
                    rst.getString("group_name"),
                    rst.getInt("min"),
                    rst.getInt("max")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<AgeGroup> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From age_group");
        ArrayList<AgeGroup> ageGroupList = new ArrayList<>();
        while (rst.next()) {
            ageGroupList.add(new AgeGroup(
                    rst.getInt("GID"),
                    rst.getString("group_name"),
                    rst.getInt("min"),
                    rst.getInt("max")
            ));
        }
        return ageGroupList;
    }

    @Override
    public Integer lastIndex() {
        return null;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Age_group'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
