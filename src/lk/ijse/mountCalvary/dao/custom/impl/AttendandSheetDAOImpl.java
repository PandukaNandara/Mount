package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.AttendantSheetDAO;
import lk.ijse.mountCalvary.entity.AttendantSheet;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AttendandSheetDAOImpl implements AttendantSheetDAO {
    @Override
    public boolean save(AttendantSheet atts) throws Exception {
        return CrudUtil.executeUpdate("INSERT into attendant_sheet values(?,?,?,?)",
                atts.getATID(),
                atts.getRID(),
                atts.getDate(),
                atts.getTID()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(AttendantSheet atts) throws Exception {
        return CrudUtil.executeUpdate("INSERT into attendant_sheet(RID, date, TID) values(?,?,?)",
                atts.getRID(),
                atts.getDate(),
                atts.getTID()
        ) > 0;
    }

    @Override
    public boolean update(AttendantSheet atts) throws Exception {
        return CrudUtil.executeUpdate("UPDATE attendant_sheet set ATID = ?, RID = ?, date = ?, TID = ? where ATID = ?",
                atts.getATID(),
                atts.getRID(),
                atts.getDate(),
                atts.getTID(),
                atts.getATID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from attendant_sheet where ATID = ?", id) > 0;
    }

    @Override
    public AttendantSheet search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From attendant_sheet where ATID = ?", id);
        if (rst.next()) {
            return new AttendantSheet(
                    rst.getInt("ATID"),
                    rst.getInt("RID"),
                    rst.getDate("date"),
                    rst.getInt("TID")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<AttendantSheet> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From attendant_sheet ");
        ArrayList<AttendantSheet> atList = new ArrayList<>();
        while (rst.next()) {
            atList.add(new AttendantSheet(
                    rst.getInt("ATID"),
                    rst.getInt("RID"),
                    rst.getDate("date"),
                    rst.getInt("TID")
            ));
        }
        return atList;
    }

    @Override
    public Integer lastIndex() {
        return null;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'attendant_sheet'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }

    @Override
    public ArrayList<Integer> getDistinctYears() throws Exception {
        ResultSet rst = CrudUtil.executeQuery(
                "select distinct (year(date))\n" +
                        "from attendant_sheet;");
        ArrayList<Integer> years = new ArrayList<>();
        while (rst.next()) {
            years.add(rst.getInt(1));
        }
        return years;
    }
}
