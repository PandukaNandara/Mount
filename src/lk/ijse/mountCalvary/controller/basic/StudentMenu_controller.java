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

public final class StudentMenu_controller extends SuperController implements Initializable {

    @FXML
    private VBox acStudentMenu;

    @FXML
    private JFXButton btNewStudent;

    @FXML
    private JFXButton btUpdateStudent;

    @FXML
    private JFXButton btBack;

    @FXML
    private JFXButton btStudentProfile;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @FXML
    private JFXButton btImportStudentDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acStudentMenu);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", acStudentMenu, this);
    }

    @FXML
    void btUpdateStudent_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/UpdateStudent.fxml", acStudentMenu, this);
    }

    @FXML
    void btNewStudent_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/NewStudent.fxml", acStudentMenu, this);
    }

    @FXML
    void btStudentProfile_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/profile/StudentProfile.fxml", acStudentMenu, this);
    }

    @FXML
    private void btImportStudentDetails_onAction(ActionEvent actionEvent) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/ImportStudentAndPreview.fxml", acStudentMenu, this);
    }
}
