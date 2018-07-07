package lk.ijse.mountCalvary.controller.activity.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.mountCalvary.controller.DateRange;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.OptionPane;
import lk.ijse.mountCalvary.controller.Reporter;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JoinedStudentController implements Initializable {

    private static JasperReport joinedStudentReport;
    ArrayList<RegistrationDTO> allJoinedStudent;
    @FXML
    private VBox JoinedStudent;
    @FXML
    private TableView<RegistrationDTO> tblStudentList;
    @FXML
    private TableColumn<RegistrationDTO, String> colStudent;
    @FXML
    private TableColumn<RegistrationDTO, Date> colJoinedDate;
    @FXML
    private JFXComboBox<String> cboxTimeRange;
    @FXML
    private TableColumn<RegistrationDTO, String> colClass;
    private ActivityProfileController activityProfileController;
    private ObservableList<RegistrationDTO> registrationOfThisActivity;
    private ActivityBO activityBOImpl;
    @FXML
    private JFXButton btPrint;
    private ActivityDTO selectedActivity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);

        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("studentClass"));

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);

    }

    public void init(ActivityProfileController activityProfileController) {
        this.activityProfileController = activityProfileController;
    }

    public void insertActivity(ActivityDTO activityDTO) {
        try {
            selectedActivity = activityDTO;
            registrationOfThisActivity = activityBOImpl.getRegistrationOfThisActivity(activityDTO.getAID());
            cboxTimeRange.getSelectionModel().select(DateRange.ALL);
            tblStudentList.getItems().setAll(registrationOfThisActivity);
            cboxTimeRange.getItems().setAll(DateRange.getDateRanges());
            cboxTimeRange.getSelectionModel().select(0);
        } catch (Exception e) {
            Logger.getLogger(JoinedStudentController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    private void cboxTimeRange_onAction(ActionEvent actionEvent) {
        filterData();
    }

    private void filterData() {
        int timeRange = cboxTimeRange.getSelectionModel().getSelectedIndex();
        int reg = -1;
        ArrayList<RegistrationDTO> filter = new ArrayList<>();
        switch (timeRange) {
            case DateRange.IN_THIS_WEEK: {
                searchMethod(DateRange.Range.WEEK, filter);
                break;
            }
            case DateRange.IN_THIS_MONTH: {
                searchMethod(DateRange.Range.MONTH, filter);
                break;
            }
            case DateRange.IN_THIS_YEAR: {
                searchMethod(DateRange.Range.YEAR, filter);
                break;
            }
            case DateRange.ALL: {
                searchMethod(DateRange.Range.ALL, filter);
                break;
            }
        }
        tblStudentList.getItems().setAll(filter);
    }

    private void searchMethod(DateRange.Range range, ArrayList<RegistrationDTO> filter) {
        for (RegistrationDTO oneRegi : registrationOfThisActivity) {
            if (DateRange.checkInRange(range, oneRegi.getJoinedDate())) {
                filter.add(oneRegi);
            }
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        if (selectedActivity != null) {
            try {
                if (joinedStudentReport == null) {
                    InputStream attendanceOfStudentFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/activity/JoinedStudentReport.jrxml");
                    joinedStudentReport = JasperCompileManager.compileReport(attendanceOfStudentFile);
                }
                JRBeanCollectionDataSource joinedStudent = new JRBeanCollectionDataSource(tblStudentList.getItems());
                HashMap joinedStudentMap = new HashMap();
                joinedStudentMap.put("ActivityName", selectedActivity.getaName());
                joinedStudentMap.put("JoinedStudent", joinedStudent);
                joinedStudentMap.put("TimeRange", cboxTimeRange.getSelectionModel().getSelectedItem());
                JasperPrint attendancePrint = JasperFillManager.fillReport(joinedStudentReport, joinedStudentMap, new JREmptyDataSource());

                Reporter.showReport(attendancePrint, "Joined student");

            } catch (Exception e) {
                Logger.getLogger(JoinedStudentController.class.getName()).log(Level.SEVERE, null, e);

            }
        } else {
            OptionPane.showError("Please select an activity to print.");
        }
    }
}
