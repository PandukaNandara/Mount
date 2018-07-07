package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.PaymentBO;
import lk.ijse.mountCalvary.business.custom.RegistrationBO;
import lk.ijse.mountCalvary.controller.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.PaymentDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentPaymentController implements Initializable {

    private static JasperReport paymentReport;
    private static JasperReport jasperReport;
    @FXML
    private AnchorPane acUpdatePayment;
    @FXML
    private TableView<PaymentDTO> tblStudentPayment;
    @FXML
    private TableColumn<PaymentDTO, String> colActivity_tblStudentPayment;
    @FXML
    private TableColumn<PaymentDTO, Integer> colYear_tblStudentPayment;
    @FXML
    private TableColumn<PaymentDTO, Month> colMonth_tblStudentPayment;
    @FXML
    private TableColumn<PaymentDTO, Integer> colFee_tblStudentPayment;
    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;
    @FXML
    private JFXComboBox<Month> cboxMonth;
    @FXML
    private JFXComboBox<Integer> cboxYear;
    @FXML
    private JFXButton btPrint;
    @FXML
    private VBox acStudentPayment;
    private studentProfileController studentProfileController;
    private ObservableList<PaymentDTO> paymentDetailOfThisStudent;
    private PaymentBO paymentBOImpl;
    private RegistrationBO registrationBOImpl;
    private ObservableList<ActivityDTO> activityListForThisStudent;
    private StudentDTO selectedStudent;
    @FXML
    private JFXButton btInverse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);

        colActivity_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colMonth_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("month"));
        colFee_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colYear_tblStudentPayment.setCellValueFactory(new PropertyValueFactory<>("year"));

        paymentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
        registrationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.REGISTRATION);

        cboxMonth.getItems().setAll(Month.getAllMonth());
        cboxMonth.getItems().add(0, new Month(-1));
        cboxMonth.getSelectionModel().select(0);

        cboxYear.getItems().setAll(Common.loadYear());
        cboxYear.getSelectionModel().select(new Integer(LocalDate.now().getYear()));
        cboxActivity.getSelectionModel().select(0);

    }

    public void init(studentProfileController studentProfileController) {
        this.studentProfileController = studentProfileController;
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        filterPayment();
    }

    protected void insertStudentID(StudentDTO studentDTO) {
        try {
            selectedStudent = studentDTO;
            paymentDetailOfThisStudent = null;
            paymentDetailOfThisStudent = paymentBOImpl.getPaymentDetailOfThisStudent(studentDTO.getSID());
            activityListForThisStudent = registrationBOImpl.getActivityListForThisStudent(studentDTO.getSID());

            activityListForThisStudent.add(0, new ActivityDTO(-1, "All"));
            cboxActivity.getItems().setAll(activityListForThisStudent);

            //This is used to select all activity

            ////////////////////////////////////////////
            tblStudentPayment.getItems().setAll(paymentDetailOfThisStudent);
            cboxActivity.getSelectionModel().select(0);
        } catch (Exception e) {
            Logger.getLogger(StudentPaymentController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {
        filterPayment();
    }

    private void filterPayment() {
        try {
            ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
            int year = cboxYear.getSelectionModel().getSelectedItem();
            int month = cboxMonth.getSelectionModel().getSelectedItem().getValue();
            int AID = cboxActivity.getSelectionModel().getSelectedItem().getAID();
            for (PaymentDTO onePayment : paymentDetailOfThisStudent) {
                if ((AID == -1 || AID == onePayment.getAID()) &&
                        (month == -1 || month == onePayment.getMonth().getValue()) &&
                        (year == onePayment.getYear())
                        )
                    paymentDTOS.add(onePayment);
            }
            tblStudentPayment.getItems().setAll(paymentDTOS);
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    private void btInverse_onAction(ActionEvent actionEvent) {

    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {
        filterPayment();

    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
//        if(selectedStudent != null){
//
//            try {
//                if(jasperReport == null){
//                    InputStream reportFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/StudentPayment.jrxml");
//                    jasperReport = JasperCompileManager.compileReport(reportFile);
//                }
//                HashMap map = new HashMap();
//                if(cboxActivity.getSelectionModel().getSelectedItem().getAID() == -1){
//                    map.put("seeActivity", true);
//                }
//                if(cboxMonth.getSelectionModel().getSelectedItem().getValue() == -1){
//                    map.put("seeMonth", true);
//                }
//
//                map.put("ActivityName", cboxActivity.getSelectionModel().getSelectedItem().getaName());
//                map.put("year", cboxYear.getSelectionModel().getSelectedItem());
//                map.put("month", cboxMonth.getSelectionModel().getSelectedItem().toString());
//                map.put("studentName", selectedStudent.getsName());
//
//                JRBeanCollectionDataSource pay = new JRBeanCollectionDataSource(tblStudentPayment.getItems());
//                map.put("payments", pay);
//
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
//                System.out.println("This is it");
//                Reporter.showReport(jasperPrint, "Student payment");
//
//            } catch (Exception e) {
//        Logger.getLogger(StudentPaymentController.class.getName()).log(Level.SEVERE, null, e);
//                e.printStackTrace();
//            }
//        }else {
//            Common.showError("Please select a student to print.");
//        }
        if (selectedStudent != null) {
            try {
//                Progress progress = new Progress(acStudentPayment,"Loading report", "Now loading");
//                progress.show();
                if (paymentReport == null) {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/StudentPaymentReport.jrxml");
                    paymentReport = JasperCompileManager.compileReport(resourceAsStream);
                }
                ActivityDTO selectedActivity = cboxActivity.getSelectionModel().getSelectedItem();
                Month selectedMonth = cboxMonth.getSelectionModel().getSelectedItem();

                JRBeanCollectionDataSource payment = new JRBeanCollectionDataSource(tblStudentPayment.getItems());
                HashMap map = new HashMap();
                map.put("StudentID", selectedStudent.getSID());
                map.put("StudentName", selectedStudent.getsName());
                map.put("Payment", payment);

                if (selectedActivity.getAID() == -1) {
                    map.put("seeActivity", true);
                } else {
                    map.put("seeActivity", false);
                }
                map.put("Activity", selectedActivity.getaName());

                map.put("seeYear", false);
                map.put("Year_", cboxYear.getSelectionModel().getSelectedItem().toString());

                if (selectedMonth.getValue() == -1) {
                    map.put("seeMonth", true);
                } else {
                    map.put("seeMonth", false);
                }
                map.put("month", selectedMonth.toString());


                JasperPrint jasperPrint = JasperFillManager.fillReport(paymentReport, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Student payment");
//                progress.close();
            } catch (Exception e) {
                Logger.getLogger(StudentPaymentController.class.getName()).log(Level.SEVERE, null, e);

            }
        } else {
            OptionPane.showError("Please select a student to print.");
        }
    }

}
