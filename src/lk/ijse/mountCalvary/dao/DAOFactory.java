package lk.ijse.mountCalvary.dao;

import lk.ijse.mountCalvary.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory = new DAOFactory();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOType daoType) {
        switch (daoType) {
            case CUSTOM:
                return (T) new QueryDAOImpl();
            case ACTIVITY:
                return (T) new ActivityDAOImpl();
            case AGE_GROUP:
                return (T) new AgeGroupDAOImpl();
            case ATTENDANT_SHEET:
                return (T) new AttendandSheetDAOImpl();
            case COMPETITION:
                return (T) new CompetitionDAOImpl();
            case EVENT:
                return (T) new EventDAOImpl();
            case EVENT_LIST:
                return (T) new EventListDAOImpl();
            case PARTICIPATION:
                return (T) new ParticipationDAOImpl();
            case PAYMENT:
                return (T) new PaymentDAOImpl();
            case REGISTRATION:
                return (T) new RegistrationDAOImpl();
            case STUDENT:
                return (T) new StudentDAOImpl();
            case TEACHER:
                return (T) new TeacherDAOImpl();
            case TEACHER_IN_CHARGE_LIST:
                return (T) new TeacherInChargeDAOImpl();
            case TELEPHONE_NO:
                return (T) new TelNoDAOImpl();
            case PHYSICAL_TEST:
                return (T) new PhysicalTestDAOImpl();
            case TERM:
                return (T) new TermDAOImpl();
            case LOG_IN:
                return (T) new LogInDAOImpl();
            case COMP_CONTRIBUTION:
                return (T) new CompContributionDAOImpl();
            case MS_STUDENT:
                return (T) new MSStudentDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOType {
        ACTIVITY, AGE_GROUP, LOG_IN, ATTENDANT_SHEET, COMPETITION, EVENT, EVENT_LIST, PARTICIPATION,
        PAYMENT, REGISTRATION, STUDENT, TEACHER, TEACHER_IN_CHARGE_LIST, TELEPHONE_NO, CUSTOM,
        TERM, PHYSICAL_TEST,
        COMP_CONTRIBUTION,
        MS_STUDENT
    }


}
