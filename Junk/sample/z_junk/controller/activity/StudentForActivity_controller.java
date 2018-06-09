package sample.z_junk.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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

public class StudentForActivity_controller implements Initializable {
    @FXML
    private JFXButton btNext_StudentForActivity;

    @FXML
    private JFXButton btSkip_StudentForActivity;

    @FXML
    private JFXButton btBack_StudentForActivity;

    @FXML
    private JFXButton btRemove_tblStudentList;

    @FXML
    private AnchorPane acStudentForActivity;

    @FXML
    private TableView<?> tblStudentList;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colJoinedDate;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXDatePicker btJoinedDate;

    @FXML
    private JFXComboBox<?> cboxStudent;

    @FXML
    private JFXTextField btStudentID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAdd_onAction(ActionEvent event) {

    }

    @FXML
    void btJoinedDate_onAction(ActionEvent event) {

    }

    @FXML
    void btStudent_onAction(ActionEvent event) {

    }

    @FXML
    void cboxStudent_onAction(ActionEvent event) {

    }

    @FXML
    private void btRemove_tblStudentList_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void btBack_StudentForActivity_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/NewActivity.fxml", this.acStudentForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSkip_StudentForActivity_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/EventForActivity.fxml", this.acStudentForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btNext_StudentForActivity_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/activity/EventForActivity.fxml", this.acStudentForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
