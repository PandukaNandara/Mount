package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.EventBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.EventDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.Event;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.EventDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class EventBOImpl implements EventBO {

    private EventDAO eventDAOImpl;
    private ActivityBOImpl activityBOImpl;
    private QueryDAO queryDAOImpl;
    public EventBOImpl() {
        eventDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EVENT);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);

        activityBOImpl = new ActivityBOImpl();
    }

    @Override
    public boolean addAllEvent(ObservableList<EventDTO> eventList) throws Exception{

        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try{
            for(EventDTO oneEvent : eventList){
                if(!eventDAOImpl.saveWithoutPKey(new Event(oneEvent.getEventName(), oneEvent.isGender(), oneEvent.getAID()))){
                 return false;
                }
            }
            conn.commit();
            return true;
        }finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }
    @Override
    public ObservableList<EventDTO> getAllEventWithActivity() throws Exception {
        ArrayList<ActivityDTO> allActivity = activityBOImpl.getAllActivity();
        ArrayList<EventDTO> allEventDTO = new ArrayList<>();
        ArrayList<Event> allEvent = eventDAOImpl.getAll();
        for(int i = 0; i < allEvent.size(); i++){
            Event oneEvent = allEvent.get(i);
            allEventDTO.add(new EventDTO(oneEvent.getEID(), oneEvent.geteName(), oneEvent.isGender(), oneEvent.getAID()));
            for( ActivityDTO oneActivity : allActivity){
                if(allEventDTO.get(i).getAID() == oneActivity.getAID()) {
                    allEventDTO.get(i).setActivityDTO(oneActivity);
                    break;
                }
            }
        }
        return FXCollections.observableArrayList(allEventDTO);
    }
    @Override
    public ObservableList<EventDTO> getEventForThisActivity(int AID) throws Exception {
        ArrayList<EventDTO> allEventDTO = new ArrayList<>();
        ArrayList<Event> allEvent = eventDAOImpl.getEventsForThisActivity(AID);

        for(Event oneEvent : allEvent){
            allEventDTO.add(new EventDTO(oneEvent.getEID(), oneEvent.geteName(), oneEvent.isGender(), oneEvent.getAID()));
        }
        return FXCollections.observableArrayList(allEventDTO);
    }

    @Override
    public ObservableList<CompetitionDTO> getCompetitionForThisEvent(int EID) throws Exception {
        ArrayList<CompetitionDTO> competitionDTOS = new ArrayList<>();
        ArrayList<CustomEntity> competitionForThisEvent = queryDAOImpl.getCompetitionForThisEvent(EID);
        for(CustomEntity oneCompetition : competitionForThisEvent){
            competitionDTOS.add(new CompetitionDTO(oneCompetition.getCID(), oneCompetition.getcName(),oneCompetition.getDate()));
        }
        return FXCollections.observableArrayList(competitionDTOS);
    }
}
