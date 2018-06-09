package sample.z_junk.controller.student.profile;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalDetail_controller implements Initializable {

    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private JFXRadioButton rbMale;

    @FXML
    private JFXRadioButton rbFemale;

    @FXML
    private JFXComboBox<?> cboxClass;

    @FXML
    private JFXComboBox<?> cboxGrade;

    @FXML
    private JFXDatePicker dtDOB;

    @FXML
    private TableView<?> tblTelNo;

    @FXML
    private TableColumn<?, ?> colTelNo;

    @FXML
    private JFXTextField txtMotherName;

    @FXML
    private JFXTextField txtFatherName;

    @FXML
    private JFXTextField txtStudentID;

    @FXML
    private JFXTextArea txtaDescStudent;

    @FXML
    private AnchorPane acPersonalDetail;
    @FXML
    private JFXTextField txtAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
