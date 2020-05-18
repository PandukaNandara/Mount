package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Participation;

public interface ParticipationDAO extends CrudDAO<Participation, Integer> {
    boolean saveWithoutPKey(Participation par) throws Exception;
}
