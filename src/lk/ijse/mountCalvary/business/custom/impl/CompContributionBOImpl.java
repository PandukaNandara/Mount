package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.CompContributionBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.CompContributionDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CompContribution;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.model.CompContributionDTO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:32 AM
 */
public final class CompContributionBOImpl implements CompContributionBO {

    private CompContributionDAO compContributionDAOImpl;
    private QueryDAO queryDAOImpl;

    public CompContributionBOImpl() {
        compContributionDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COMP_CONTRIBUTION);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public boolean addAllContribution(ObservableList<CompContributionDTO> compContributionDTOS) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            for (CompContributionDTO one : compContributionDTOS) {
                if (!compContributionDAOImpl.saveWithoutPKey(new CompContribution(one.getSID(),
                        one.getCID(),
                        one.getContribution())))
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
    public ObservableList<CompContributionDTO> getContributionForThisCompetition(int CID) throws Exception {
        ArrayList<CustomEntity> studentList = queryDAOImpl.getContributionForThisCompetition(CID);
        ObservableList<CompContributionDTO> compContributionDTOS = FXCollections.observableArrayList();
        for (CustomEntity one : studentList) {
            compContributionDTOS.add(new CompContributionDTO(one.getCCID(),
                    one.getSID(),
                    one.getCID(),
                    one.getContribution(), one.getsName()));
        }
        return compContributionDTOS;
    }

    @Override
    public ObservableList<CompContributionDTO> getContributionForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> studentList = queryDAOImpl.getContributionForThisStudent(SID);
        ObservableList<CompContributionDTO> compContributionDTOS = FXCollections.observableArrayList();
        for (CustomEntity one : studentList) {
            compContributionDTOS.add(new CompContributionDTO(
                    one.getCCID(),
                    one.getSID(),
                    one.getcName(),
                    one.getCID(),
                    one.getContribution())
            );
        }
        return compContributionDTOS;
    }

}
