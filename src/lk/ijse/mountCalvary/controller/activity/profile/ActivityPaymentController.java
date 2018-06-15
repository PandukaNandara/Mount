package lk.ijse.mountCalvary.controller.activity.profile;

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
import lk.ijse.mountCalvary.controller.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.PaymentDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityPaymentController implements Initializable {

    private static JasperReport paymentReport;
    @FXML
    private AnchorPane acActivityPayment;
    @FXML
    private TableView<PaymentDTO> tblActivityPayment;
    @FXML
    private TableColumn<PaymentDTO, String> colStudent_tblActivityPayment;
    @FXML
    private TableColumn<PaymentDTO, Integer> colYear_tblActivityPayment;
    @FXML
    private TableColumn<PaymentDTO, String> colMonth_tblActivityPayment;
    @FXML
    private TableColumn<PaymentDTO, BigDecimal> colFee_tblActivityPayment;
    @FXML
    private JFXComboBox<Month> cboxMonth;
    @FXML
    private JFXComboBox<Integer> cboxYear;
    @FXML
    private JFXButton btPrint;
    @FXML
    private JFXTextField txtStudent;

    private ObservableList<PaymentDTO> paymentDetailOfThisActivity;
    private AutoComplete<RegistrationDTO> autoComplete;
    private PaymentBO paymentBOImpl;
    private ActivityBO activityBOImpl;
    private ObservableList<RegistrationDTO> allRegistration;
    private ActivityDTO selectedActivity;
    private ActivityProfileController activityProfileController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);

        colStudent_tblActivityPayment.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colFee_tblActivityPayment.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colMonth_tblActivityPayment.setCellValueFactory(new PropertyValueFactory<>("month"));
        colYear_tblActivityPayment.setCellValueFactory(new PropertyValueFactory<>("year"));

        paymentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);

        autoComplete = new AutoComplete<>(txtStudent);
        autoComplete.setAutoCompletionsAction(event -> filterData());

        cboxMonth.getItems().setAll(Month.getAllMonth());
        cboxMonth.getItems().add(0, new Month(-1));
        cboxMonth.getSelectionModel().select(0);

        cboxYear.getItems().setAll(Common.loadYear());
        cboxYear.getSelectionModel().select(new Integer(LocalDate.now().getYear()));
    }

    public void init(ActivityProfileController activityProfileController) {
        this.activityProfileController = activityProfileController;

    }

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        filterData();
    }

    private void filterData() {
        try {
            ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

            String name = txtStudent.getText();

            int rid;
            try {
                rid = Common.searchRegistration(name, allRegistration).getRID();
            } catch (NullPointerException e) {
                rid = -1;
            }
            int year = cboxYear.getSelectionModel().getSelectedItem();
            int month = cboxMonth.getSelectionModel().getSelectedItem().getValue();

            for (PaymentDTO onePayment : paymentDetailOfThisActivity) {
                if ((rid == -1 || rid == onePayment.getRID()) &&
                        (month == -1 || month == onePayment.getMonth().getValue()) &&
                        (year == onePayment.getYear())
                        )
                    paymentDTOS.add(onePayment);
            }
            tblActivityPayment.getItems().setAll(paymentDTOS);
        } catch (NullPointerException e) {


        }
    }

    public void insertActivity(ActivityDTO activityDTO) {
        try {
            selectedActivity = activityDTO;
            allRegistration = activityBOImpl.getRegistrationOfThisActivity(activityDTO.getAID());

            paymentDetailOfThisActivity = paymentBOImpl.getPaymentDetailOfThisActivity(activityDTO.getAID());

            autoComplete.changeSuggestion(allRegistration);
            tblActivityPayment.getItems().setAll(paymentDetailOfThisActivity);

        } catch (Exception e) {
            Logger.getLogger(ActivityPaymentController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void btPrint_onAction(ActionEvent event) {
        if (selectedActivity != null) {
            try {
                if (paymentReport == null) {
                    InputStream paymentFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/activity/ActivityPaymentReport.jrxml");
                    paymentReport = JasperCompileManager.compileReport(paymentFile);
                }
                String studentName = txtStudent.getText().trim();
                Month month = cboxMonth.getSelectionModel().getSelectedItem();
                HashMap paymentMap = new HashMap();

                JRBeanCollectionDataSource payment = new JRBeanCollectionDataSource(tblActivityPayment.getItems());
                if (txtStudent.getText().trim().length() < 1) {
                    paymentMap.put("seeStudentName", true);
                    studentName = "All";
                }
                paymentMap.put("ActivityName", selectedActivity.getaName());
                paymentMap.put("StudentName", studentName);
                if (month.getValue() == -1) {
                    paymentMap.put("seeMonth", true);
                }
                paymentMap.put("Year", cboxYear.getSelectionModel().getSelectedItem());
                paymentMap.put("Month", month.toString());
                paymentMap.put("Payment", payment);

                JasperPrint paymentPrint = JasperFillManager.fillReport(paymentReport, paymentMap, new JREmptyDataSource());
                Reporter.showReport(paymentPrint, "Activity payment");
            } catch (Exception e) {
                Logger.getLogger(ActivityPaymentController.class.getName()).log(Level.SEVERE, null, e);

            }
        } else {
            Common.showError("Please select a student to print.");
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {
        filterData();
    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {
        filterData();
    }


//
//    @FXML
//    void btExcel_onAction(ActionEvent event) {
//
//    }
}
