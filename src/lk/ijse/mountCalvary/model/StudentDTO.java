package lk.ijse.mountCalvary.model;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.tool.House;
import lk.ijse.mountCalvary.entity.Gender;

import java.util.Date;

public class StudentDTO implements Gender, SearchProvider {

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
    private ObservableList<TelNoDTO> telNoList;

    private boolean quit;
    private int BCID;
    private String genderType;
    private House houseName;

    private ObservableList<RegistrationDTO> allInitialActivity;

    public StudentDTO() {
    }

    public StudentDTO(int SID, String sName) {
        this.SID = SID;
        this.sName = sName;
    }

    public StudentDTO(String sName, Date DOB, boolean gender, String sClass, String fatherName, String motherName,
                      String note, int house, String address, ObservableList<TelNoDTO> telNoList) {
        this.sName = sName;
        this.DOB = DOB;
        this.gender = gender;
        this.sClass = sClass;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.note = note;
        this.house = house;
        this.address = address;
        this.telNoList = telNoList;
    }

    public StudentDTO(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName,
                      String motherName, String note, int house, String address, ObservableList<TelNoDTO> telNoList) {
        this(sName, DOB, gender, sClass, fatherName, motherName, note, house, address, telNoList);
        this.setSID(SID);
    }

    public StudentDTO(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName,
                      String motherName, String note, int house, String address, ObservableList<TelNoDTO> telNoList,
                      boolean quit, int BCID) {
        this(sName, DOB, gender, sClass, fatherName, motherName, note, house, address, telNoList, quit, BCID);
        this.setSID(SID);
    }


    public StudentDTO(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName,
                      String motherName, String note, int house, String address, ObservableList<TelNoDTO> allTelNum,
                      ObservableList<RegistrationDTO> allInitialActivity) {
        this(SID, sName, gender, DOB, sClass, fatherName, motherName, note, house, address, allTelNum);
        this.allInitialActivity = allInitialActivity;
    }

    public StudentDTO(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName,
                      String motherName, String note, int house, String address, ObservableList<TelNoDTO> allTelNum,
                      ObservableList<RegistrationDTO> allInitialActivity, boolean quit, int BCID) {
        this(SID, sName, gender, DOB, sClass, fatherName, motherName, note, house, address, allTelNum, allInitialActivity);
        this.BCID = BCID;
        this.quit = quit;
    }

    public StudentDTO(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName,
                      String motherName, String note, int house, String address) {
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

    public StudentDTO(String sName, Date DOB, boolean gender, String sClass, String fatherName, String motherName,
                      String note, int house, String address, ObservableList<TelNoDTO> telNoList,
                      boolean quit, int BCID) {
        this(sName, DOB, gender, sClass, fatherName, motherName, note, house, address, telNoList);
        this.quit = quit;
        this.BCID = BCID;
    }

    public StudentDTO(int SID, String sName, Date DOB, boolean gender, String sClass, String fatherName,
                      String motherName, String note, int house, String address, ObservableList<TelNoDTO> telNoList,
                      boolean quit, int BCID) {
        this(sName, DOB, gender, sClass, fatherName, motherName, note, house, address, telNoList, quit, BCID);
        this.SID = SID;
    }

    public StudentDTO(int SID, String sName, boolean gender, Date DOB, String sClass, String fatherName,
                      String motherName, String note, int house, String address, boolean quit, int BCID) {
        this(SID, sName, gender, DOB, sClass, fatherName, motherName, note, house, address);
        this.quit = quit;
        this.BCID = BCID;
    }

    @Override
    public String toString() {
        return sName;
    }

    public String toStringAndGetAll() {
        return "StudentDTO{" +
                "SID=" + SID +
                ", sName='" + sName + '\'' +
                ", DOB=" + DOB +
                ", gender=" + gender +
                ", sClass='" + sClass + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", note='" + note + '\'' +
                ", house=" + house +
                ", address='" + address + '\'' +
                ", telNoList=" + telNoList +
                ", quit=" + quit +
                ", BCID=" + BCID +
                ", allInitialActivity=" + allInitialActivity +
                '}';
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getSName() {
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

    public String getSClass() {
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

    public ObservableList<TelNoDTO> getTelNoList() {
        return telNoList;
    }

    public void setTelNoList(ObservableList<TelNoDTO> telNoList) {
        this.telNoList = telNoList;
    }

    public ObservableList<RegistrationDTO> getAllInitialActivity() {
        return allInitialActivity;
    }

    public void setAllInitialActivity(ObservableList<RegistrationDTO> allInitialActivity) {
        this.allInitialActivity = allInitialActivity;
    }

    @Override
    public int getID() {
        return SID;
    }

    @Override
    public String getName() {
        return sName;
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

    public String getGenderType() {
        return gender == Gender.MALE ?
                (genderType = "Male") :
                (genderType = "Female");
    }

    public House getHouseName() {
        return new House(house);
    }

}
