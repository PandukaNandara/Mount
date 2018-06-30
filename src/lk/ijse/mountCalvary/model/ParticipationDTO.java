package lk.ijse.mountCalvary.model;

import lk.ijse.mountCalvary.entity.Participation;

import java.util.Date;

public class ParticipationDTO {
    private int PID;
    private int ELID;
    private int RID;
    private String result;
    private String performance;

    private int SID;
    private String studentName;
    private Date DOB;
    private int AID;
    private String eventName;

    private int CID;
    private String competitionName;

    private Date date;
    private String date_String;
    private String activityName;

    private boolean newOne;
    private RegistrationDTO reg;

    private int GID;
    private String groupName;

    public ParticipationDTO() {
    }

    public ParticipationDTO(int ELID, int RID, String result, String performance) {
        this.ELID = ELID;
        this.RID = RID;
        this.result = result;
        this.performance = performance;
    }

    public ParticipationDTO(int ELID, RegistrationDTO reg ,String result, String performance, String eventName) {
        this.ELID = ELID;
        this.result = result;
        this.performance = performance;
        this.reg = reg;
        this.studentName = reg.getStudentName();
        this.eventName = eventName;
        this.RID = reg.getRID();
    }

    public ParticipationDTO(int pid, int ELID, int RID, String result, String performance) {
        this.PID = pid;
        this.ELID = ELID;
        this.RID = RID;
        this.result = result;
        this.performance = performance;
    }

    public ParticipationDTO(int CID, String competitionName, Date date, int AID,  String activityName, String eventName, String result, String performance) {
        this.result = result;
        this.performance = performance;
        this.AID = AID;
        this.eventName = eventName;
        this.CID = CID;
        this.competitionName = competitionName;
        setDate(date);
        this.activityName = activityName;
    }

    public ParticipationDTO(Participation participation) {
        this.PID = participation.getPID();
        this.ELID = participation.getELID();
        this.RID = participation.getRID();
        this.result = participation.getResult();
        this.performance = participation.getPerformance();
        this.SID = participation.getSID();
        this.studentName = participation.getsName();
        this.DOB = participation.getDOB();
        this.AID = participation.getAID();
        this.eventName = participation.getEventName();
    }

    public ParticipationDTO(int pid, int sid, int rid, String studentName, String result, String performance, int gid, String group_name, int elid) {
        setPID(pid);
        setSID(sid);
        setRID(rid);
        setStudentName(studentName);
        setResult(result);
        setPerformance(performance);
        setGID(gid);
        setGroupName(group_name);
        setELID(elid);
    }
    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getELID() {
        return ELID;
    }

    public void setELID(int ELID) {
        this.ELID = ELID;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "PID=" + PID +
                "Student Name=" + studentName +
                ", ELID=" + ELID +
                ", RID=" + RID +
                ", Event name=" + eventName +
                ", result='" + result + '\'' +
                ", performance='" + performance + '\'' +
                '}';
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public boolean isNewOne() {
        return newOne;
    }

    public void setAsNewOne(boolean newOne) {
        this.newOne = newOne;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        setDate_String(date.toString());
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public RegistrationDTO getReg() {
        return reg;
    }

    public void setReg(RegistrationDTO reg) {
        this.reg = reg;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDate_String() {
        return date_String;
    }

    public void setDate_String(String date_String) {
        this.date_String = date_String;
    }
}
