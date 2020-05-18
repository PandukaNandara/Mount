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
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.activity.profile.ActivityPaymentController;
import lk.ijse.mountCalvary.controller.basic.MainMenuFrame_controller;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.PaymentDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public final class MakePayment_controller extends SuperController implements Initializable {

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
    private TableView<PaymentDTO> tblPastStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, Integer> colPastYear_tblPastStudentPayment;

    @FXML
    private TableColumn<PaymentDTO, Month> colPastMonth_tblPastStudentPayment;

    @FXML
    private JFXButton btActivityPaymentHistory;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private JFXComboBox<Month> cboxMonth;

    @FXML
    private JFXComboBox<Year> cboxYear;

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
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
        ButtonFireForEnterSetter.setGlobalEventHandler(acUpdatePayment);

        colActivity_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colStudent_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colFee_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colMonth_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("month"));
        colYear_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("year"));

        colPastYear_tblPastStudentPayment.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPastMonth_tblPastStudentPayment.setCellValueFactory(new PropertyValueFactory<>("month"));

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        paymentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

        //paymentBOImpl = BOFactory.getInstance().getBO()
        auto = new AutoComplete<>(txtStudentName);
        auto.setAutoCompletionsAction(event1 -> searchStudentAndShowPastDetails());
        cboxMonth.getItems().setAll(Month.getAllMonth());

        cboxYear.getItems().setAll(Year.getAllYear());
        cboxYear.getSelectionModel().select(new Year(LocalDate.now().getYear()));
        cboxMonth.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
        try {
            loadActivity();
        } catch (Exception e) {
            callLogger(e);
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
        int year = cboxYear.getSelectionModel().getSelectedItem().getYear();

        try {
            BigDecimal fee = BigDecimal.valueOf(Double.parseDouble(txtFee.getText()));
            if (isStudentCorrect()) {
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
            OptionPane.showErrorAtSide("The fee value is incorrect");
            txtFee.requestFocus();
            txtFee.selectAll();
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askWarning("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdatePayment, this);
        }
    }

    private void reloadTable() {
        int month = cboxMonth.getSelectionModel().getSelectedItem().getValue();
        int year = cboxYear.getSelectionModel().getSelectedItem().getYear();
        int AID = cboxActivity.getSelectionModel().getSelectedItem().getAID();
        try {
            ObservableList<PaymentDTO> paymentDetailForThisMonthAndYearAndActivity = paymentBOImpl.getPaymentDetailForThisMonthAndYearAndActivity(AID, year, month);
            removeOldRecords();
            tblStudentPayment.getItems().addAll(paymentDetailForThisMonthAndYearAndActivity);
        } catch (Exception e) {
            callLogger(e);
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
        try {
            if (tblStudentPayment.getSelectionModel().getSelectedItem().isNewOne()) {
                if (Common.removeItemFromTable(tblStudentPayment) == null)
                    OptionPane.showErrorAtSide("Please select an item from the table");
            } else {
                OptionPane.showErrorAtSide("This payment record is already added.");
            }
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        int count = 0;
        ObservableList<PaymentDTO> newPayments = FXCollections.observableArrayList();
        for (PaymentDTO onePayment : tblStudentPayment.getItems()) {
            if (onePayment.isNewOne()) {
                count++;
                newPayments.add(onePayment);
            }
        }
        if (count == 0)
            OptionPane.showErrorAtSide("Please add some new data.");
        else if (OptionPane.askQuestion("Do you want to make all payments?")) {
            try {
                if (paymentBOImpl.addAllPayment(newPayments)) {
                    OptionPane.showDoneAtSide("All Payment has successfully made.");
                    screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdatePayment, this);
                } else {
                    OptionPane.showWarningAtSide("Something's wrong we can't do your request.");
                }

            } catch (Exception e) {
                OptionPane.showWarning("Something's wrong we can't do your request.");
                callLogger(e);
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
            callLogger(e);
        }
    }

    private void searchStudentAndShowPastDetails() {
        selectedRegistrationDTO = auto.getSelectedItemByName();
        if (isStudentCorrect()) {
            txtFee.requestFocus();
        }

    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        String studentID = txtStudentID.getText().trim();
        if (studentID.length() == 0) {
            txtStudentName.requestFocus();
        } else if (Common.isInteger(studentID)) {
            int SID = Integer.parseInt(studentID);
            selectedRegistrationDTO = auto.searchByID(SID);
            if (isStudentCorrect())
                txtFee.requestFocus();
        } else {
            OptionPane.showErrorAtSide("The Student ID is invalid.");
        }
    }

    private boolean isStudentCorrect() {
        if (selectedRegistrationDTO != null) {
            txtStudentName.setText(selectedRegistrationDTO.getStudentName());
            txtStudentID.setText(String.valueOf(selectedRegistrationDTO.getSID()));
            try {
                tblPastStudentPayment.getItems().setAll(paymentBOImpl.getPaymentForThisActivityAndStudent(selectedRegistrationDTO.getRID()));
            } catch (Exception e) {
                callLogger(e);
            }

            return true;
        } else {
            tblPastStudentPayment.getItems().clear();
            OptionPane.showErrorAtSide("The student ID is not existed.");
            return false;
        }
    }

    private void clearAll(boolean withFee) {
        txtStudentID.clear();
        txtStudentName.clear();
        if (withFee)
            txtFee.clear();
    }

    @FXML
    private void btActivityPaymentHistory_onAction(ActionEvent actionEvent) {
        if (cboxActivity.getSelectionModel().getSelectedItem() == null)
            OptionPane.showErrorAtSide("Please select an activity");
        else {
            ActivityPaymentController controller = ScreenLoader.getInstance().loadNewWindow(
                    "/lk/ijse/mountCalvary/view/activity/profile/ActivityPayment.fxml",
                    String.format("Past payment details - %s", cboxActivity.getSelectionModel().getSelectedItem().getaName()));

            controller.insertActivity(new ActivityDTO(cboxActivity.getSelectionModel().getSelectedItem().getAID(), ""));
        }

    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {
        try {
            reloadTable();
        } catch (NullPointerException ignored) {
        }
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
        searchStudentAndShowPastDetails();
    }

}
