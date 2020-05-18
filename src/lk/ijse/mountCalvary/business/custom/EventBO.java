package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.EventDTO;

public interface EventBO extends SuperBO {
    boolean addAllEvent(ObservableList<EventDTO> evenList) throws Exception;

    ObservableList<EventDTO> getAllEventWithActivity() throws Exception;

    ObservableList<EventDTO> getEventForThisActivity(int AID) throws Exception;

    ObservableList<CompetitionDTO> getCompetitionForThisEvent(int eid) throws Exception;
}
