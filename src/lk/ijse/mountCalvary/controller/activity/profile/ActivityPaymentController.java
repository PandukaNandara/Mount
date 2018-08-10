package lk.ijse.mountCalvary.controller.activity.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.PaymentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
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

public final class ActivityPaymentController extends SuperController implements Initializable {

    private static JasperReport paymentReport;
    @FXML
    private VBox acActivityPayment;
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
    private JFXComboBox<Year> cboxYear;
    @FXML
    private JFXButton btPrint;
    @FXML
    private JFXTextField txtStudent;

    @FXML
    private JFXToggleButton btInverse;

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
        ButtonFireForEnterSetter.setGlobalEventHandler(acActivityPayment);

        btInverse.setTooltip(new Tooltip(MessageReader.getInstance().getMessage(0)));
        btInverse.setDisable(true);

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

        cboxYear.getItems().setAll(Year.getAllYear());
        cboxYear.getSelectionModel().select(new Year(LocalDate.now().getYear()));

        txtStudent.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue)
                filterData();
        });


    }

    public void init(ActivityProfileController activityProfileController) {
        this.activityProfileController = activityProfileController;

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
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a student to print.");
        }
    }

    private void filterData() {
        Month thisMonth = cboxMonth.getSelectionModel().getSelectedItem();
        if (thisMonth.getValue() == Month.ALL) {
            btInverse.setDisable(true);
            btInverse.setSelected(false);
        } else {
            btInverse.setDisable(false);
        }
        boolean isInverseSelected = btInverse.isSelected();

        try {
            ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
            int rid;
            try {
                rid = autoComplete.getSelectedItemByName().getRID();
            } catch (NullPointerException e) {
                rid = -1;

            }
            int year = cboxYear.getSelectionModel().getSelectedItem().getYear();
            int month = cboxMonth.getSelectionModel().getSelectedItem().getValue();

            for (PaymentDTO onePayment : paymentDetailOfThisActivity) {
                if ((rid == -1 || rid == onePayment.getRID()) &&
                        (month == Month.ALL || month == onePayment.getMonth().getValue()) &&
                        (year == onePayment.getYear())
                        )
                    paymentDTOS.add(onePayment);

            }
            tblActivityPayment.getItems().setAll(paymentDTOS);
            if (isInverseSelected) {
                inverseFilter();
            }
        } catch (NullPointerException ignored) {
        }
    }

    public void insertActivity(ActivityDTO activityDTO) {
        try {
            selectedActivity = activityDTO;
            allRegistration = activityBOImpl.getRegistrationOfThisActivity(activityDTO.getAID());

            paymentDetailOfThisActivity = paymentBOImpl.getPaymentDetailOfThisActivity(activityDTO.getAID());

            autoComplete.changeSuggestion(allRegistration);
            tblActivityPayment.getItems().setAll(paymentDetailOfThisActivity);
            Common.clearSortOrder(tblActivityPayment);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    /**
     * We are going to get inverse data from the tblActivityPayment
     * first we check whether the toggle button is select or not.
     * Then we iterate all registration for this activity and secondly
     * we iterate present paymentDTO that shown in tblAttendantSheet.
     */
    private void inverseFilter() {
        boolean isSelected = btInverse.isSelected();
        if (isSelected) try {
            txtStudent.clear();
            txtStudent.setDisable(true);
            //ok
            ArrayList<PaymentDTO> inverseFilter = new ArrayList<>();
            //ok
            ObservableList<PaymentDTO> present = tblActivityPayment.getItems();

            //ok
            ObservableList<RegistrationDTO> registrations = activityBOImpl.
                    getRegistrationOfThisActivity(selectedActivity.getAID());


            for (int i = 0; i < registrations.size(); i++) {
                boolean shouldRemove = false;
                for (int j = 0; j < present.size(); j++) {
                    if (registrations.get(i).getRID() == present.get(j).getRID()) {
                        present.remove(j--);
                        shouldRemove = true;
                    }
                }
                if (shouldRemove)
                    registrations.remove(i--);
                else {
                    PaymentDTO paymentDTO = new PaymentDTO();

                    paymentDTO.setRID(registrations.get(i).getRID());
                    paymentDTO.setStudentName(registrations.get(i).getStudentName());
                    inverseFilter.add(paymentDTO);
                }

            }

            tblActivityPayment.getItems().setAll(inverseFilter);
        } catch (Exception e) {
            callLogger(e);
        }
        else {
            txtStudent.setDisable(false);
            filterData();
        }
    }

    @FXML
    private void btInverse_onAction(ActionEvent actionEvent) {
        inverseFilter();
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

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        filterData();
    }


//
//    @FXML
//    void btExcel_onAction(ActionEvent event) {
//
//    }
}
