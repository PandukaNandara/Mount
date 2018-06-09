package sample.z_junk.controller.payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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

public class StudentPayment_controller implements Initializable {

    @FXML
    private AnchorPane acUpdatePayment;

    @FXML
    private TableView<?> tblStudentPayment;

    @FXML
    private TableColumn<?, ?> colStudent_tblStudentPayment;

    @FXML
    private TableColumn<?, ?> colActivity_tblStudentPayment;

    @FXML
    private TableColumn<?, ?> colYear_tblStudentPayment;

    @FXML
    private TableColumn<?, ?> colMonth_tblStudentPayment;

    @FXML
    private TableColumn<?, ?> colFee_tblStudentPayment;

    @FXML
    private JFXComboBox<?> cboxStudentName;

    @FXML
    private JFXComboBox<?> cboxActivity;

    @FXML
    private JFXComboBox<?> cboxMonth;

    @FXML
    private JFXComboBox<?> cboxYear;

    @FXML
    private JFXTextField cboxStudnetID;

    @FXML
    private JFXButton btRemove_tblStudentPayment;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private JFXButton btCancel;

    @FXML
    void btAdd_onAction(ActionEvent event) {

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/payment/StudentPayment.fxml",this.acUpdatePayment, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_tblStudentPayment_onAction(ActionEvent event) {

    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/payment/StudentPayment.fxml",this.acUpdatePayment, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {

    }

    @FXML
    void cboxStudnetID_onAction(ActionEvent event) {

    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btExcel_onAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btSearch_onAction(ActionEvent actionEvent) {
    }
}
