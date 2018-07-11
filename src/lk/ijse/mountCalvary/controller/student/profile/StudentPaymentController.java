package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.PaymentBO;
import lk.ijse.mountCalvary.business.custom.RegistrationBO;
import lk.ijse.mountCalvary.controller.tool.*;
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
    private JFXToggleButton btInverse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acStudentPayment);

        btInverse.setTooltip(new Tooltip(MessageReader.getInstance().getMessage(1)));
        btInverse.setDisable(true);

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

            if (month == Month.ALL) {
                btInverse.setDisable(true);
                btInverse.setSelected(false);
            } else {
                btInverse.setDisable(false);
            }
            boolean isInverseSelected = btInverse.isSelected();


            for (PaymentDTO onePayment : paymentDetailOfThisStudent) {
                if ((AID == -1 || AID == onePayment.getAID()) &&
                        (month == -1 || month == onePayment.getMonth().getValue()) &&
                        (year == onePayment.getYear())
                        )
                    paymentDTOS.add(onePayment);
            }
            tblStudentPayment.getItems().setAll(paymentDTOS);

            if (isInverseSelected) {
                inverseFilter();
            }

        } catch (NullPointerException ignored) {
        }
    }

    private void inverseFilter() {
        boolean isSelected = btInverse.isSelected();
        if (isSelected) try {
            ArrayList<PaymentDTO> inverseFilter = new ArrayList<>();
            //ok
            ObservableList<PaymentDTO> present = tblStudentPayment.getItems();

            //ok
            ObservableList<ActivityDTO> activities = registrationBOImpl.
                    getActivityListForThisStudent(selectedStudent.getSID());


            for (int i = 0; i < activities.size(); i++) {
                boolean shouldRemove = false;
                for (int j = 0; j < present.size(); j++) {
                    if (activities.get(i).getAID() == present.get(j).getAID()) {
                        present.remove(j--);
                        shouldRemove = true;
                    }
                }
                if (shouldRemove)
                    activities.remove(i--);
                else {
                    PaymentDTO paymentDTO = new PaymentDTO();

                    paymentDTO.setAID(activities.get(i).getAID());
                    paymentDTO.setActivityName(activities.get(i).getaName());
                    inverseFilter.add(paymentDTO);
                }

            }

        } catch (Exception e) {
            Logger.getLogger(StudentPaymentController.class.getName()).log(Level.SEVERE, null, e);
        }
        else
            filterPayment();
    }


    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
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
            OptionPane.showErrorAtSide("Please select a student to print.");
        }
    }

    @FXML
    private void btInverse_onAction(ActionEvent actionEvent) {
        inverseFilter();
    }

    @FXML
    void cboxMonth_onAction(ActionEvent event) {
        filterPayment();
    }

}
