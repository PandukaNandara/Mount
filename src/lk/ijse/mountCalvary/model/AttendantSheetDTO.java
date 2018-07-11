package lk.ijse.mountCalvary.model;

import java.util.Date;

public class AttendantSheetDTO {
    private int ATID;
    private int RID;
    private int SID;
    private RegistrationDTO registrationDTO;
    private TeacherDTO teacherDTO;

    private Date date;
    private String date_String;

    private int TID;
    private int AID;
    private String studentName;
    private String activityName;
    private String teacherName;

    public AttendantSheetDTO() {

    }

    public AttendantSheetDTO(int RID, Date date, int TID) {
        this.RID = RID;
        setDate(date);
        this.TID = TID;
    }

    public AttendantSheetDTO(int ATID, int RID, Date date,int SID, int TID, String studentName, String teacherName) {
        this.ATID = ATID;
        this.RID = RID;
        this.SID = SID;
        setDate(date);
        this.TID = TID;
        this.studentName = studentName;
        this.teacherName = teacherName;
    }

    public AttendantSheetDTO(int ATID, int RID, Date date, int AID, String activityName, int TID, String teacherName) {
        this.ATID = ATID;
        this.RID = RID;
        setDate(date);
        this.AID = AID;
        this.TID = TID;
        this.activityName = activityName;
        this.teacherName = teacherName;
    }

    public AttendantSheetDTO(RegistrationDTO registrationDTO, TeacherDTO teacherDTO, Date date) {
        this.RID = registrationDTO.getRID();
        this.registrationDTO = registrationDTO;
        this.teacherDTO = teacherDTO;
        this.TID = teacherDTO.getTID();
        setDate(date);
        this.studentName = registrationDTO.getStudentName();
        this.teacherName = teacherDTO.getTName();
        this.activityName = registrationDTO.getActivityName();
    }

    public AttendantSheetDTO(int atid, int RID, Date date, int TID) {
        ATID = atid;
        this.RID = RID;
        this.TID = TID;
        setDate(date);
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.date_String = date.toString();
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public int getATID() {
        return ATID;
    }

    public void setATID(int ATID) {
        this.ATID = ATID;
    }

    @Override
    public String toString() {
        return "AttendantSheet{" +
                "ATID=" + ATID +
                ", RID=" + RID +
                ", date=" + date +
                ", TID=" + TID +
                ", AID=" + AID +
                '}';
    }

    public String getStudentName() {
        return studentName;
    }

    public AttendantSheetDTO setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public RegistrationDTO getRegistrationDTO() {
        return registrationDTO;
    }

    public void setRegistrationDTO(RegistrationDTO registrationDTO) {
        this.registrationDTO = registrationDTO;
    }

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getDate_String() {
        return date_String;
    }

    public void setDate_String(String date_String) {
        this.date_String = date_String;
    }
}
