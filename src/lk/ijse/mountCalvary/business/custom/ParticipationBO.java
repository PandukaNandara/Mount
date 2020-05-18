package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.ParticipationDTO;

public interface ParticipationBO extends SuperBO {
    boolean addAllParticipation(ObservableList<ParticipationDTO> allParticipationDTOS) throws Exception;

    ObservableList<ParticipationDTO> getCompetitionAndAchievementOfThisStudent(int SID) throws Exception;

    ObservableList<ParticipationDTO> getParticipationForThisEventList(int ELID) throws Exception;

    ObservableList<ParticipationDTO> getParticipationForThisCompetition(int CID) throws Exception;

    ObservableList<ParticipationDTO> getParticipationForThisEventAndCompetition(int EID, int CID) throws Exception;
}
