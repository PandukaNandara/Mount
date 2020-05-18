package lk.ijse.mountCalvary.entity;

import java.util.Date;

public class Registration {
    private int RID;
    private int SID;
    private int AID;
    private Date joinedDate;


    public Registration() {
    }

    public Registration(int SID, int AID, Date joinedDate) {
        this.SID = SID;
        this.AID = AID;
        this.joinedDate = joinedDate;
        System.out.println(toString());
    }

    public Registration(int RID, int SID, int AID, Date joinedDate) {
        this(SID, AID, joinedDate);
        this.RID = RID;
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
    }

    @Override
    public String toString() {
        return "Registration{" +
                "RID=" + RID +
                ", SID=" + SID +
                ", AID=" + AID +
                ", joinedDate=" + joinedDate +
                '}';
    }
}
