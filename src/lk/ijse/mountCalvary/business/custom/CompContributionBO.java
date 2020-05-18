package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.CompContributionDTO;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:30 AM
 */
public interface CompContributionBO extends SuperBO {
    boolean addAllContribution(ObservableList<CompContributionDTO> compContributionDTOS) throws Exception;

    ObservableList<CompContributionDTO> getContributionForThisCompetition(int CID) throws Exception;

    ObservableList<CompContributionDTO> getContributionForThisStudent(int SID) throws Exception;
}
