package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ActivityBO extends SuperBO {
    ArrayList<ActivityDTO> getAllActivity() throws Exception;

    ArrayList<ActivityDTO> getAllActivityWithEvent()throws Exception;

    boolean addActivityWithStudentAndEvent(ActivityDTO activity) throws Exception;

    ArrayList<ActivityDTO> getActivityWithStudent() throws Exception;

    ArrayList<ActivityDTO> getActivityWithStudentNotDoThis() throws Exception;

    boolean updateTeacherOfActivity(ActivityDTO updateActivity) throws SQLException, IOException, ClassNotFoundException, Exception;

    ObservableList<RegistrationDTO> getRegistrationOfThisActivity(int AID) throws Exception;
}
