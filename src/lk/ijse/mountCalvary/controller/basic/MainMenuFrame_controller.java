package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.OptionPane;
import lk.ijse.mountCalvary.tool.ScreenLoader;

import java.net.URL;
import java.util.ResourceBundle;

public final class MainMenuFrame_controller extends SuperController implements Initializable {

    private static MainMenuFrame_controller mainMenuFrame;
    private static VBox left;
    @FXML
    private BorderPane bpMainMenu;
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
    @FXML
    private JFXButton btTest;
    @FXML
    private VBox sideBar;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    public MainMenuFrame_controller() {
        mainMenuFrame = this;
    }

    public static MainMenuFrame_controller getMainMenuFrame() {
        return mainMenuFrame;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(bpMainMenu);
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.bpMainMenu, this);
        hideSideBar();
    }

    @FXML
    private void btMainMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.bpMainMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().hideSideBar();
    }

    @FXML
    private void btStudentMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btActivityMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;

        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btCompetitionMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btTeacherMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/TeacherMenu.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btUpdateAttendanceMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/attendance/UpdateAttendance.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btSpecialReportMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/SpecialReportMenu.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btSettingsMenu_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/settings/Settings.fxml", this.bpMainMenu, this);

    }

    @FXML
    private void btPayment_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml", bpMainMenu, this);

    }

    private boolean doYouWantToDiscard() {
        return !OptionPane.askQuestion("Do you stop this this task?");
    }

    @FXML
    private void btTest_onAction(ActionEvent actionEvent) {
        if (GlobalBoolean.isLocked() && doYouWantToDiscard())
            return;
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/TestMenu.fxml", bpMainMenu, this);
    }

    public void hideSideBar() {
        if (bpMainMenu.getLeft() != null)
            left = (VBox) bpMainMenu.getLeft();
        bpMainMenu.setLeft(null);
    }

    public void showSideBar() {
        bpMainMenu.setLeft(left);
    }
}
