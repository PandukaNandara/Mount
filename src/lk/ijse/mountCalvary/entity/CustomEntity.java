package lk.ijse.mountCalvary.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CustomEntity {

    private Student student;
    private int RID;

    private int SID;
    private String sName;
    private String studentClass;

    private Date joinedDate;
    private int ATID;
    private Date date;

    private int PID;
    private int CID;
    private String cName;
    private String result;
    private String performance;

    private String eName;

    private int GID;
    private String group_name;

    private int AID;
    private String aName;  //Activity Name

    private int ELID;

    private int TID;
    private String tName;

    private int TINCID;

    private Date DOB;

    //This gender is user to represent student gender
    private boolean gender;

    private int PAYID;
    private BigDecimal fee;
    private int month;
    private int year;

    private Competition competition;

    private ArrayList<EventList> eventLists;


    public CustomEntity() {
    }

    public CustomEntity(int SID, int RID, int AID, String aName) {
        this.SID = SID;
        this.RID = RID;
        this.AID = AID;
        this.aName = aName;
    }

    public CustomEntity(int ATID, int RID, Date date, int AID, String aName, int TID, String tName) {
        this.RID = RID;
        this.ATID = ATID;
        this.date = date;
        this.AID = AID;
        this.TID = TID;
        this.aName = aName;
        this.tName = tName;
    }

    public CustomEntity(int PAYID, int RID, BigDecimal fee, int month, int year, int AID, String aName) {
        this.RID = RID;
        this.AID = AID;
        this.aName = aName;
        this.PAYID = PAYID;
        this.fee = fee;
        this.month = month;
        this.year = year;
    }

    public CustomEntity(int CID, String cName, Date date, int AID, String aName, String eName, String result, String performance) {
        this.date = date;
        this.CID = CID;
        this.cName = cName;
        this.result = result;
        this.performance = performance;
        this.eName = eName;
        this.AID = AID;
        this.aName = aName;
    }

    public CustomEntity(int ATID, int RID, int SID, String sName, Date date, int TID, String tName) {
        this.RID = RID;
        this.SID = SID;
        this.sName = sName;
        this.ATID = ATID;
        this.date = date;
        this.TID = TID;
        this.tName = tName;
    }

    public CustomEntity(int RID, int SID, int AID, Date joinedDate, String aName) {
        this.RID = RID;
        this.SID = SID;
        this.AID = AID;
        this.joinedDate = joinedDate;
        this.aName = aName;
    }

    public CustomEntity(Competition competition, ArrayList<EventList> eventLists) {
        this.competition = competition;
        this.eventLists = eventLists;
    }

    public CustomEntity(int RID, int SID, String sName) {
        this.RID = RID;
        this.SID = SID;
        this.sName = sName;
    }

    public CustomEntity(int RID, int SID, String sName, boolean gender, Date DOB) {
        this.RID = RID;
        this.SID = SID;
        this.sName = sName;
        this.DOB = DOB;
        this.gender = gender;
    }

    public CustomEntity(Student student) {
        this.student = student;
    }

    public CustomEntity(int payid, int rid, int sid, String sName, BigDecimal fee, int month, int year) {
        setPAYID(payid);
        setRID(rid);
        setSID(sid);
        setsName(sName);
        setFee(fee);
        setMonth(month);
        setYear(year);
    }

    public CustomEntity(int CID, String cName, Date date) {
        setCID(CID);
        setcName(cName);
        setDate(date);
    }

    public CustomEntity(int pid, int sid, int rid, String sName, String result, String performance, int gid, String group_name, int elid) {
        setPID(pid);
        setSID(sid);
        setRID(rid);
        setsName(sName);
        setResult(result);
        setPerformance(performance);
        setGID(gid);
        setGroup_name(group_name);
        setELID(elid);
    }


    public Student getStudent() {
        return student;

    }

    public void setStudent(Student student) {
        this.student = student;

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

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public ArrayList<EventList> getEventLists() {
        return eventLists;
    }

    public void setEventLists(ArrayList<EventList> eventLists) {
        this.eventLists = eventLists;
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

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
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
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getATID() {
        return ATID;
    }

    public void setATID(int ATID) {
        this.ATID = ATID;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getPAYID() {
        return PAYID;
    }

    public void setPAYID(int PAYID) {
        this.PAYID = PAYID;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getELID() {
        return ELID;
    }

    public void setELID(int ELID) {
        this.ELID = ELID;
    }

    public int getTINCID() {
        return TINCID;
    }

    public void setTINCID(int TINCID) {
        this.TINCID = TINCID;
    }
}
