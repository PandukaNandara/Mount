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

public class ActivityMenu_controller implements Initializable {

    @FXML
    private VBox acActivityMenu;

    @FXML
    private JFXButton btNewActivity;

    @FXML
    private JFXButton btNewEvent;

    @FXML
    private JFXButton btAddStudent;

    @FXML
    private JFXButton btUpdateActivity;

    @FXML
    private JFXButton btBack;
    @FXML
    private JFXButton btActivityProfile;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", acActivityMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdateActivity_onAction(ActionEvent event) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/activity/UpdateActivity.fxml", acActivityMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btAddStudent_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/activity/AddStudentForActivity.fxml", acActivityMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btNewEvent_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/activity/NewEventForActivity.fxml", acActivityMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btNewActivity_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/activity/NewActivity.fxml", acActivityMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btActivityProfile_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/activity/profile/ActivityProfile.fxml", acActivityMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

