package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment, Integer> {
    boolean saveWithoutPKey(Payment pay) throws Exception;
}
