package lk.ijse.mountCalvary.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ActivityDTO {
    private int AID;
    private String aName;
    private int TID;
    private ObservableList<RegistrationDTO> registrationDTOS;
    private ObservableList<EventDTO> eventDTOS;
    private ObservableList<StudentDTO> studentDTOS;

    public ActivityDTO() {
    }

    public ActivityDTO(int AID, String aName, ObservableList<RegistrationDTO> registrationDTOS) {
        this.AID = AID;
        this.aName = aName;
        this.registrationDTOS = registrationDTOS;
        this.studentDTOS = studentDTOS;
    }

    public ActivityDTO(String aName, int TID, ObservableList<RegistrationDTO> registrationDTOS, ObservableList<EventDTO> eventDTOS) {

        this.aName = aName;
        this.TID = TID;
        this.registrationDTOS = registrationDTOS;
        this.eventDTOS = eventDTOS;
    }

    public ActivityDTO(int AID, String aName, int TID, ObservableList<EventDTO> eventDTOS) {
        this.AID = AID;
        this.aName = aName;
        this.TID = TID;
        this.eventDTOS = eventDTOS;
    }

    public ActivityDTO(int AID, String aName) {
        this.AID = AID;
        this.aName = aName;
    }

    public ActivityDTO(String aName, int TID) {
        this.aName = aName;
        this.TID = TID;
    }

    public ActivityDTO(int AID, String aName, int TID) {
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
        return aName;
    }

    public ObservableList<EventDTO> getEventDTOS() {
        return eventDTOS;
    }

    public void setEventDTOS(ObservableList<EventDTO> eventDTOS) {
        this.eventDTOS = eventDTOS;
    }

    public ObservableList<RegistrationDTO> getRegistrationDTOS() {
        return registrationDTOS;
    }

    public void setRegistrationDTOS(ObservableList<RegistrationDTO> registrationDTOS) {
        this.registrationDTOS = registrationDTOS;
    }

    public ObservableList<StudentDTO> getStudentDTOS() {
        return studentDTOS;
    }

    public void setStudentDTOS(ArrayList<StudentDTO> studentDTOS) {
        this.studentDTOS = FXCollections.observableArrayList(studentDTOS);
    }
}
