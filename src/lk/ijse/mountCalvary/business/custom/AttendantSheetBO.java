package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.AttendantSheetDTO;

public interface AttendantSheetBO extends SuperBO {
    boolean saveAllAttendantSheet(ObservableList<AttendantSheetDTO> attendantSheetDTOS) throws Exception;

    ObservableList<AttendantSheetDTO> getAttendanceSheetForThisStudent(int SID) throws Exception;

    ObservableList<AttendantSheetDTO> getAttendanceSheetForThisActivity(int AID) throws Exception;
}
