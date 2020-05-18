package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.TermDTO;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 5:48 AM
 */
public interface TermBO extends SuperBO {
    boolean saveTerm(TermDTO termDTO) throws Exception;

    ObservableList<TermDTO> getAllTerms() throws Exception;
}
