package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.Common;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuFrame_controller implements Initializable {

    @FXML
    private BorderPane bpMainMenu;

    @FXML
    private VBox acMenu;

    @FXML
    private JFXButton btSettingsMenu;

    @FXML
    private JFXButton btSpecialReportMenu;

    @FXML
    private JFXButton btUpdateAttendanceMenu;

    @FXML
    private JFXButton btTeacherMenu;

    @FXML
    private JFXButton btCompetitionMenu;

    @FXML
    private JFXButton btActivityMenu;

    @FXML
    private JFXButton btStudentMenu;

    @FXML
    private JFXButton btMainMenu;
    @FXML
    private JFXButton btPayment;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btMainMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btStudentMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btActivityMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btCompetitionMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btTeacherMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/TeacherMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btUpdateAttendanceMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/attendance/UpdateAttendance.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSpecialReportMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/SpecialReportMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSettingsMenu_onAction(ActionEvent actionEvent) {
        Common.showMessage("Still Developing");
//        try {
//            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/", this.acMenu, this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @FXML
    private void btPayment_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
