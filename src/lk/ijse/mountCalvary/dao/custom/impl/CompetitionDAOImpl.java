package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.CompetitionDAO;
import lk.ijse.mountCalvary.entity.Competition;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CompetitionDAOImpl implements CompetitionDAO {
    @Override
    public boolean save(Competition com) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Competition VALUES (?, ?, ?, ?, ?)",
                com.getCID(),
                com.getComName(),
                com.getLocation(),
                com.getDate(),
                com.getDesc()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Competition com) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Competition(cName, location, Date, desc_) VALUES (?, ?, ?, ?)",
                com.getComName(),
                com.getLocation(),
                com.getDate(),
                com.getDesc()
        ) > 0;
    }

    @Override
    public boolean update(Competition com) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Competition set CID = ?, cName = ?, location = ?, date = ?, desc_ = ? where CID = ?",
                com.getCID(),
                com.getComName(),
                com.getLocation(),
                com.getDate(),
                com.getDesc(),
                com.getCID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from Competition where CID = ?", id) > 0;
    }

    @Override
    public Competition search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Competition where CID = ?", id);
        if (rst.next()) {
            return new Competition(
                    rst.getInt("CID"),
                    rst.getString("cName"),
                    rst.getString("location"),
                    rst.getDate("Date"),
                    rst.getString("desc_")
            );
        } else {
            return null;
        }
    }

    @Override
    public Competition search(String name) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Competition where cName = ?", name);
        if (rst.next()) {
            return new Competition(
                    rst.getInt("CID"),
                    rst.getString("cName"),
                    rst.getString("location"),
                    rst.getDate("Date"),
                    rst.getString("desc_")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Competition> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Competition order by CID DESC");
        ArrayList<Competition> comList = new ArrayList<>();
        while (rst.next()) {
            comList.add(new Competition(
                    rst.getInt("CID"),
                    rst.getString("cName"),
                    rst.getString("location"),
                    rst.getDate("Date"),
                    rst.getString("desc_")
            ));
        }
        return comList;
    }

    @Override
    public ArrayList<Competition> getCompetitionsNameAndNumber() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT CID, cName From Competition order by CID DESC");
        ArrayList<Competition> comList = new ArrayList<>();
        while (rst.next()) {
            comList.add(new Competition(
                    rst.getInt("CID"),
                    rst.getString("cName")
            ));
        }
        return comList;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(CID) From Competition").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'competition'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
