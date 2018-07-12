package lk.ijse.mountCalvary.model;

import lk.ijse.mountCalvary.entity.Event;
import lk.ijse.mountCalvary.entity.EventInterface;

import java.util.ArrayList;

public class EventDTO implements EventInterface {
    private int EID;
    private String eventName;
    private int gender;
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
        setGender(event.getGender());
    }

    public EventDTO(String eventName, int gender) {
        this.eventName = eventName;
        setGender(gender);
    }

    public EventDTO(String eventName, int gender, int AID) {
        this(eventName, gender);
        this.AID = AID;
    }

    public EventDTO(int EID, String eventName, int gender, ActivityDTO activityDTO) {
        this(eventName, gender, activityDTO.getAID());
        this.EID = EID;
        this.activityDTO = activityDTO;
    }

    public EventDTO(int EID, String eventName, int gender, int AID) {
        this(eventName, gender, AID);
        this.EID = EID;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
        switch (gender) {
            case MALE:
                genderType = "Male";
                break;
            case FEMALE:
                genderType = "Female";
                break;
            case MIXED:
                genderType = "Mixed";
                break;
        }
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
