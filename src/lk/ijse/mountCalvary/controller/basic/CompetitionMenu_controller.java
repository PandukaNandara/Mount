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

public class CompetitionMenu_controller implements Initializable {
    @FXML
    private JFXButton btAddStudentCompetition;
    @FXML
    private JFXButton btAddEventCompetition;

    @FXML
    private VBox acCompetitionMenu;

    @FXML
    private JFXButton btCreateCompetition;

    @FXML
    private JFXButton btDeleteCompetition;

    @FXML
    private JFXButton btBack;

    @FXML
    private JFXButton btCompetitionProfile;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btCreateCompetition_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/NewCompetition.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btDeleteCompetition_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/DeleteCompetition.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btCompetitionProfile_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/profile/CompetitionProfile.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btAddEventCompetition_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/EventForCompetition.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btAddStudentCompetition_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/StudentForCompetition.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
