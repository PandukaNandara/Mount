package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.PhysicalTestDTO;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 5:48 AM
 */
public interface PhysicalTestBO extends SuperBO {
    boolean addNewPhysicalTest(PhysicalTestDTO dto) throws Exception;

    boolean updatePhysicalTest(PhysicalTestDTO dto) throws Exception;

    ObservableList<PhysicalTestDTO> getPhysicalTestForThisClassAndTerm(int TERM_ID, String class_) throws Exception;

    ObservableList<PhysicalTestDTO> getPhysicalTestForThisTerm(int TERM_ID) throws Exception;

    ObservableList<PhysicalTestDTO> getPhysicalTestForThisStudent(int sid) throws Exception;
}
