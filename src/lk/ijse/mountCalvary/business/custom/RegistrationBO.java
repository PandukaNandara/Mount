package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;

public interface RegistrationBO extends SuperBO{
    boolean addAllRegistration(ObservableList<RegistrationDTO> rgList) throws Exception;

    ObservableList<RegistrationDTO> getRegistrationForThisStudent(int SID) throws Exception;

    ObservableList<ActivityDTO> getActivityListForThisStudent(int SID) throws Exception;
}
