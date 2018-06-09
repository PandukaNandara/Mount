package sample.z_junk.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventForActivity_controller implements Initializable {

    @FXML
    private AnchorPane acEventForActivity;
    @FXML
    private TableView<?> tblEvent;
    @FXML
    private TableColumn<?, ?> colEventName;
    @FXML
    private JFXButton btAdd;
    @FXML
    private JFXButton btFinish;
    @FXML
    private JFXTextField txtEventID;
    @FXML
    private JFXTextField txtEventName;
    @FXML
    private JFXRadioButton rbtMale;
    @FXML
    private JFXRadioButton rbtFemale;
    @FXML
    private JFXButton btBack;
    @FXML
    private JFXButton btRemove;
    @FXML
    private TableColumn colGender;
    @FXML
    private JFXButton btRemove_tblEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAdd_onAction(ActionEvent event) {

    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/StudentForActivity.fxml", this.acEventForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btFinish_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acEventForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void rbtFemale_onAction(ActionEvent event) {

    }

    @FXML
    void rbtMale_onAction(ActionEvent event) {

    }

    @FXML
    void txtEventID_onAction(ActionEvent event) {

    }

    @FXML
    void txtEventName_onAction(ActionEvent event) {

    }

    @FXML
    private void btRemove_tblEvent_onAction(ActionEvent actionEvent) {

    }
}
