package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMenu_controller implements Initializable{

    @FXML
    private VBox acStudentMenu;

    @FXML
    private JFXButton btNewStudent;

    @FXML
    private JFXButton btUpdateStudent;

    @FXML
    private JFXButton btBack;

    @FXML
    private JFXButton btStudentProfile;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
    }
    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdateStudent_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/UpdateStudent.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btNewStudent_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/NewStudent.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudentProfile_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/profile/StudentProfile.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
