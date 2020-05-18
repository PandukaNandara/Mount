package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.TelNoBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.TelNoDAO;
import lk.ijse.mountCalvary.entity.TelNo;
import lk.ijse.mountCalvary.model.TelNoDTO;

import java.util.ArrayList;

public final class TelNoBOImpl implements TelNoBO {

    private TelNoDAO telNoDAOImpl;

    public TelNoBOImpl() {
        telNoDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TELEPHONE_NO);
    }

    @Override
    public boolean addWithoutPKey(TelNoDTO telNoDTO) throws Exception {

        return telNoDAOImpl.saveWithoutPKey(new TelNo(telNoDTO.getTelNo(), telNoDTO.getSID()));
    }

    @Override
    public ObservableList<TelNoDTO> getTelNosForThisStudent(int sid) throws Exception {
        ArrayList<TelNo> forThisStudent = telNoDAOImpl.getTelNosForThisStudent(sid);
        ArrayList<TelNoDTO> telNoDTOS = new ArrayList<>();
        for (TelNo oneNumber : forThisStudent) {
            telNoDTOS.add(new TelNoDTO(oneNumber.getTelID(), oneNumber.getTelNo(), oneNumber.getSID()));
        }
        return FXCollections.observableArrayList(telNoDTOS);
    }
}
