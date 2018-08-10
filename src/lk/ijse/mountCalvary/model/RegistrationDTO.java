package lk.ijse.mountCalvary.model;

import lk.ijse.mountCalvary.entity.EventInterface;
import lk.ijse.mountCalvary.entity.Gender;

import java.util.Date;

public class RegistrationDTO implements Gender, SearchProvider {
    private int RID;
    private int SID;
    private int AID;
    private ActivityDTO activity;
    private Date DOB;
    private int age;

    private Date joinedDate;
    private String joinedDate_string;

    private StudentDTO studentDTO;
    private String studentName;
    private String studentClass;
    private String activityName;
    private boolean gender;

    private boolean newOne;

    public RegistrationDTO() {
    }

    public RegistrationDTO(int RID, int SID, int AID, String studentName, String activityName) {
        this.RID = RID;
        this.SID = SID;
        this.AID = AID;
        this.studentName = studentName;
        this.activityName = activityName;
    }

    public RegistrationDTO(int RID, int SID, int AID, Date joinedDate, String activityName) {
        this.RID = RID;
        this.SID = SID;
        this.AID = AID;
        setJoinedDate(joinedDate);
        this.activityName = activityName;
    }

    public RegistrationDTO(int RID, int SID, String studentName) {
        this.RID = RID;
        this.SID = SID;
        this.studentName = studentName;
    }

    public RegistrationDTO(int RID, int SID, String studentName, Date joinedDate) {
        this.RID = RID;
        this.SID = SID;
        this.studentName = studentName;
        setJoinedDate(joinedDate);
    }

    public RegistrationDTO(int RID, int SID, int AID, String studentName, Date DOB, boolean gender, String activityName) {
        this.RID = RID;
        this.SID = SID;
        this.AID = AID;
        this.DOB = DOB;
        this.studentName = studentName;
        this.activityName = activityName;
        this.gender = gender;
    }

    public RegistrationDTO(StudentDTO studentDTO, Date joinedDate) {
        this.joinedDate = joinedDate;
        this.studentDTO = studentDTO;
        setSID(studentDTO.getSID());
        this.studentName = studentDTO.getSName();
    }

    public RegistrationDTO(StudentDTO studentDTO, int AID, Date joinedDate) {
        this.AID = AID;
        setJoinedDate(joinedDate);
        this.studentDTO = studentDTO;
        this.studentName = studentDTO.getSName();
        SID = studentDTO.getSID();
    }

    public RegistrationDTO(int SID, ActivityDTO activity, Date joinedDate) {
        this.SID = SID;
        this.activity = activity;
        this.joinedDate = joinedDate;
        setAID(activity.getAID());
    }

    public RegistrationDTO(int SID, int AID, Date joinedDate) {
        this.SID = SID;
        this.AID = AID;
        setJoinedDate(joinedDate);
    }

    public RegistrationDTO(int RID, int SID, int AID, Date joinedDate) {
        this.RID = RID;
        this.SID = SID;
        this.AID = AID;
        setJoinedDate(joinedDate);
    }

    public boolean isDeserveForEvent(int eventType) {
        return (gender == MALE)
                ? (eventType == EventInterface.MALE || eventType == EventInterface.MIXED)
                : (eventType == EventInterface.FEMALE || eventType == EventInterface.MIXED);
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
        joinedDate_string = joinedDate.toString();
    }

    @Override
    public String toString() {
        return studentName;
    }

    public ActivityDTO getActivity() {
        return activity;
    }

    public void setActivity(ActivityDTO activity) {
        this.activity = activity;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getJoinedDate_string() {
        return joinedDate_string;
    }

    public void setJoinedDate_string(String joinedDate_string) {
        this.joinedDate_string = joinedDate_string;
    }

    @Override
    public int getID() {
        return SID;
    }

    @Override
    public String getName() {
        return studentName;
    }

    public boolean isNewOne() {
        return newOne;
    }

    public void setNewOne(boolean newOne) {
        this.newOne = newOne;
    }
}
