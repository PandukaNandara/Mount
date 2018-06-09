package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.PaymentDTO;

public interface PaymentBO extends SuperBO {
    boolean addAllPayment(ObservableList<PaymentDTO> allPayment) throws Exception;

    ObservableList<PaymentDTO> getPaymentDetailOfThisStudent(int sid) throws Exception;

    ObservableList<PaymentDTO> getPaymentDetailOfThisActivity(int AID) throws Exception;
}