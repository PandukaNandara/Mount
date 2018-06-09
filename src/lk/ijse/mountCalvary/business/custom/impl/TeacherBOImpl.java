package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.TeacherDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.Teacher;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class TeacherBOImpl implements TeacherBO {
    private TeacherDAO teacherDAOImpl;
    public TeacherBOImpl() {
        teacherDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TEACHER);
    }

    @Override
    public boolean addTeacher(TeacherDTO teacher) throws Exception{
        return teacherDAOImpl.saveWithoutPKey(new Teacher(teacher.getTName()));
    }
    @Override
    public boolean addAllTeacher(ObservableList<TeacherDTO> all) throws Exception{
        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try{
            for(TeacherDTO teacher : all)
            if(!(teacherDAOImpl.saveWithoutPKey(new Teacher(teacher.getTName())))){
                conn.setAutoCommit(true);
                return false;
            }
            conn.commit();
            return true;
        }finally {
            conn.setAutoCommit(true);
        }
    }
    @Override
    public ArrayList<TeacherDTO> getAllTeacher() throws Exception {
        ArrayList<Teacher> allTeacher = teacherDAOImpl.getAll();
        ArrayList<TeacherDTO> allTeacherDTO = new ArrayList();
        for(Teacher one : allTeacher){
            allTeacherDTO.add(new TeacherDTO(one.getTID(), one.getTName()));
        }
        return allTeacherDTO;
    }
}
