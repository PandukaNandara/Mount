package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.TeacherInChargeListBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.dao.custom.TeacherInChargeListDAO;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.model.TeacherInChargeListDTO;

import java.util.ArrayList;

public class TeacherInChargeListBOImpl implements TeacherInChargeListBO {

    TeacherInChargeListDAO teacherInChargeListDAOImpl;
    QueryDAO queryDAOImpl;

    public TeacherInChargeListBOImpl() {
        teacherInChargeListDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TEACHER_IN_CHARGE_LIST);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public ObservableList<TeacherInChargeListDTO> getTeacherInChargeListForThisCompetition(int CID) throws Exception {
        ArrayList<TeacherInChargeListDTO> teacherInChargeListDTOS = new ArrayList<>();
        ArrayList<CustomEntity> teacherInChargeListForThisCompetition = queryDAOImpl.getTeacherInChargeListForThisCompetition(CID);
        for(CustomEntity oneTeacher : teacherInChargeListForThisCompetition){
            teacherInChargeListDTOS.add(new TeacherInChargeListDTO(oneTeacher.getTINCID(), oneTeacher.getTID(), oneTeacher.gettName()));
        }
        return FXCollections.observableArrayList(teacherInChargeListDTOS);
    }
}
