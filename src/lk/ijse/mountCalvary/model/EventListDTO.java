package lk.ijse.mountCalvary.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.entity.EventList;
import lk.ijse.mountCalvary.entity.Gender;
import lk.ijse.mountCalvary.entity.Participation;

import java.util.ArrayList;

public class EventListDTO {
    private int ELID;
    private int CID;
    private int EID;
    private int GID;
    private EventDTO eventDTO;
    private CompetitionDTO competitionDTO;
    private AgeGroupDTO ageGroupDTO;
    private boolean isNewEventList;
    private String eventName;
    private String genderType = "???";
    private boolean gender;

    private int AID;
    private String activityName = "?";

    private ObservableList<ParticipationDTO> participationDTOS;


    public EventListDTO() {
    }

    public EventListDTO(EventList eventList) {
       // System.out.println("Pass");
        ELID = eventList.getELID();
        CID = eventList.getCID();
        GID = eventList.getGID();
        EID = eventList.getEID();

        eventDTO = new EventDTO(eventList.getEvent());
        eventName = eventDTO.getEventName();
        activityName = eventDTO.getActivityName();
        if (eventDTO.isGender() == Gender.MALE) {
            gender = Gender.MALE;
            genderType = "Male";
        } else {
            gender = Gender.FEMALE;
            genderType = "Female";
        }
        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();
        if(eventList.getParticipations() != null) {
            for (Participation participation : eventList.getParticipations()) {
                ParticipationDTO participationDTO = new ParticipationDTO(participation);
                participationDTO.setEventName(eventName);
                participationDTOS.add(participationDTO);
            }
            this.participationDTOS = FXCollections.observableArrayList(participationDTOS);
        }
    }

    public EventListDTO(EventDTO eventDTO, CompetitionDTO competitionDTO, AgeGroupDTO ageGroupDTO) {
        this.eventDTO = eventDTO;
        this.competitionDTO = competitionDTO;
        this.ageGroupDTO = ageGroupDTO;
        this.CID = competitionDTO.getCID();
        this.EID = eventDTO.getEID();
        this.GID = ageGroupDTO.getGID();
        activityName = eventDTO.getActivityName();
        if (eventDTO.isGender() == Gender.MALE) {
            gender = Gender.MALE;
            genderType = "Male";
        } else {
            gender = Gender.FEMALE;
            genderType = "Female";
        }
    }

    public EventListDTO(int ELID, EventDTO eventDTO, CompetitionDTO competitionDTO, AgeGroupDTO ageGroupDTO) {
        this.ELID = ELID;
        this.eventDTO = eventDTO;
        this.competitionDTO = competitionDTO;
        this.ageGroupDTO = ageGroupDTO;
        this.CID = competitionDTO.getCID();
        this.EID = eventDTO.getEID();
        this.GID = ageGroupDTO.getGID();
        activityName = eventDTO.getActivityName();
        if (eventDTO.isGender() == Gender.MALE) {
            gender = Gender.MALE;
            genderType = "Male";
        } else {
            gender = Gender.FEMALE;
            genderType = "Female";
        }
    }

    public EventListDTO(int CID, int EID, int GID) {
        this.CID = CID;
        this.EID = EID;
        this.GID = GID;
    }

    public EventListDTO(int CID, EventDTO eventDTO, int GID) {
        this.CID = CID;
        this.GID = GID;
        this.EID = eventDTO.getEID();
        this.eventDTO = eventDTO;
        activityName = eventDTO.getActivityName();
        if (eventDTO.isGender() == Gender.MALE) {
            gender = Gender.MALE;
            genderType = "Male";
        } else {
            gender = Gender.FEMALE;
            genderType = "Female";
        }
    }

    public EventListDTO(int ELID, int CID, int GID, EventDTO eventDTO, CompetitionDTO competitionDTO) {
        this.ELID = ELID;
        this.CID = CID;
        this.GID = GID;
        this.eventDTO = eventDTO;
        this.competitionDTO = competitionDTO;
        this.EID = eventDTO.getEID();
        activityName = eventDTO.getActivityName();
        this.CID = competitionDTO.getCID();
        if (eventDTO.isGender() == Gender.MALE) {
            gender = Gender.MALE;
            genderType = "Male";
        } else {
            gender = Gender.FEMALE;
            genderType = "Female";
        }
    }

    public EventListDTO(int ELID, int CID, int EID, int GID) {
        this.ELID = ELID;
        this.CID = CID;
        this.EID = EID;
        this.GID = GID;
    }

    public int getELID() {
        return ELID;
    }

    public void setELID(int ELID) {
        this.ELID = ELID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public AgeGroupDTO getAgeGroupDTO() {
        this.GID = ageGroupDTO.getGID();
        return ageGroupDTO;
    }

    public void setAgeGroupDTO(AgeGroupDTO ageGroupDTO) {
        this.ageGroupDTO = ageGroupDTO;
    }

    @Override
    public String toString() {
        return "EventListDTO{" +
                "ELID=" + ELID +
                ", CID=" + CID +
                ", EID=" + EID +
                ", GID=" + GID +
                ", Activity Name=" + activityName +
                ", eventDTO=" + eventDTO +
                ", ageGroupDTO=" + ageGroupDTO +
                ", isNewEventList = " + isNewEventList +
                '}';
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public void setEventDTO(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
        this.activityName = eventDTO.getActivityName();
        this.EID = eventDTO.getEID();
        this.AID = eventDTO.getAID();
    }

    public CompetitionDTO getCompetitionDTO() {
        return competitionDTO;
    }

    public void setCompetitionDTO(CompetitionDTO competitionDTO) {
        this.CID = competitionDTO.getCID();
        this.competitionDTO = competitionDTO;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public boolean isNewEventList() {
        return isNewEventList;
    }

    public void setNewEventList(boolean newEventList) {
        isNewEventList = newEventList;

    }

    public ObservableList<ParticipationDTO> getParticipationDTOS() {
        return participationDTOS;
    }

    public void setParticipationDTOS(ObservableList<ParticipationDTO> participationDTOS) {
        this.participationDTOS = participationDTOS;
    }

    public void setParticipationDTOS(ArrayList<Participation> participations) {
        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();
        for(Participation oneParticipation : participations)
            participationDTOS.add(new ParticipationDTO(oneParticipation));
        this.participationDTOS = FXCollections.observableArrayList(participationDTOS);
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
