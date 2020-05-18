package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.TeacherInChargeListDTO;

public interface TeacherInChargeListBO extends SuperBO {
    ObservableList<TeacherInChargeListDTO> getTeacherInChargeListForThisCompetition(int CID) throws Exception;
}
