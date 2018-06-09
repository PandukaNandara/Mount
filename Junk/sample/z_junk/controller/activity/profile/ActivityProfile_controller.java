package sample.z_junk.controller.activity.profile;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityProfile_controller implements Initializable {

    @FXML
    private BorderPane bpActivityProfile;

    @FXML
    private AnchorPane acActivityRoot;

    @FXML
    private VBox vbxActivityProfileSideMenu;

    @FXML
    private JFXButton btActivityDetail;

    @FXML
    private JFXButton btAttendantSheet;

    @FXML
    private JFXButton btJoinedSheet;

    @FXML
    private JFXButton btEventAndCompetition;

    @FXML
    private HBox hbxReport;

    @FXML
    private JFXButton btPrint;

    @FXML
    private JFXButton btExcel;

    @FXML
    private AnchorPane bpActivityProfileTop;

    @FXML
    private JFXButton btSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void btActivityDetail_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/profile/ActivityDetail.fxml", this.acActivityRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btAttendantSheet_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/profile/AttendantSheet.fxml", this.acActivityRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btJoinedSheet_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/profile/JoinedStudent.fxml", this.acActivityRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btEventAndCompetition_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/profile/EventAndCompetitionOfActivity.fxml", this.acActivityRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void btExcel_onAction(ActionEvent actionEvent) {

    }
}
