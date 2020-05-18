package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.CompetitionDTO;

public interface CompetitionBO extends SuperBO {
    boolean addCompetitionWithTeacherInCharge(CompetitionDTO comp) throws Exception;

    ObservableList<CompetitionDTO> getCompetitionWithEventList() throws Exception;

    ObservableList<CompetitionDTO> getAllCompetitionWithParticipation() throws Exception;

    boolean deleteCompetition(int CID) throws Exception;

    ObservableList<CompetitionDTO> getAllCompetition() throws Exception;

    CompetitionDTO getCompetitionDetail(int CID) throws Exception;
}
