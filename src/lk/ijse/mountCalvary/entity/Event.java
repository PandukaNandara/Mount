package lk.ijse.mountCalvary.entity;

public class Event implements Gender{
    private int EID;
    private String eName;
    private boolean gender;
    private int AID;

    /// For Query DAO
    private String aName;

    public Event() {
    }

    public Event(String eName, boolean gender, int AID) {
        this.eName = eName;
        this.gender = gender;
        this.AID = AID;
    }

    public Event(int EID, String eName, boolean gender, int AID) {
        this.EID = EID;
        this.eName = eName;
        this.gender = gender;
        this.AID = AID;
    }
    public Event(int EID, String eName, boolean gender, int AID, String aName) {
        this.EID = EID;
        this.eName = eName;
        this.gender = gender;
        this.AID = AID;
        this.aName = aName;
    }
    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    @Override
    public String toString() {
        return "Event{" +
                "EID=" + EID +
                ", eName='" + eName + '\'' +
                ", gender=" + gender +
                ", AID=" + AID +
                '}';
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }
}
