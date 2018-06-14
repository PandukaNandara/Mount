package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.PaymentBO;
import lk.ijse.mountCalvary.controller.Month;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.ActivityDAO;
import lk.ijse.mountCalvary.dao.custom.PaymentDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.Payment;
import lk.ijse.mountCalvary.model.PaymentDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    private Connection conn;

    private PaymentDAO paymentDAOImpl;
    private ActivityDAO activityDAOImpl;

    private QueryDAO queryDAOImpl;
    public PaymentBOImpl() {
        paymentDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
        activityDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ACTIVITY);
    }

    @Override
    public boolean addAllPayment(ObservableList<PaymentDTO> allPayment) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            for (PaymentDTO paymentDTO : allPayment) {
                if (!paymentDAOImpl.saveWithoutPKey(new Payment(paymentDTO.getRID(),
                        paymentDTO.getFee(),
                        paymentDTO.getMonth().getValue(),
                        paymentDTO.getYear()))) {
                    return false;
                }
            }
            conn.commit();
            return true;
        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<PaymentDTO> getPaymentDetailOfThisStudent(int sid) throws Exception {
        ArrayList<CustomEntity> payments = queryDAOImpl.getPaymentOfThisStudent(sid);
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (CustomEntity onePayment : payments) {
            paymentDTOS.add(new PaymentDTO(
                            onePayment.getPAYID(),
                            onePayment.getRID(),
                            onePayment.getFee(),
                            new Month(onePayment.getMonth()),
                            onePayment.getYear(),
                            onePayment.getAID(),
                            onePayment.getaName()
                    )
            );
        }
        return FXCollections.observableArrayList(paymentDTOS);
    }

    @Override
    public ObservableList<PaymentDTO> getPaymentDetailOfThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> payments = queryDAOImpl.getPaymentOfThisActivity(AID);
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (CustomEntity onePayment : payments) {
            paymentDTOS.add(new PaymentDTO(
                            onePayment.getPAYID(),
                            onePayment.getRID(),
                            onePayment.getSID(),
                            onePayment.getsName(),
                            onePayment.getFee(),
                            new Month(onePayment.getMonth()),
                            onePayment.getYear()
                    )
            );
        }
        return FXCollections.observableArrayList(paymentDTOS);
    }

    @Override
    public ObservableList<PaymentDTO> getPaymentDetailForThisMonthAndYearAndActivity(int aid, int year, int month) throws Exception {
        ArrayList<CustomEntity> payments = queryDAOImpl.getPaymentDetailForThisMonthAndYearAndActivity(aid, year, month);
        String activityName = activityDAOImpl.search(aid).getaName();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (CustomEntity onePayment : payments) {
            paymentDTOS.add(new PaymentDTO(
                            onePayment.getPAYID(),
                            onePayment.getRID(),
                            onePayment.getSID(),
                            onePayment.getsName(),
                            onePayment.getFee(),
                            new Month(onePayment.getMonth()),
                            onePayment.getYear(),
                            activityName
                    )
            );
        }
        return FXCollections.observableArrayList(paymentDTOS);
    }
}
