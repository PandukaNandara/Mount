package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.OptionPane;
import lk.ijse.mountCalvary.controller.ScreenLoader;

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
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            GlobalBoolean.setLock(false);
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btMainMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btStudentMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btActivityMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;

            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btCompetitionMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btTeacherMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/TeacherMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btUpdateAttendanceMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/attendance/UpdateAttendance.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSpecialReportMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/SpecialReportMenu.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void btSettingsMenu_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/settings/Settings.fxml", this.bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void btPayment_onAction(ActionEvent actionEvent) {
        try {
            if (GlobalBoolean.isLocked() && doYouWantToDiscard())
                return;
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml", bpMainMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean doYouWantToDiscard() {
        return !OptionPane.askQuestion("Do you stop this this task?");
    }
}
