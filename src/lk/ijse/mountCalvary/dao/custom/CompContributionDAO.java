package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.CompContribution;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:11 AM
 */
public interface CompContributionDAO extends CrudDAO<CompContribution, Integer> {
    boolean saveWithoutPKey(CompContribution cc) throws Exception;
}
