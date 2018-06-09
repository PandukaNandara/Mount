package sample.z_junk.controller.student;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStudent_controller implements Initializable {
    @FXML
    private AnchorPane acUpdateStudent;
    @FXML
    private JFXButton btNext_UpdateStudent;
    @FXML
    private JFXRadioButton rbMale;
    @FXML
    private JFXRadioButton rbFemale;
    @FXML
    private JFXComboBox<?> cboxClass;
    @FXML
    private JFXComboBox<?> cboxGarde;
    @FXML
    private JFXDatePicker dtDOB;
    @FXML
    private JFXTextField txtMotherName;
    @FXML
    private JFXTextField txtFatherName;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXComboBox<?> cboxStudentName;
    @FXML
    private JFXButton btSearchStudent;
    @FXML
    private JFXTextArea txtaDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btNext_UpdateStudent(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/UpdateMorePersonalDetail.fxml", this.acUpdateStudent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btSearchStudent_onAction(ActionEvent event) {

    }

    @FXML
    void cboxClass_onAction(ActionEvent event) {

    }

    @FXML
    void cboxGrade_onAction(ActionEvent event) {

    }

    @FXML
    void cboxStudentName_onAction(ActionEvent event) {

    }

    @FXML
    void dtDOB_onAction(ActionEvent event) {

    }

    @FXML
    void txtFatherName_onAction(ActionEvent event) {

    }

    @FXML
    void txtMotherName_onAction(ActionEvent event) {

    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {

    }

    @FXML
    void txtaDesc_onAction(MouseEvent event) {

    }

}
