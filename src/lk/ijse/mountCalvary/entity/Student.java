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
    private String house;
    private String address;
    public Student() {
    }

    public Student(int SID, String sName) {
        this.SID = SID;
        this.sName = sName;
    }

    public Student(String sName, boolean gender, Date DOB, String sClass, String fatherName, String motherName, String note, String house, String address) {
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

    public Student(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName, String motherName, String note, String house, String address) {
        this.house = house;
        this.gender = gender;
        this.address = address;
        this.setSID(SID);
        this.setsName(sName);
        this.setDOB(DOB);
        this.setsClass(sClass);
        this.setFatherName(fatherName);
        this.setMotherName(motherName);
        this.setNote(note);
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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
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
