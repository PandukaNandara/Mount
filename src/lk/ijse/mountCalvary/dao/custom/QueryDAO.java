package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.SuperDAO;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.EventList;
import lk.ijse.mountCalvary.entity.Participation;

import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> getAllStudentNotDoThisActivity(int AID) throws Exception;

    ArrayList<CustomEntity> getRegistrationOfThisActivity(int AID) throws Exception;

    ArrayList<CustomEntity> getAllCompetitionWithEventList() throws Exception;

    ArrayList<EventList> getEventListForThisCompetition(int CID) throws Exception;

    ArrayList<CustomEntity> getAllCompetitionWithParticipation() throws Exception;

    ArrayList<Participation> getParticipationForThisEventList(int ELID) throws Exception;

    ArrayList<Participation> getParticipationForThisCompetition(int CID) throws Exception;

    ArrayList<CustomEntity> getRegistrationForThisStudent(int SID) throws Exception;

    ArrayList<CustomEntity> getAttendanceSheetForThisStudent(int SID) throws Exception;

    ArrayList<CustomEntity> getActivityListForThisStudent(int SID) throws Exception;

    ArrayList<CustomEntity> getCompetitionAndAchievementOfThisStudent(int SID) throws  Exception;

    ArrayList<CustomEntity> getAllAttendanceForThisActivity(int AID) throws Exception;

    ArrayList<CustomEntity> getPaymentOfThisStudent(int SID) throws Exception;

    ArrayList<CustomEntity> getPaymentOfThisActivity(int AID) throws Exception;

    ArrayList<CustomEntity> getCompetitionForThisEvent(int EID) throws Exception;

    ArrayList<CustomEntity> getParticipationForThisEventAndCompetition(int EID, int CID) throws Exception;

    ArrayList<CustomEntity> getTeacherInChargeListForThisCompetition(int cid) throws Exception;
}
