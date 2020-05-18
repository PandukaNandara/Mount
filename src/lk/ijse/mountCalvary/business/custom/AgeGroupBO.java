package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.AgeGroupDTO;

public interface AgeGroupBO extends SuperBO {
    ObservableList<AgeGroupDTO> getAgeGroups() throws Exception;
}
