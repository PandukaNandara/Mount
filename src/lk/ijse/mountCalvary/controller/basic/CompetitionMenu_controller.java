package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;

import java.net.URL;
import java.util.ResourceBundle;

public final class CompetitionMenu_controller extends SuperController implements Initializable {
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
    @FXML
    private JFXButton btStudentContribution;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acCompetitionMenu);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acCompetitionMenu, this);
    }

    @FXML
    void btCreateCompetition_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/NewCompetition.fxml", this.acCompetitionMenu, this);
    }

    @FXML
    void btDeleteCompetition_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/DeleteCompetition.fxml", this.acCompetitionMenu, this);
    }

    @FXML
    private void btCompetitionProfile_onAction(ActionEvent actionEvent) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/profile/CompetitionProfile.fxml", this.acCompetitionMenu, this);
    }

    @FXML
    private void btAddEventCompetition_onAction(ActionEvent actionEvent) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/EventForCompetition.fxml", this.acCompetitionMenu, this);
    }

    @FXML
    private void btAddStudentCompetition_onAction(ActionEvent actionEvent) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/StudentForCompetition.fxml", this.acCompetitionMenu, this);
    }

    @FXML
    private void btStudentContribution_onAction(ActionEvent actionEvent) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/CompetitionContribution.fxml", this.acCompetitionMenu, this);
    }
}
