package lk.ijse.mountCalvary.entity;

import java.util.Date;

public class AttendantSheet {
    private int ATID;
    private int RID;
    private Date date;
    private int TID;

    public AttendantSheet() {

    }

    public AttendantSheet(int RID, Date date, int TID) {
        this.RID = RID;
        this.date = date;
        this.TID = TID;
    }

    public AttendantSheet(int atid, int RID, Date date, int TID) {
        this(RID, date, TID);
        ATID = atid;
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
                '}';
    }
}
