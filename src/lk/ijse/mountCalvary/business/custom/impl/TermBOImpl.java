package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.TermBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.TermDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.Term;
import lk.ijse.mountCalvary.model.TermDTO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 5:49 AM
 */
public final class TermBOImpl implements TermBO {

    private TermDAO termDAOImpl;

    public TermBOImpl() {
        termDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TERM);
    }

    @Override
    public boolean saveTerm(TermDTO termDTO) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
        try {
            con.setAutoCommit(false);
            if (termDAOImpl.saveWithoutPKey(new Term(termDTO.getTermName(), termDTO.getYear(), termDTO.getTermNo()))) {
                con.commit();
                return true;
            } else {
                return false;
            }
        } finally {
            con.rollback();
            con.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<TermDTO> getAllTerms() throws Exception {
        ArrayList<Term> terms = termDAOImpl.getAll();
        ArrayList<TermDTO> termDTOS = new ArrayList<>();
        for (Term one : terms) {
            termDTOS.add(new TermDTO(one.getTERM_ID(), one.getTermName(), one.getYear(), one.getTermNo()));
        }
        return FXCollections.observableArrayList(termDTOS);
    }
}
