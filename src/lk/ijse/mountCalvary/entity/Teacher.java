package lk.ijse.mountCalvary.entity;

public class Teacher {
    private int TID;
    private String tName;

    public Teacher() {
    }

    public Teacher(String tName) {
        this.tName = tName;
    }

    public Teacher(int TID, String tName) {
        this(tName);
        this.TID = TID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "TID=" + TID +
                ", tName='" + tName + '\'' +
                '}';
    }
}
