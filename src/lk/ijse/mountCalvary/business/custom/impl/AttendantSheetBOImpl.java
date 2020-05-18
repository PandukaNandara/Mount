package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.AttendantSheetBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.AttendantSheetDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.AttendantSheet;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.model.AttendantSheetDTO;

import java.sql.Connection;
import java.util.ArrayList;

public final class AttendantSheetBOImpl implements AttendantSheetBO {
    private AttendantSheetDAO attendantSheetDAOImpl;
    private QueryDAO queryDAOImpl;

    private Connection conn;

    public AttendantSheetBOImpl() {
        attendantSheetDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ATTENDANT_SHEET);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public boolean saveAllAttendantSheet(ObservableList<AttendantSheetDTO> attendantSheetDTOS) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            for (AttendantSheetDTO attendantSheetDTO : attendantSheetDTOS) {
                if (!attendantSheetDAOImpl.saveWithoutPKey(new AttendantSheet(
                        attendantSheetDTO.getRID(),
                        attendantSheetDTO.getDate(),
                        attendantSheetDTO.getTID())
                ))
                    return false;
            }
            conn.commit();
            return true;

        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<AttendantSheetDTO> getAttendanceSheetForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> attendanceSheetForThisStudent = queryDAOImpl.getAttendanceSheetForThisStudent(SID);
        ArrayList<AttendantSheetDTO> attendantSheetDTOS = new ArrayList<>();
        for (CustomEntity oneAttendance : attendanceSheetForThisStudent) {
            attendantSheetDTOS.add(new AttendantSheetDTO(
                            oneAttendance.getATID(),
                            oneAttendance.getRID(),
                            oneAttendance.getDate(),
                            oneAttendance.getAID(),
                            oneAttendance.getaName(),
                            oneAttendance.getTID(),
                            oneAttendance.gettName()
                    )
            );
        }
        return FXCollections.observableArrayList(attendantSheetDTOS);
    }

    @Override
    public ObservableList<AttendantSheetDTO> getAttendanceSheetForThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> allAttendanceForThisActitivy = queryDAOImpl.getAllAttendanceForThisActivity(AID);
        ArrayList<AttendantSheetDTO> attendantSheetDTOS = new ArrayList<>();
        for (CustomEntity oneAtt : allAttendanceForThisActitivy) {
            attendantSheetDTOS.add(new AttendantSheetDTO(
                            oneAtt.getATID(),
                            oneAtt.getRID(),
                            oneAtt.getDate(),
                            oneAtt.getSID(),
                            oneAtt.getTID(),
                            oneAtt.getsName(),
                            oneAtt.gettName()
                    )
            );
        }
        return FXCollections.observableArrayList(attendantSheetDTOS);
    }
}
