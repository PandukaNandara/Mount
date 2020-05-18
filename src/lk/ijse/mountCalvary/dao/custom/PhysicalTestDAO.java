package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.PhysicalTest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 2:24 AM
 */
public interface PhysicalTestDAO extends CrudDAO<PhysicalTest, Integer> {
    boolean saveWithoutPKey(PhysicalTest entity) throws Exception;
}
