package lk.ijse.mountCalvary.controller.payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.PaymentBO;
import lk.ijse.mountCalvary.controller.AutoComplete;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.Month;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.PaymentDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MakePayment_controller implements Initializable {

    @FXML
    private AnchorPane acUpdatePayment;

    @FXML
    private TableView<PaymentDTO> tblStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, String> colStudent_tblStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, String> colActivity_tblStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, Integer> colYear_tblStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, String> colMonth_tblStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, BigDecimal> colFee_tblStudentPayment;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private JFXComboBox<Month> cboxMonth;

    @FXML
    private JFXComboBox<Integer> cboxYear;

    @FXML
    private JFXTextField txtStudentID;

    @FXML
    private JFXButton btRemove_tblStudentPayment;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private JFXTextField txtFee;
    private AutoComplete<RegistrationDTO> auto;

    private ActivityBO activityBOImpl;

    private PaymentBO paymentBOImpl;

    private ObservableList<RegistrationDTO> registrationOfThisActivity;
    private RegistrationDTO selectedRegistrationDTO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colActivity_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colStudent_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colFee_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colMonth_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("month"));
        colYear_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("year"));

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        paymentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
        //paymentBOImpl = BOFactory.getInstance().getBO()
        auto = new AutoComplete<>(txtStudentName);
        auto.setAutoCompletionsAction(event1 -> searchStudent());

        cboxMonth.getItems().setAll(Month.getAllMonth());

        cboxYear.getItems().setAll(Common.loadYear());
        cboxYear.getSelectionModel().select(new Integer(LocalDate.now().getYear()));

        cboxMonth.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
        try {
            loadActivity();
        } catch (Exception ex) {

        }
    }

    private void loadActivity() throws Exception {
        ArrayList<ActivityDTO> allActivity = activityBOImpl.getAllActivity();
        cboxActivity.getItems().setAll(allActivity);
    }

    @FXML
    void btAdd_onAction(ActionEvent event) {
        Month selectedMonth = cboxMonth.getSelectionModel().getSelectedItem();
        int year = cboxYear.getSelectionModel().getSelectedItem();
        try {
            BigDecimal fee = BigDecimal.valueOf(Double.parseDouble(txtFee.getText()));
            if (checkStudentIsCorrect()) {
                PaymentDTO paymentDTO = new PaymentDTO(selectedRegistrationDTO, fee, selectedMonth, year);
                paymentDTO.setActivityName(cboxActivity.getSelectionModel().getSelectedItem().getaName());
                tblStudentPayment.getItems().add(paymentDTO);
                clearAll(false);
                txtStudentID.requestFocus();
            }
        } catch (NumberFormatException e) {
            Common.showError("The fee value is incorrect");
            txtFee.requestFocus();
            txtFee.selectAll();
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {

    }

    @FXML
    void btRemove_tblStudentPayment_onAction(ActionEvent event) {

        if(Common.removeItemFromTable(tblStudentPayment) == null)
            Common.showError("Please select an item from the table");

    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        if(Common.askQuestion("Do you want to make all payment?")) {
            try {
                if (paymentBOImpl.addAllPayment(tblStudentPayment.getItems())) {
                    Common.showMessage("All Payment has successfully made");
                } else {
                    Common.showWarning("Something's wrong we can't do your request");
                }
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdatePayment, this);
            } catch (Exception e) {
                Common.showWarning(e.toString());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        clearAll(true);
        ActivityDTO select = cboxActivity.getSelectionModel().getSelectedItem();
        try {
            registrationOfThisActivity = activityBOImpl.getRegistrationOfThisActivity(select.getAID());
            auto.changeSuggestion(registrationOfThisActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        ObservableList<RegistrationDTO> regList = select.getRegistrationDTOS();
//        auto.changeSuggestion(regList);
//        auto.setAutoCompletionsAction(event1 -> {
//            StudentDTO studentDTO = Common.searchStudent(txtStudentName.getText().trim(), cboxActivity.getSelectionModel().getSelectedItem().getStudentDTOS());
//            RegistrationDTO registrationDTO = Common.searchRegistration(txtStudentName.getText().trim(), cboxActivity.getSelectionModel().getSelectedItem().getRegistrationDTOS());
//            txtStudnetID.setText(registrationDTO.getSID() + "");
//        });
//        cboxActivity.setDisable(true);
    }

    private void searchStudent() {
        String studentName = txtStudentName.getText().trim();
        selectedRegistrationDTO = Common.searchRegistration(studentName, registrationOfThisActivity);
        if (checkStudentIsCorrect())
            txtFee.requestFocus();
    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {}

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        String studentID = txtStudentID.getText().trim();
        if (studentID.length() == 0) {
            txtStudentName.requestFocus();
        }else if (Common.isInteger(studentID)) {
            int SID = Integer.parseInt(studentID);
            selectedRegistrationDTO = Common.searchRegistration(SID, registrationOfThisActivity);
            if (checkStudentIsCorrect())
                txtFee.requestFocus();
        } else {
            Common.showError("The Student ID is invalid.");
        }
    }
    private boolean checkStudentIsCorrect() {
        if (selectedRegistrationDTO != null) {
            txtStudentName.setText(selectedRegistrationDTO.getStudentName());
            txtStudentID.setText(selectedRegistrationDTO.getSID() + "");
            return true;
        } else {
            Common.showError("the student ID is not existed.");
            return false;
        }
    }
    private void clearAll(boolean withFee) {
        txtStudentID.setText("");
        txtStudentName.setText("");
        if(withFee)
            txtFee.setText("");
    }
    @FXML
    void cboxYear_onAction(ActionEvent event) {}

    @FXML
    void txtFee_onAction(ActionEvent event) {
        btAdd.fire();
    }

    @FXML
    void txtStudentName_onAction(ActionEvent event) {
        searchStudent();
    }

}
