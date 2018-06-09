package lk.ijse.mountCalvary.model;

public class TeacherDTO {
    private int TID;
    private String tName;

    public TeacherDTO() {
    }

    public TeacherDTO(String tName) {
        this.tName = tName;
    }

    public TeacherDTO(int TID) {
        this.TID = TID;
    }

    public TeacherDTO(int TID, String tName) {
        this.TID = TID;
        this.tName = tName;
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
        return tName;
    }
}
