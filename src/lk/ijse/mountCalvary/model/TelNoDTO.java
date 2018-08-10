package lk.ijse.mountCalvary.model;

public class TelNoDTO {
    private int telID;
    private String telNo;
    private int SID;
    private String oldTelNo;

    public TelNoDTO() {
    }

    public TelNoDTO(int telID, String telNo, int SID, String oldTelNo) {
        this.telID = telID;
        this.telNo = telNo;
        this.SID = SID;
        this.oldTelNo = oldTelNo;
    }


    public TelNoDTO(int telID, String telNo, int SID) {
        this.telID = telID;
        this.telNo = telNo;
        this.SID = SID;
    }

    public TelNoDTO(String telNo, int SID) {
        this.telNo = telNo;
        this.SID = SID;
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

    public String getOldTelNo() {
        return oldTelNo;
    }

    public void setOldTelNo(String oldTelNo) {
        this.oldTelNo = oldTelNo;
    }
}
