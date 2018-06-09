package sample.z_junk.controller.attendance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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

public class UpdateAttendance_controller implements Initializable {
    @FXML
    private AnchorPane acUpdateAttendance;
    @FXML
    private JFXComboBox<?> cboxActivity;
    @FXML
    private JFXComboBox<?> cboxTeacherInCharge;
    @FXML
    private JFXDatePicker dpDate;
    @FXML
    private TableView<?> tblStudentList;
    @FXML
    private TableColumn<?, ?> colStudentList;
    @FXML
    private TableView<?> tblAttendance;
    @FXML
    private TableColumn<?, ?> colStudent;
    @FXML
    private TableColumn<?, ?> colActivity;
    @FXML
    private TableColumn<?, ?> colTeacherInCharge;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btUpdate;
    @FXML
    private JFXButton btRemove;
    @FXML
    private JFXButton btAddAll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAddAll_onAction(ActionEvent event) {

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", this.acUpdateAttendance, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_onAction(ActionEvent event) {

    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", this.acUpdateAttendance, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxTeacherInCharge_onAction(ActionEvent event) {

    }

    @FXML
    void dpDate_onAction(ActionEvent event) {

    }

}
