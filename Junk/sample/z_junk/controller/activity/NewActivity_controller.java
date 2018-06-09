package sample.z_junk.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewActivity_controller implements Initializable {
    @FXML
    private JFXButton btNext_newActivity;


    @FXML
    private AnchorPane acNewActivity;

    @FXML
    private JFXTextField txtActivityName;

    @FXML
    private JFXRadioButton rbxNonPhysicalActivity;

    @FXML
    private JFXRadioButton btPhysicalActivity;

    @FXML
    private JFXComboBox<?> cboxTeacher;

    @FXML
    private JFXButton btAddNewTeacher;

    @FXML
    private JFXTextField btActivityID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void btActivityID_onAction(ActionEvent event) {

    }

    @FXML
    void btAddNewTeacher_onAction(ActionEvent event) {

    }

    @FXML
    void btPhysicalActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxTeacher_onAction(ActionEvent event) {

    }

    @FXML
    void rbxNonPhysicalActivity_onAction(ActionEvent event) {

    }

    @FXML
    void txtActivityName_onAction(ActionEvent event) {

    }

    @FXML
    private void btNext_newActivity_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/StudentForActivity.fxml", this.acNewActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
