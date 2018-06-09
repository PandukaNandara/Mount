package sample.z_junk.controller.payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ActivityPayment_controller implements Initializable {

    @FXML
    private AnchorPane acActivityPayment;

    @FXML
    private TableView<?> tblActivityPayment;

    @FXML
    private TableColumn<?, ?> colActivity_tblActivityPayment;

    @FXML
    private TableColumn<?, ?> colYear_tblActivityPayment;

    @FXML
    private TableColumn<?, ?> colMonth_tblActivityPayment;

    @FXML
    private TableColumn<?, ?> colFee_tblActivityPayment;

    @FXML
    private JFXComboBox<?> cboxActivity;

    @FXML
    private JFXComboBox<?> cboxMonth;

    @FXML
    private JFXComboBox<?> cboxYear;

    @FXML
    private JFXButton btPrint;

    @FXML
    private JFXButton btExcel;

    @FXML
    void btExcel_onAction(ActionEvent event) {

    }

    @FXML
    void btPrint_onAction(ActionEvent event) {

    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {

    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
