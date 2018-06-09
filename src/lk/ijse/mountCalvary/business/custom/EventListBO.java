package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.EventListDTO;

import java.util.ArrayList;

public interface EventListBO extends SuperBO {
    boolean addAllEventList(ArrayList<EventListDTO> allEvent) throws Exception;

    ObservableList<EventListDTO> getEventListForThisCompetition(int CID) throws Exception;
}
