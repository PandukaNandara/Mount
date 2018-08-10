package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Term;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 2:05 AM
 */
public interface TermDAO extends CrudDAO<Term, Integer> {
    boolean saveWithoutPKey(Term entity) throws Exception;

    ArrayList<Integer> getDistinctYears() throws Exception;
}
