package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Payment;

import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment, Integer> {
    boolean saveWithoutPKey(Payment pay) throws Exception;

    ArrayList<Payment> getPaymentForThisActivityAndStudent(int RID) throws Exception;

    ArrayList<Integer> getDistinctYears() throws Exception;
}
