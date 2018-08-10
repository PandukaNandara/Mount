package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.TelNoDAO;
import lk.ijse.mountCalvary.entity.TelNo;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TelNoDAOImpl implements TelNoDAO {

    @Override
    public boolean save(TelNo tel) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO telephone_no VALUES (?, ?, ?)",
                tel.getTelID(),
                tel.getTelNo(),
                tel.getSID()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(TelNo tel) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO telephone_no(tel_no, SID) VALUES (?, ?)",
                tel.getTelNo(),
                tel.getSID()
        ) > 0;
    }

    @Override
    public boolean update(TelNo tel) throws Exception {
        return CrudUtil.executeUpdate("UPDATE telephone_no set TELID = ?, tel_no = ?, SID = ? where TELID = ?",
                tel.getTelID(),
                tel.getTelNo(),
                tel.getSID(),
                tel.getTelID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from telephone_no where TELID = ?", id) > 0;
    }

    @Override
    public TelNo search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From telephone_no where TELID = ?", id);
        if (rst.next()) {
            return new TelNo(
                    rst.getInt("TELID"),
                    rst.getString("tel_no"),
                    rst.getInt("SID")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<TelNo> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From telephone_no ");
        ArrayList<TelNo> allTelNo = new ArrayList<>();
        while (rst.next()) {
            allTelNo.add(new TelNo(
                    rst.getInt("TELID"),
                    rst.getString("tel_no"),
                    rst.getInt("SID")
            ));
        }
        return allTelNo;
    }

    //This method is used to get telephone number of particular student

    @Override
    public ArrayList<TelNo> getTelNosForThisStudent(int SID) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From telephone_no where SID = ?", SID);
        ArrayList<TelNo> allTelNo = new ArrayList<>();
        while (rst.next()) {
            allTelNo.add(new TelNo(
                    rst.getInt("TELID"),
                    rst.getString("tel_no"),
                    rst.getInt("SID")
            ));
        }
        return allTelNo;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(TELID) From telephone_no").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'telephone_no'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
