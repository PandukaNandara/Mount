package lk.ijse.mountCalvary.entity;

public class Activity {
    private int AID;
    private String aName;
    private int TID;

    public Activity() {
    }

    public Activity(String aName, int TID) {
        this.aName = aName;
        this.TID = TID;
    }

    public Activity(int AID, String aName, int TID) {
        this.AID = AID;
        this.aName = aName;
        this.TID = TID;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "AID=" + AID +
                ", aName='" + aName + '\'' +
                ", TID=" + TID +
                '}';
    }
}
