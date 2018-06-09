package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.AgeGroupBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.AgeGroupDAO;
import lk.ijse.mountCalvary.entity.AgeGroup;
import lk.ijse.mountCalvary.model.AgeGroupDTO;

import java.util.ArrayList;

public class AgeGroupBOImpl implements AgeGroupBO {
    private AgeGroupDAO ageGroupDAOImpl;
    public AgeGroupBOImpl(){
        ageGroupDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.AGE_GROUP);
    }
    @Override
    public ObservableList<AgeGroupDTO> getAgeGroups() throws Exception {
        ArrayList<AgeGroup> allAgeGroups = ageGroupDAOImpl.getAll();
        ArrayList<AgeGroupDTO> allAgeGroupDTOS = new ArrayList<>();
        for(AgeGroup ageGroup : allAgeGroups){
            allAgeGroupDTOS.add(new AgeGroupDTO(ageGroup));
        }
        return FXCollections.observableArrayList(allAgeGroupDTOS);
    }
}
