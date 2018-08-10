package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.PaymentDAO;
import lk.ijse.mountCalvary.entity.Payment;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment pay) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Payment VALUES (?, ?, ?, ?, ?)",
                pay.getPAYID(),
                pay.getRID(),
                pay.getFee().toString(),
                pay.getMonth(),
                pay.getYear()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Payment pay) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO Payment(RID, fee, month, year) VALUES (?, ?, ?, ?)",
                pay.getRID(),
                pay.getFee().toString(),
                pay.getMonth(),
                pay.getYear()
        ) > 0;
    }

    @Override
    public boolean update(Payment pay) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Payment set PAYID = ?, RID = ?, fee = ?, month = ?, year = ? where PAYID = ?",
                pay.getPAYID(),
                pay.getRID(),
                pay.getFee().toString(),
                pay.getMonth(),
                pay.getYear(),
                pay.getPAYID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from Payment where PAYID = ?", id) > 0;
    }

    @Override
    public Payment search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Payment where PAYID = ?");

        if (rst.next()) {
            return new Payment(
                    rst.getInt("PAYID"),
                    rst.getInt("RID"),
                    new BigDecimal(rst.getDouble("fee")),
                    rst.getInt("month"),
                    rst.getInt("year")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Payment> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Payment");
        ArrayList<Payment> payList = new ArrayList<>();
        while (rst.next()) {
            payList.add(new Payment(
                    rst.getInt("PAYID"),
                    rst.getInt("RID"),
                    new BigDecimal(rst.getDouble("fee")),
                    rst.getInt("month"),
                    rst.getInt("year")
            ));
        }
        return payList;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(PAYID) From Payment").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Payment'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }

    @Override
    public ArrayList<Payment> getPaymentForThisActivityAndStudent(int RID) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT *\n" +
                "From Payment\n" +
                "where RID = ?\n" +
                "order by Month and year", RID);
        ArrayList<Payment> payList = new ArrayList<>();
        while (rst.next()) {
            payList.add(new Payment(
                    rst.getInt("PAYID"),
                    rst.getInt("RID"),
                    new BigDecimal(rst.getDouble("fee")),
                    rst.getInt("month"),
                    rst.getInt("year")
            ));
        }
        return payList;
    }

    @Override
    public ArrayList<Integer> getDistinctYears() throws Exception {
        ResultSet rst = CrudUtil.executeQuery(
                "select distinct (Year)\n" +
                        "from payment;");
        ArrayList<Integer> years = new ArrayList<>();
        while (rst.next()) {
            years.add(rst.getInt(1));
        }
        return years;
    }
}
