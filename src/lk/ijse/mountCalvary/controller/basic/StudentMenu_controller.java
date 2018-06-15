package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.GlobalBoolean;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMenu_controller implements Initializable{

    @FXML
    private AnchorPane acStudentMenu;

    @FXML
    private JFXButton btNewStudent;

    @FXML
    private JFXButton btUpdateStudent;

    @FXML
    private JFXButton btBack;

    @FXML
    private JFXButton btStudentProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
    }
    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdateStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/student/UpdateStudent.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btNewStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/student/NewStudent.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudentProfile_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/student/profile/StudentProfile.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
