package lk.ijse.mountCalvary.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

public class CompetitionDTO {
    private int CID;
    private String comName;
    private String location;
    private Date date;
    private String desc;
    private ObservableList<EventListDTO> eventListDTOS;
    private ObservableList<ParticipationDTO> participationDTOS;

    private ObservableList<TeacherInChargeListDTO> teacherInChargeList;

    public CompetitionDTO(String comName, String location, Date date, String desc, ObservableList<TeacherInChargeListDTO> teacherInChargeList) {
        this.comName = comName;
        this.location = location;
        setDate(date);
        this.desc = desc;
        this.teacherInChargeList = teacherInChargeList;
    }

    public CompetitionDTO() {

    }

    public CompetitionDTO(int CID, String comName) {
        this.CID = CID;
        this.comName = comName;
    }

    public CompetitionDTO(int CID, String comName, Date date) {
        this.CID = CID;
        this.comName = comName;
        setDate(date);
    }

    public CompetitionDTO(String comName, String location, Date date, String desc) {
        this.comName = comName;
        this.location = location;
        setDate(date);
        this.desc = desc;
    }

    public CompetitionDTO(int CID, String comName, String location, Date date, String desc) {
        this.setCID(CID);
        this.setComName(comName);
        this.setLocation(location);
        this.setDate(date);
        this.setDesc(desc);
    }

    public CompetitionDTO(int cid, String comName, String location, Date date, String desc, ArrayList<TeacherInChargeListDTO> teacherInChargeListDTOS) {
        this.CID = cid;
        this.comName = comName;
        this.location = location;
        setDate(date);
        this.desc = desc;
        this.teacherInChargeList = FXCollections.observableArrayList(teacherInChargeListDTOS);
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

//    @Override
//    public String toString() {
//        return "CompetitionDTO{" +
//                "CID=" + CID +
//                ", comName='" + comName + '\'' +
//                ", location='" + location + '\'' +
//                ", date=" + date +
//                ", desc='" + desc + '\'' +
//                ", eventListDTOS=" + eventListDTOS +
//                ", teacherInChargeList=" + teacherInChargeList +
//                '}';
//    }

    @Override
    public String toString() {
        return comName;
    }

    public ObservableList<TeacherInChargeListDTO> getTeacherInChargeList() {
        return teacherInChargeList;
    }

    public void setTeacherInChargeList(ObservableList<TeacherInChargeListDTO> teacherInChargeList) {
        this.teacherInChargeList = teacherInChargeList;
    }

    public ObservableList<EventListDTO> getEventListDTOS() {
        return eventListDTOS;
    }

    public void setEventListDTOS(ObservableList<EventListDTO> eventListDTOS) {
        this.eventListDTOS = eventListDTOS;
    }

    public ObservableList<ParticipationDTO> getParticipationDTOS() {
        //System.out.println(participationDTOS);
        return participationDTOS;
    }

    public void setParticipationDTOS(ObservableList<ParticipationDTO> participationDTOS) {
        //System.out.println(participationDTOS);
        this.participationDTOS = participationDTOS;
    }

//    public String getDate_string() {
//        return date_string;
//    }
//
//    public void setDate_string(String date_string) {
//        this.date_string = date_string;
//    }
}
