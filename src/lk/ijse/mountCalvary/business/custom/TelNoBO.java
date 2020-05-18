package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.TelNoDTO;

public interface TelNoBO extends SuperBO {
    boolean addWithoutPKey(TelNoDTO telNoDTO) throws Exception;

    ObservableList<TelNoDTO> getTelNosForThisStudent(int sid) throws Exception;
}
