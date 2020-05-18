package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.CompContributionDAO;
import lk.ijse.mountCalvary.entity.CompContribution;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:15 AM
 */
public class CompContributionDAOImpl implements CompContributionDAO {

    @Override
    public boolean save(CompContribution cc) throws Exception {
        return CrudUtil.executeUpdate("insert into comp_contribution values(?,?,?,?)",
                cc.getCCID(),
                cc.getSID(),
                cc.getCID(),
                cc.getContribution()) > 0;
    }

    @Override
    public boolean saveWithoutPKey(CompContribution cc) throws Exception {
        return CrudUtil.executeUpdate("insert into comp_contribution(SID, CID, contribution) values(?,?,?)",
                cc.getSID(),
                cc.getCID(),
                cc.getContribution()) > 0;
    }

    @Override
    public boolean update(CompContribution cc) throws Exception {
        return CrudUtil.executeUpdate("update comp_contribution set " +
                        "SID = ?," +
                        " CID = ?, " +
                        "contribution = ? " +
                        "where CCID = ?",
                cc.getSID(),
                cc.getCID(),
                cc.getContribution(),
                cc.getCCID()) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("delete " +
                "from comp_contribution where " +
                "CCID = ?", id) > 0;
    }

    @Override
    public CompContribution search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select * " +
                "from comp_contribution " +
                "where CCID = ?", id);
        if (rst.next()) {
            return new CompContribution(
                    rst.getInt("CCID"),
                    rst.getInt("SID"),
                    rst.getInt("CID"),
                    rst.getString("contribution")
            );
        } else
            return null;
    }

    @Override
    public ArrayList<CompContribution> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select * " +
                "from comp_contribution ");
        ArrayList<CompContribution> all = new ArrayList<>();

        while (rst.next()) {
            all.add(
                    new CompContribution(
                            rst.getInt("CCID"),
                            rst.getInt("SID"),
                            rst.getInt("CID"),
                            rst.getString("contribution")
                    ));
        }
        return all;
    }

    @Override
    public Integer lastIndex() {
        return null;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'comp_contribution'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
