package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.*;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.Activity;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.Event;
import lk.ijse.mountCalvary.entity.Registration;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.EventDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class ActivityBOImpl implements ActivityBO {
    private ActivityDAO activityImpl;
    private EventDAO eventImpl;
    private StudentDAO studentDAOImpl;
    private RegistrationDAO registrationImpl;
    private Connection conn;
    private QueryDAO queryDAOImpl;

    public ActivityBOImpl() {
        activityImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ACTIVITY);
        eventImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EVENT);
        studentDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
        registrationImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REGISTRATION);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public ArrayList<ActivityDTO> getAllActivity() throws Exception {
        ArrayList<Activity> all = activityImpl.getAll();
        ArrayList<ActivityDTO> activityList = new ArrayList<>();
        for (Activity a : all) {
            activityList.add(new ActivityDTO(a.getAID(), a.getaName(), a.getTID()));
        }
        return activityList;
    }

    @Override
    public ArrayList<ActivityDTO> getAllActivityWithEvent() throws Exception {

        ArrayList<Event> allEvent = eventImpl.getAll();

        ArrayList<Activity> allActivity = activityImpl.getAll();

        ArrayList<ActivityDTO> allActivityWithEvent = new ArrayList<>();
        for (int i = 0; i < allActivity.size(); i++) {
            Activity oneAct = allActivity.get(i);
            allActivityWithEvent.add(i, new ActivityDTO(oneAct.getAID(), oneAct.getaName(), oneAct.getTID()));
            ArrayList<EventDTO> eventForActivity = new ArrayList<>();
            for (int j = 0; j < allEvent.size(); j++) {
                Event oneEvent = allEvent.get(j);
                if (allActivityWithEvent.get(i).getAID() == oneEvent.getAID()) {
                    eventForActivity.add(new EventDTO(oneEvent.getEID(), oneEvent.geteName(), oneEvent.isGender(), allActivityWithEvent.get(i)));
                }
            }
            allActivityWithEvent.get(i).setEventDTOS(FXCollections.observableArrayList(eventForActivity));
        }

        return allActivityWithEvent;
    }

    @Override
    public boolean addActivityWithStudentAndEvent(ActivityDTO activity) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            if (activityImpl.save(new Activity(activity.getaName(), activity.getTID()))) {
                int AID = activityImpl.getIncrementIndex();
                ObservableList<EventDTO> allEvent = activity.getEventDTOS();

                for (EventDTO oneEvent : allEvent) {
                    if (!eventImpl.saveWithoutPKey(new Event(oneEvent.getEventName(), oneEvent.isGender(), AID))) {
                        return false;
                    }
                }
                ObservableList<RegistrationDTO> allReg = activity.getRegistrationDTOS();
                for (RegistrationDTO oneReg : allReg) {
                    if (!registrationImpl.saveWithoutPKey(new Registration(oneReg.getSID(), AID, oneReg.getJoinedDate()))) {
                        return false;
                    }
                }
            } else {
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
    public ArrayList<ActivityDTO> getActivityWithStudent() throws Exception {

        ArrayList<ActivityDTO> activityDTOS = getAllActivity();
        for (ActivityDTO activityDTO : activityDTOS) {

            ArrayList<CustomEntity> registrationOfThisActivity = queryDAOImpl.getRegistrationOfThisActivity(activityDTO.getAID());
            ArrayList<RegistrationDTO> registrations = new ArrayList<>();

            for (CustomEntity oneRegi : registrationOfThisActivity) {

                RegistrationDTO registrationDTO = new RegistrationDTO(oneRegi.getRID(),
                        oneRegi.getSID(),
                        activityDTO.getAID(),
                        oneRegi.getsName(),
                        oneRegi.getDOB(),
                        oneRegi.isGender(),
                        activityDTO.getaName()
                );
                registrationDTO.setActivity(activityDTO);
                registrations.add(registrationDTO);

            }
            activityDTO.setRegistrationDTOS(FXCollections.observableArrayList(registrations));
        }
        return activityDTOS;
    }

    @Override
    public ArrayList<ActivityDTO> getActivityWithStudentNotDoThis() throws Exception {
        ArrayList<ActivityDTO> allActivity = getAllActivity();
        for (ActivityDTO oneActivity : allActivity) {
            ArrayList<CustomEntity> allStudentNotDoThisActivity = queryDAOImpl.getAllStudentNotDoThisActivity(oneActivity.getAID());
            ArrayList<StudentDTO> students = new ArrayList<>();
            for (CustomEntity oneCustom : allStudentNotDoThisActivity)
                students.add(new StudentDTO(oneCustom.getStudent().getSID(), oneCustom.getStudent().getsName()));
            oneActivity.setStudentDTOS(students);
        }
        return allActivity;
    }

    @Override
    public boolean updateTeacherOfActivity(ActivityDTO updateActivity) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            if (!activityImpl.update(new Activity(updateActivity.getAID(), updateActivity.getaName(), updateActivity.getTID()))) {
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
    public ObservableList<RegistrationDTO> getRegistrationOfThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> registration = queryDAOImpl.getRegistrationOfThisActivity(AID);
        ArrayList<RegistrationDTO> registrationDTOS = new ArrayList<>();
        for (CustomEntity oneRegi : registration) {
            RegistrationDTO registrationDTO = new RegistrationDTO(oneRegi.getRID(), oneRegi.getSID(), oneRegi.getsName(), oneRegi.getJoinedDate());
            registrationDTO.setStudentClass(oneRegi.getStudentClass());
            registrationDTO.setDOB(oneRegi.getDOB());
            registrationDTO.setGender(oneRegi.isGender());
            registrationDTOS.add(registrationDTO);
        }
        return FXCollections.observableArrayList(registrationDTOS);
    }
}
