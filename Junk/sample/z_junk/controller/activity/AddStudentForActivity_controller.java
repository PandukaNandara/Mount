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

public class AddStudentForActivity_controller implements Initializable {
    @FXML
    private AnchorPane acAddStudent;
    @FXML
    private TableView<?> tblStudentList;
    @FXML
    private TableColumn<?, ?> colStudentName;
    @FXML
    private TableColumn<?, ?> colJoinedDate;
    @FXML
    private JFXButton btAdd;
    @FXML
    private JFXDatePicker dtJoinedDate;
    @FXML
    private JFXComboBox<?> cboxStudent;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXButton btSubmit;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXComboBox<?> cboxActivity;
    @FXML
    private JFXButton btRemove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAdd_onAction(ActionEvent event) {

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acAddStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_onAction(ActionEvent event) {

    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acAddStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxStudent_onAction(ActionEvent event) {

    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {

    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {

    }


}
