package sample.z_junk.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuFrame_controller implements Initializable {

    @FXML
    private BorderPane bpMainMenu;

    @FXML
    private AnchorPane acMenu;

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
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btMainMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btStudentMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/StudentMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btActivityMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btCompetitionMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/CompetitionMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btTeacherMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/TeacherMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btUpdateAttendanceMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/attendance/UpdateAttendance.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSpecialReportMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/SpecialReportMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSettingsMenu_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void btPayment_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/PaymentMenu.fxml", this.acMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
