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

public class TeacherMenu_controller implements Initializable {
    @FXML
    private AnchorPane acTeacherMenu;
    @FXML
    private JFXButton btNewTeacher;
    @FXML
    private JFXButton btBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml",this.acTeacherMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btNewTeacher_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/teacher/NewTeacher.fxml", this.acTeacherMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
