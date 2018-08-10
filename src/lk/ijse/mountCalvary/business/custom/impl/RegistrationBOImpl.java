package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.RegistrationBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.dao.custom.RegistrationDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.Registration;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;

import java.sql.Connection;
import java.util.ArrayList;

public final class RegistrationBOImpl implements RegistrationBO {

    RegistrationDAO registrationDAOImpl;
    QueryDAO queryDAOImpl;
    Connection conn;

    public RegistrationBOImpl() {
        registrationDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REGISTRATION);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public boolean addAllRegistration(ObservableList<RegistrationDTO> rgList) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            for (RegistrationDTO oneReg : rgList) {
                System.out.println(rgList.toString());
                if (!registrationDAOImpl.saveWithoutPKey(new Registration(oneReg.getSID(), oneReg.getAID(), oneReg.getJoinedDate())))
                    return false;
            }
            conn.commit();
            return true;
        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<RegistrationDTO> getRegistrationForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> registrations = queryDAOImpl.getRegistrationForThisStudent(SID);
        ArrayList<RegistrationDTO> registrationDTOS = new ArrayList<>();
        for (CustomEntity oneRegi : registrations) {
            registrationDTOS.add(new RegistrationDTO(oneRegi.getRID(), oneRegi.getSID(), oneRegi.getAID(), oneRegi.getJoinedDate(), oneRegi.getaName()));
        }
        return FXCollections.observableArrayList(registrationDTOS);
    }

    @Override
    public ObservableList<ActivityDTO> getActivityListForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> activityList = queryDAOImpl.getActivityListForThisStudent(SID);
        ArrayList<ActivityDTO> activityDTOS = new ArrayList<>();
        for (CustomEntity oneActivity : activityList) {
            activityDTOS.add(new ActivityDTO(oneActivity.getAID(), oneActivity.getaName()));
        }
        return FXCollections.observableArrayList(activityDTOS);
    }
//
//    public static RegistrationDTO searchRegistration(String name, ArrayList<RegistrationDTO> list) {
//        return searchRegistration(name, FXCollections.observableArrayList(list));
//    }
//
//    public static RegistrationDTO searchRegistration(int SID, ArrayList<RegistrationDTO> list) {
//        return searchRegistration(SID, FXCollections.observableArrayList(list));
//    }
//
//    public static RegistrationDTO searchRegistration(String name, ObservableList<RegistrationDTO> list) {
//        for (RegistrationDTO oneRegi : list) {
//            if (oneRegi.getStudentName().toLowerCase().equals(name.trim().toLowerCase().replaceAll(" +", " ")))
//                return oneRegi;
//        }
//        return null;
//    }
//
//    public static RegistrationDTO searchRegistration(int SID, ObservableList<RegistrationDTO> list) {
//        for (RegistrationDTO oneRegi : list) {
//            if (oneRegi.getSID() == SID)
//                return oneRegi;
//        }
//        return null;
//    }
}
