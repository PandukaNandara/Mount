package lk.ijse.mountCalvary.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.entity.EventInterface;
import lk.ijse.mountCalvary.entity.EventList;
import lk.ijse.mountCalvary.entity.Participation;

import java.util.ArrayList;

public class EventListDTO implements EventInterface {
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
    private int gender;

    private int AID;
    private String activityName = "?";

    private ObservableList<ParticipationDTO> participationDTOS;


    public EventListDTO() {
    }

    public EventListDTO(EventList eventList) {
        this(eventList.getELID(), eventList.getCID(), eventList.getEID(), eventList.getGID());
        eventDTO = new EventDTO(eventList.getEvent());
        eventName = eventDTO.getEventName();
        activityName = eventDTO.getActivityName();
        setGender(eventDTO.getGender());
        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();
        if (eventList.getParticipations() != null) {
            for (Participation participation : eventList.getParticipations()) {
                ParticipationDTO participationDTO = new ParticipationDTO(participation);
                participationDTO.setEventName(eventName);
                participationDTOS.add(participationDTO);
            }
            this.participationDTOS = FXCollections.observableArrayList(participationDTOS);
        }
    }

    public EventListDTO(EventDTO eventDTO, CompetitionDTO competitionDTO, AgeGroupDTO ageGroupDTO) {
        setEventDTO(eventDTO);
        this.eventDTO = eventDTO;
        this.competitionDTO = competitionDTO;
        this.ageGroupDTO = ageGroupDTO;
        this.CID = competitionDTO.getCID();
        this.GID = ageGroupDTO.getGID();
        activityName = eventDTO.getActivityName();
        setGender(eventDTO.getGender());
    }

    public EventListDTO(int ELID, EventDTO eventDTO, CompetitionDTO competitionDTO, AgeGroupDTO ageGroupDTO) {
        this(ELID, competitionDTO.getCID(), eventDTO.getEID(), ageGroupDTO.getGID());
        this.eventDTO = eventDTO;
        this.competitionDTO = competitionDTO;
        this.ageGroupDTO = ageGroupDTO;
        activityName = eventDTO.getActivityName();
        setGender(eventDTO.getGender());
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
        setGender(eventDTO.getGender());
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
        setGender(eventDTO.getGender());

    }

    public EventListDTO(int ELID, int CID, int EID, int GID) {
        this.ELID = ELID;
        this.CID = CID;
        this.EID = EID;
        this.GID = GID;
    }


    public AgeGroupDTO getAgeGroupDTO() {
        this.GID = ageGroupDTO.getGID();
        return ageGroupDTO;
    }

    public void setAgeGroupDTO(AgeGroupDTO ageGroupDTO) {
        this.ageGroupDTO = ageGroupDTO;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public void setEventDTO(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
        this.EID = eventDTO.getEID();
        this.activityName = eventDTO.getActivityName();
        this.EID = eventDTO.getEID();
        setGender(eventDTO.getGender());
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

    public void setParticipationDTOS(ArrayList<Participation> participations) {
        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();
        for (Participation oneParticipation : participations)
            participationDTOS.add(new ParticipationDTO(oneParticipation));
        this.participationDTOS = FXCollections.observableArrayList(participationDTOS);
    }

    public void setParticipationDTOS(ObservableList<ParticipationDTO> participationDTOS) {
        this.participationDTOS = participationDTOS;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
        switch (gender) {
            case MALE:
                this.genderType = "Male";
                break;
            case FEMALE:
                this.genderType = "Female";
                break;
            case MIXED:
                this.genderType = "Mixed";
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

}
