package lk.ijse.mountCalvary.entity;

public class TeacherInChargeList {
    private int TINCID;
    private int CID;
    private int TID;
    public TeacherInChargeList() {
    }
    public TeacherInChargeList(int CID, int TID) {
        this.CID = CID;
        this.TID = TID;
    }

    public TeacherInChargeList(int tincid, int CID, int TID) {
        TINCID = tincid;
        this.CID = CID;
        this.TID = TID;
    }

    public int getTINCID() {
        return TINCID;
    }

    public void setTINCID(int TINCID) {
        this.TINCID = TINCID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    @Override
    public String toString() {
        return "TeacherInChargeList{" +
                "TINCID=" + TINCID +
                ", CID=" + CID +
                ", TID=" + TID +
                '}';
    }
}
