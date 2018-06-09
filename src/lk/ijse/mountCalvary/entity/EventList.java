package lk.ijse.mountCalvary.entity;

import java.util.ArrayList;

public class EventList {
    private int ELID;
    private int CID;
    private int EID;
    private int GID;

    //For Custom Entity
    private ArrayList<Participation> participations;
    private Event event;
    private AgeGroup ageGroup;

    public EventList() {
    }

    public EventList(int CID, int EID, int GID) {
        this.CID = CID;
        this.EID = EID;
        this.GID = GID;
    }

    public EventList(int ELID, int CID, int EID, int GID) {
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

    public ArrayList<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(ArrayList<Participation> participations) {
        this.participations = participations;
    }

    @Override
    public String toString() {
        return "EventList{" +
                "ELID=" + ELID +
                ", CID=" + CID +
                ", EID=" + EID +
                ", GID=" + GID +
                '}';
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }
}
