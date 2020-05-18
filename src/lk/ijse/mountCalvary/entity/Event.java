package lk.ijse.mountCalvary.entity;

public class Event implements EventInterface {
    private int EID;
    private String eName;
    private int gender;
    private int AID;

    /// For Query DAO
    private String aName;

    public Event() {
    }

    public Event(String eName, int gender, int AID) {
        this.eName = eName;
        this.gender = gender;
        this.AID = AID;
    }

    public Event(int EID, String eName, int gender, int AID) {
        this(eName, gender, AID);
        this.EID = EID;
    }

    public Event(int EID, String eName, int gender, int AID, String aName) {
        this(EID, eName, gender, AID);
        this.aName = aName;
    }

    @Override
    public boolean isMaleEvent() {
        return gender == MALE;
    }

    @Override
    public boolean isFemaleEvent() {
        return gender == FEMALE;
    }

    @Override
    public boolean isMixedEvent() {
        return gender == MIXED;
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

    public int getGender() {
        return gender;
    }


    public void setGender(int gender) {
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
