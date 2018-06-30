package lk.ijse.mountCalvary.model;

import lk.ijse.mountCalvary.entity.Event;
import lk.ijse.mountCalvary.entity.Gender;

import java.util.ArrayList;

public class EventDTO implements Gender {
    private int EID;
    private String eventName;
    private boolean gender;
    private int AID;
    private String genderType;
    private ActivityDTO activityDTO;
    private String activityName;
    private ArrayList<ParticipationDTO> participationDTOS;

    public EventDTO() {
    }

    public EventDTO(Event event) {
        AID = event.getAID();
        EID = event.getEID();
        eventName = event.geteName();
        gender = event.isGender();

        if (gender)
            genderType = "Male";
        else
            genderType = "Female";
    }

    public EventDTO(String eventName, boolean gender) {
        this.eventName = eventName;
        this.gender = gender;
        if (gender)
            genderType = "Male";
        else
            genderType = "Female";
        System.out.println(toString());
    }

    public EventDTO(String eventName, boolean gender, int AID) {
        this.eventName = eventName;
        this.gender = gender;
        this.AID = AID;
        if (gender)
            genderType = "Male";
        else
            genderType = "Female";
    }

    public EventDTO(int EID, String eventName, boolean gender, ActivityDTO activityDTO) {
        this.EID = EID;
        this.eventName = eventName;
        this.gender = gender;
        this.AID = activityDTO.getAID();
        this.activityDTO = activityDTO;
        if (gender)
            genderType = "Male";
        else
            genderType = "Female";
    }

    public EventDTO(int EID, String eventName, boolean gender, int AID) {
        this.EID = EID;
        this.eventName = eventName;
        this.gender = gender;
        this.AID = AID;
        if (gender)
            genderType = "Male";
        else
            genderType = "Female";
    }

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    @Override
    public String toString() {
        return eventName + "  -  " + genderType;
    }

    public ActivityDTO getActivityDTO() {
        return activityDTO;
    }

    public void setActivityDTO(ActivityDTO activityDTO) {
        this.activityDTO = activityDTO;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
