package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.ParticipationDAO;
import lk.ijse.mountCalvary.entity.Participation;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ParticipationDAOImpl implements ParticipationDAO {
    @Override
    public boolean save(Participation par) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Participation VALUES (?, ?, ?, ?, ?)",
                par.getPID(),
                par.getRID(),
                par.getELID(),
                par.getResult(),
                par.getPerformance()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Participation par) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Participation(RID, ELID, Result, Performance) VALUES (?, ?, ?, ?)",
                par.getRID(),
                par.getELID(),
                par.getResult(),
                par.getPerformance()
        ) > 0;
    }

    @Override
    public boolean update(Participation par) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Participation set PID = ?, RID = ?, ELID = ?, result = ?, performance = ?, where PID = ?",
                par.getPID(),
                par.getRID(),
                par.getELID(),
                par.getResult(),
                par.getPerformance(),
                par.getPID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("Delete from Participation where PID = ?", id) > 0;
    }

    @Override
    public Participation search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Participation where PID = ?", id);
        if (rst.next()) {
            return new Participation(
                    rst.getInt("PID"),
                    rst.getInt("RID"),
                    rst.getInt("ELID"),
                    rst.getString("Result"),
                    rst.getString("Performance")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Participation> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Participation");
        ArrayList<Participation> allParti = new ArrayList<>();
        while (rst.next()) {
            allParti.add(new Participation(
                    rst.getInt("PID"),
                    rst.getInt("RID"),
                    rst.getInt("ELID"),
                    rst.getString("Result"),
                    rst.getString("Performance")
            ));
        }
        return allParti;
    }

    @Override
    public Integer lastIndex() throws Exception {
        boolean b = CrudUtil.executeQuery("SELECT max(PID) From Participation").next();
        return 0;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Participation'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
