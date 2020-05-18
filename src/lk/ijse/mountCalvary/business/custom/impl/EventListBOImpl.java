package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.EventListBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.EventListDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.EventList;
import lk.ijse.mountCalvary.model.AgeGroupDTO;
import lk.ijse.mountCalvary.model.EventDTO;
import lk.ijse.mountCalvary.model.EventListDTO;

import java.sql.Connection;
import java.util.ArrayList;

public final class EventListBOImpl implements EventListBO {
    private EventListDAO eventListDAOImpl;
    private Connection conn;
    private QueryDAO queryDAOImpl;

    public EventListBOImpl() {
        eventListDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EVENT_LIST);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public boolean addAllEventList(ArrayList<EventListDTO> allEvent) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            for (EventListDTO oneEventListDTO : allEvent) {
                if (!eventListDAOImpl.saveWithoutPKey(new EventList(oneEventListDTO.getCID(), oneEventListDTO.getEID(), oneEventListDTO.getGID())))
                    return false;
            }

            conn.commit();
            return true;
        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<EventListDTO> getEventListForThisCompetition(int CID) throws Exception {
        ArrayList<EventList> eventLists = queryDAOImpl.getEventListForThisCompetition(CID);
        ArrayList<EventListDTO> eventListDTOS = new ArrayList<>();
        for (EventList oneEventList : eventLists) {

            EventListDTO eventListDTO = new EventListDTO(oneEventList);

            EventDTO eventDTO = new EventDTO(oneEventList.getEvent());
            eventDTO.setActivityName(oneEventList.getEvent().getaName());

            eventDTO.setAID(oneEventList.getEvent().getAID());

            eventListDTO.setEventDTO(eventDTO);

            eventListDTO.setAgeGroupDTO(new AgeGroupDTO(oneEventList.getAgeGroup()));

            eventListDTOS.add(eventListDTO);
        }
        return FXCollections.observableArrayList(eventListDTOS);
    }
}
