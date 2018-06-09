package sample.z_junk.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateActivity_controller implements Initializable {
    @FXML
    private AnchorPane acUpdateActivity;
    @FXML
    private JFXComboBox<?> cboxTeacherInCharge;
    @FXML
    private JFXButton btUpdate;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXComboBox<?> cboxActivity;
    @FXML
    private JFXButton btAddNewTeacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAddNewTeacher_onAction(ActionEvent event) {

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acUpdateActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btTeacherInCharge_onAction(ActionEvent event) {

    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acUpdateActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

}
