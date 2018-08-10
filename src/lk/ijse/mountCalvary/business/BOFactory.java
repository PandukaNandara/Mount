package lk.ijse.mountCalvary.business;


import lk.ijse.mountCalvary.business.custom.impl.*;

public class BOFactory {

    private static BOFactory ourInstance = new BOFactory();

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return ourInstance;
    }

    public <T extends SuperBO> T getBO(BOType type) {
        switch (type) {
            case STUDENT:
                return (T) new StudentBOImpl();
            case ACTIVITY:
                return (T) new ActivityBOImpl();
            case LOG_IN:
                return (T) new LogInBOImpl();
            case TEL_NO:
                return (T) new TelNoBOImpl();
            case TEACHER:
                return (T) new TeacherBOImpl();
            case REGISTRATION:
                return (T) new RegistrationBOImpl();
            case EVENT:
                return (T) new EventBOImpl();
            case ATTENDANT_SHEET:
                return (T) new AttendantSheetBOImpl();
            case COMPETITION:
                return (T) new CompetitionBOImpl();
            case AGE_GROUP:
                return (T) new AgeGroupBOImpl();
            case PARTICIPATION:
                return (T) new ParticipationBOImpl();
            case EVENT_LIST:
                return (T) new EventListBOImpl();
            case PAYMENT:
                return (T) new PaymentBOImpl();
            case TEACHER_IN_CHARGE_LIST:
                return (T) new TeacherInChargeListBOImpl();
            case TERM:
                return (T) new TermBOImpl();
            case PHYSICAL_TEST:
                return (T) new PhysicalTestBOImpl();
            case COMP_CONTRIBUTION:
                return (T) new CompContributionBOImpl();
            default:
                return null;
        }
    }

    public enum BOType {
        STUDENT, LOG_IN, ACTIVITY, TEL_NO, TEACHER, REGISTRATION, EVENT, ATTENDANT_SHEET, COMPETITION, AGE_GROUP,
        EVENT_LIST, PARTICIPATION, PAYMENT, TEACHER_IN_CHARGE_LIST,
        TERM, PHYSICAL_TEST,
        COMP_CONTRIBUTION
    }

}
