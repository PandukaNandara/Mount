package lk.ijse.mountCalvary.business.custom;

import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.util.ArrayList;

public interface TeacherBO extends SuperBO {
    boolean addTeacher(TeacherDTO teacher) throws Exception;

    boolean addAllTeacher(ObservableList<TeacherDTO> all) throws Exception;

    ArrayList<TeacherDTO> getAllTeacher() throws Exception;
}
