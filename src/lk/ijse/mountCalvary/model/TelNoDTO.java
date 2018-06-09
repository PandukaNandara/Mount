package lk.ijse.mountCalvary.model;

public class TelNoDTO {
    private int telID;
    private int telNo;
    private int SID;
    private int oldTelNo;

    public TelNoDTO() {
    }

    public TelNoDTO(int telID, int telNo, int SID, int oldTelNo) {
        this.telID = telID;
        this.telNo = telNo;
        this.SID = SID;
        this.oldTelNo = oldTelNo;
    }


    public TelNoDTO(int telID, int telNo, int SID) {
        this.telID = telID;
        this.telNo = telNo;
        this.SID = SID;
    }

    public TelNoDTO(int telNo, int SID) {
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

    public int getOldTelNo() {
        return oldTelNo;
    }

    public void setOldTelNo(int oldTelNo) {
        this.oldTelNo = oldTelNo;
    }
}
