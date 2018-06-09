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

public class MainMenu_controller implements Initializable {

    @FXML
    private AnchorPane acMenu;

    @FXML
    private JFXButton btStudent;

    @FXML
    private JFXButton btActivity;

    @FXML
    private JFXButton btCompetition;

    @FXML
    private JFXButton btTeacher;

    @FXML
    private JFXButton btUpAttendance;

    @FXML
    private JFXButton btSpecialReport;

    @FXML
    void btActivity_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadInsidePanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btCompetition_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/CompetitionMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/StudentMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btTeacher_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/TeacherMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpAttendance_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/attendance/UpdateAttendance.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void btSpecialReport_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/SpecialReportMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void btPayment_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/PaymentMenu.fxml",this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
