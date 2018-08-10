package lk.ijse.mountCalvary.entity;

public class TelNo {
    private int telID;
    private String telNo;
    private int SID;

    public TelNo() {
    }

    public TelNo(String telNo, int SID) {
        this.telNo = telNo;
        this.SID = SID;
    }

    public TelNo(int telID, String telNo, int SID) {
        this(telNo, SID);
        this.telID = telID;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
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
