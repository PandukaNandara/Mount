package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu_controller implements Initializable {

    @FXML
    private VBox acMenu;

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
        System.out.println("hello");
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btCompetition_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btTeacher_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/TeacherMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpAttendance_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/attendance/UpdateAttendance.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void btSpecialReport_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/SpecialReportMenu.fxml", this.acMenu, this);
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
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml",this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}