package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.TelNo;

import java.util.ArrayList;

public interface TelNoDAO extends CrudDAO<TelNo, Integer> {
    boolean saveWithoutPKey(TelNo tel) throws Exception;

    ArrayList<TelNo> getTelNosForThisStudent(int SID) throws Exception;
}
