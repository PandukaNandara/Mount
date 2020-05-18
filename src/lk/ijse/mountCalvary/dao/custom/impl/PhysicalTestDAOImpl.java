package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.PhysicalTestDAO;
import lk.ijse.mountCalvary.entity.PhysicalTest;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 2:26 AM
 */
public class PhysicalTestDAOImpl implements PhysicalTestDAO {
    @Override
    public boolean save(PhysicalTest entity) throws Exception {
        return CrudUtil.executeUpdate("insert into physical_test values (?,?,?,?,?,?,?,?,?,?)",
                entity.getPTID(),
                entity.getSID(),
                entity.getTERM_ID(),
                entity.getClass_(),
                entity.getAttendance(),
                entity.getSkill(),
                entity.getProgressEffort(),
                entity.getPerformance(),
                entity.getTotal()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(PhysicalTest entity) throws Exception {
        System.out.println(entity.toString());
        return CrudUtil.executeUpdate("insert into physical_test(SID, TERM_ID, class, attendance, skill, " +
                        "progress_effort, attitudes, performance, total) " +
                        "values (?,?,?,?,?,?,?,?,?)",
                entity.getSID(),
                entity.getTERM_ID(),
                entity.getClass_(),
                entity.getAttendance(),
                entity.getSkill(),
                entity.getProgressEffort(),
                entity.getAttitudes(),
                entity.getPerformance(),
                entity.getTotal()
        ) > 0;
    }

    @Override
    public boolean update(PhysicalTest entity) throws Exception {
        return CrudUtil.executeUpdate("update physical_test set SID = ?, TERM_ID = ?, class = ?, attendance = ?, skill = ?, " +
                        "progress_effort = ?, attitudes = ?, performance = ?, total = ? where PTID = ?",
                entity.getSID(),
                entity.getTERM_ID(),
                entity.getClass_(),
                entity.getAttendance(),
                entity.getSkill(),
                entity.getProgressEffort(),
                entity.getAttitudes(),
                entity.getPerformance(),
                entity.getTotal(),
                entity.getPTID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("delete from physical_test where PTID = ?", id) > 0;
    }

    @Override
    public PhysicalTest search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select * from physical_test where PTID =?", id);
        if (rst.next()) {
            return new PhysicalTest(
                    rst.getInt("PTID"),
                    rst.getInt("SID"),
                    rst.getInt("TERM_ID"),
                    rst.getString("class"),
                    rst.getInt("attendance"),
                    rst.getInt("skill"),
                    rst.getInt("progress_effort"),
                    rst.getInt("attitudes"),
                    rst.getInt("performance"),
                    rst.getInt("total")

            );
        } else return null;
    }

    @Override
    public ArrayList<PhysicalTest> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select * from physical_test where PTID =?");
        ArrayList<PhysicalTest> allPhysicalTests = new ArrayList<>();
        while (rst.next()) {
            allPhysicalTests.add(new PhysicalTest(
                            rst.getInt("PTID"),
                            rst.getInt("SID"),
                            rst.getInt("TERM_ID"),
                            rst.getString("class"),
                            rst.getInt("attendance"),
                            rst.getInt("skill"),
                            rst.getInt("progress_effort"),
                            rst.getInt("attitudes"),
                            rst.getInt("performance"),
                            rst.getInt("total")

                    )
            );
        }
        return allPhysicalTests;
    }

    @Override
    public Integer lastIndex() {
        return null;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'physical_test'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}
