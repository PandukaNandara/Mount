package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.AttendantSheet;

import java.util.ArrayList;

public interface AttendantSheetDAO extends CrudDAO<AttendantSheet, Integer> {
    boolean saveWithoutPKey(AttendantSheet atts) throws Exception;

    ArrayList<Integer> getDistinctYears() throws Exception;
}
