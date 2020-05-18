package lk.ijse.mountCalvary.entity;

import java.util.Date;

public class Student implements Gender {
    private int SID;
    private String sName;
    private Date DOB;
    private boolean gender;
    private String sClass;
    private String fatherName;
    private String motherName;
    private String note;
    private int house;
    private String address;
    private boolean quit;
    private int BCID;

    public Student() {
    }

    public Student(int SID, String sName) {
        this.SID = SID;
        this.sName = sName;
    }

    public Student(String sName, boolean gender, Date DOB, String sClass, String fatherName, String motherName, String note, int house, String address) {
        this.sName = sName;
        this.DOB = DOB;
        this.gender = gender;
        this.sClass = sClass;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.note = note;
        this.house = house;
        this.address = address;
    }

    public Student(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName, String motherName, String note, int house, String address) {
        this(sName, gender, DOB, sClass, fatherName, motherName, note, house, address);
        this.SID = SID;
    }

    public Student(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName, String motherName, String note, int house, String address, boolean quit, int BCID) {
        this(sName, DOB, gender, sClass, fatherName, motherName, note, house, address, quit, BCID);
        this.SID = SID;
    }

    public Student(String sName, Date DOB, boolean gender, String sClass, String fatherName, String motherName, String note, int house, String address, boolean quit, int BCID) {
        this(sName, gender, DOB, sClass, fatherName, motherName, note, house, address);
        this.quit = quit;
        this.BCID = BCID;
    }

    public boolean isQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public int getBCID() {
        return BCID;
    }

    public void setBCID(int BCID) {
        this.BCID = BCID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "SID=" + SID +
                ", sName='" + sName + '\'' +
                ", DOB=" + DOB +
                ", gender=" + gender +
                ", sClass='" + sClass + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", note='" + note + '\'' +
                ", house='" + house + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
