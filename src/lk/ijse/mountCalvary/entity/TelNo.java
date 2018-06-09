package lk.ijse.mountCalvary.entity;

public class TelNo {
    private int telID;
    private int telNo;
    private int SID;

    public TelNo() {
    }

    public TelNo(int telNo, int SID) {
        this.telNo = telNo;
        this.SID = SID;
    }

    public TelNo(int telID, int telNo, int SID) {
        this.telID = telID;
        this.telNo = telNo;
        this.SID = SID;
    }

    public int getTelNo() {
        return telNo;
    }

    public void setTelNo(int telNo) {
        this.telNo = telNo;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public int getTelID() {
        return telID;
    }

    public void setTelID(int telID) {
        this.telID = telID;
    }

    @Override
    public String toString() {
        return "TelNo{" +
                "telID=" + telID +
                ", telNo=" + telNo +
                ", SID=" + SID +
                '}';
    }
}
