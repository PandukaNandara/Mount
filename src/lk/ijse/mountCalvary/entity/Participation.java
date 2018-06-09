package lk.ijse.mountCalvary.entity;

import java.util.Date;

public class Participation {
    private int PID;
    private int ELID;
    private int RID;
    private String result;
    private String performance;

    //For Custom Entity
    private int SID;
    private String sName;
    private Date DOB;
    private int AID;
    private String eventName;

    public Participation() {
    }

    public Participation(int ELID, int RID, String result, String performance) {
        this.ELID = ELID;
        this.RID = RID;
        this.result = result;
        this.performance = performance;
    }

    public Participation(int pid, int ELID, int RID, String result, String performance) {
        this.PID = pid;
        this.ELID = ELID;
        this.RID = RID;
        this.result = result;
        this.performance = performance;
    }

    public Participation(int PID, int ELID, int RID, String result, String performance, int SID, String sName, Date DOB, int AID) {
        this.PID = PID;
        this.ELID = ELID;
        this.RID = RID;
        this.result = result;
        this.performance = performance;
        this.SID = SID;
        this.sName = sName;
        this.DOB = DOB;
        this.AID = AID;
    }

    public Participation(int PID, int ELID, int RID, String result, String performance, int SID, String sName, Date DOB, int AID, String eventName) {
        this.PID = PID;
        this.ELID = ELID;
        this.RID = RID;
        this.result = result;
        this.performance = performance;
        this.SID = SID;
        this.sName = sName;
        this.DOB = DOB;
        this.AID = AID;
        this.eventName = eventName;
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

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
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

    @Override
    public String toString() {
        return "Participation{" +
                "PID=" + PID +
                ", ELID=" + ELID +
                ", RID=" + RID +
                ", result='" + result + '\'' +
                ", performance='" + performance + '\'' +
                '}';
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
