package sample.z_junk.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfile_controller implements Initializable {

    @FXML
    private BorderPane bpStudentProfile;

    @FXML
    private VBox vbxSideBar;

    @FXML
    private JFXButton btPersonalDetail;

    @FXML
    private JFXButton btAttendanceAndActivity;

    @FXML
    private JFXButton btCompAndAchive;

    @FXML
    private JFXButton btPrint;

    @FXML
    private JFXButton btExcel;

    @FXML
    private AnchorPane acTopStudentProfile;

    @FXML
    private JFXButton btSearch;

    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private JFXTextField btStudentID;

    @FXML
    private AnchorPane acRootStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/PersonalDetail.fxml", this.acRootStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btPersonalDetail_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/PersonalDetail.fxml", this.acRootStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btAttendanceAndActivity_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/AttendanceAndActivityOfStudent.fxml", this.acRootStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btCompAndAchive_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/CompetitionForStudent.fxml", this.acRootStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/PersonalDetail.fxml", this.acRootStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btExcel_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/profile/PersonalDetail.fxml", this.acRootStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
