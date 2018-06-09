package sample.z_junk.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

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

    }
    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdateStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/UpdateStudent.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btNewStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/NewStudent.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudentProfile_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/StudentProfile.fxml", acStudentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
