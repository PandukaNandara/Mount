package lk.ijse.mountCalvary.controller.payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.PaymentBO;
import lk.ijse.mountCalvary.controller.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.PaymentDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MakePayment_controller implements Initializable {

    @FXML
    private VBox acUpdatePayment;

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
    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);

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
        } catch (Exception e) {
            Logger.getLogger(MakePayment_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadActivity() throws Exception {
        ArrayList<ActivityDTO> allActivity = activityBOImpl.getAllActivity();
        cboxActivity.getItems().setAll(allActivity);
    }

    private boolean isAlreadyAdded(PaymentDTO paymentDTO) {
        for (PaymentDTO onePaymentDTO : tblStudentPayment.getItems()) {
            if (onePaymentDTO.getRID() == paymentDTO.getRID() &&
                    onePaymentDTO.getMonth().getValue() == paymentDTO.getMonth().getValue() &&
                    onePaymentDTO.getYear() == paymentDTO.getYear())
                return true;
        }
        return false;
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
                if (!isAlreadyAdded(paymentDTO)) {
                    paymentDTO.setNewOne(true);
                    tblStudentPayment.getItems().add(paymentDTO);
                    clearAll(false);
                } else {
                    OptionPane.showWarning("This student has paid in this month and year for this activity.");
                }

                txtStudentID.requestFocus();
            }
        } catch (NumberFormatException e) {
            OptionPane.showError("The fee value is incorrect");
            txtFee.requestFocus();
            txtFee.selectAll();
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askWarning("Do you want to cancel?");
        if (answer) {
            try {
                screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdatePayment, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void reloadTable() {
        int month = cboxMonth.getSelectionModel().getSelectedItem().getValue();
        int year = cboxYear.getSelectionModel().getSelectedItem();
        int AID = cboxActivity.getSelectionModel().getSelectedItem().getAID();
        try {
            ObservableList<PaymentDTO> paymentDetailForThisMonthAndYearAndActivity = paymentBOImpl.getPaymentDetailForThisMonthAndYearAndActivity(AID, year, month);
            removeOldRecords();
            tblStudentPayment.getItems().addAll(paymentDetailForThisMonthAndYearAndActivity);
        } catch (Exception e) {
            Logger.getLogger(MakePayment_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void removeOldRecords() {
        tblStudentPayment.getItems().removeIf(paymentDTO -> !paymentDTO.isNewOne());
//        List<PaymentDTO> toRemove = new ArrayList<>();
//        for (PaymentDTO paymentDTO : tblStudentPayment.getItems()) {
//
//            if (!paymentDTO.isNewOne())
//                toRemove.add(paymentDTO);
//        }
//        tblStudentPayment.getItems().removeAll(toRemove);
    }

    @FXML
    void btRemove_tblStudentPayment_onAction(ActionEvent event) {
        if (tblStudentPayment.getSelectionModel().getSelectedItem().isNewOne()) {
            if (Common.removeItemFromTable(tblStudentPayment) == null)
                OptionPane.showError("Please select an item from the table");
        } else {
            OptionPane.showError("This payment record is already added.");
        }
    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        if (OptionPane.askQuestion("Do you want to make all payment?")) {
            ObservableList<PaymentDTO> newPayment = FXCollections.observableArrayList();
            for (PaymentDTO onePayment : tblStudentPayment.getItems())
                if (onePayment.isNewOne())
                    newPayment.add(onePayment);
            try {
                if (paymentBOImpl.addAllPayment(newPayment)) {
                    OptionPane.showMessage("All Payment has successfully made.");
                    screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdatePayment, this);
                } else {
                    OptionPane.showWarning("Something's wrong we can't do your request.");
                }

            } catch (Exception e) {
                Logger.getLogger(MakePayment_controller.class.getName()).log(Level.SEVERE, null, e);
                OptionPane.showWarning(e.toString());

            }
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        clearAll(true);
        reloadTable();
        ActivityDTO select = cboxActivity.getSelectionModel().getSelectedItem();
        try {
            registrationOfThisActivity = activityBOImpl.getRegistrationOfThisActivity(select.getAID());
            auto.changeSuggestion(registrationOfThisActivity);
        } catch (Exception e) {
            Logger.getLogger(MakePayment_controller.class.getName()).log(Level.SEVERE, null, e);

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
        selectedRegistrationDTO = auto.getSelectedItemByName();
        if (checkStudentIsCorrect())
            txtFee.requestFocus();
    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {
        reloadTable();
    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        String studentID = txtStudentID.getText().trim();
        if (studentID.length() == 0) {
            txtStudentName.requestFocus();
        } else if (Common.isInteger(studentID)) {
            int SID = Integer.parseInt(studentID);

            selectedRegistrationDTO = auto.searchByID(SID);
            if (checkStudentIsCorrect())
                txtFee.requestFocus();
        } else {
            OptionPane.showError("The Student ID is invalid.");
        }
    }

    private boolean checkStudentIsCorrect() {
        if (selectedRegistrationDTO != null) {
            txtStudentName.setText(selectedRegistrationDTO.getStudentName());
            txtStudentID.setText(selectedRegistrationDTO.getSID() + "");
            return true;
        } else {
            OptionPane.showError("the student ID is not existed.");
            return false;
        }
    }

    private void clearAll(boolean withFee) {
        txtStudentID.setText("");
        txtStudentName.setText("");
        if (withFee)
            txtFee.setText("");
    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {
        reloadTable();
    }

    @FXML
    void txtFee_onAction(ActionEvent event) {
        btAdd.fire();
    }

    @FXML
    void txtStudentName_onAction(ActionEvent event) {
        searchStudent();
    }

}
