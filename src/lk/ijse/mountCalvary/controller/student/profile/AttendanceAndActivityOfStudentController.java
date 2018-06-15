package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.AttendantSheetBO;
import lk.ijse.mountCalvary.business.custom.RegistrationBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.DateRange;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.Reporter;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.AttendantSheetDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceAndActivityOfStudentController implements Initializable {

    private static JasperReport attendanceOfStudentReport;
    @FXML
    private AnchorPane attendanceAndActivityOfStudent;
    @FXML
    private TableView<RegistrationDTO> tblRegistration;
    @FXML
    private TableColumn<RegistrationDTO, String> colActivity_tblRegistration;
    @FXML
    private TableColumn<RegistrationDTO, Date> colJoinedDate_tblRegistration;
    @FXML
    private TableView<AttendantSheetDTO> tblAttendantSheet;
    @FXML
    private TableColumn<AttendantSheetDTO, Date> colDate_tblAttendantSheet;
    @FXML
    private TableColumn<AttendantSheetDTO, String> colActivity_tblAttendantSheet;
    @FXML
    private TableColumn<AttendantSheetDTO, String> colTeacherInCharge_tblAttendantSheet;
    @FXML
    private JFXComboBox<String> cboxTimeRange;
    private studentProfileController studentProfileController;
    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;
    private ObservableList<RegistrationDTO> registrationDTOS;
    private RegistrationBO registrationBOImpl;
    private AttendantSheetBO attendantSheetBOImpl;
    private ObservableList<AttendantSheetDTO> allAttendance;
    private String[] ageGroup = DateRange.getDateRanges();
    private StudentDTO selectedStudent;
    private JasperReport activityOfStudentReport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);

        colJoinedDate_tblRegistration.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        colActivity_tblRegistration.setCellValueFactory(new PropertyValueFactory<>("activityName"));

        colActivity_tblAttendantSheet.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colDate_tblAttendantSheet.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTeacherInCharge_tblAttendantSheet.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        registrationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.REGISTRATION);
        attendantSheetBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANT_SHEET);
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        filterData();
    }

    @FXML
    void cboxTimeRange_onAction(ActionEvent event) {
        filterData();
    }

    private void filterData() {
        try {
            int timeRange = cboxTimeRange.getSelectionModel().getSelectedIndex();
            int act = cboxActivity.getSelectionModel().getSelectedItem().getAID();
            ArrayList<AttendantSheetDTO> filter = new ArrayList<>();
            switch (timeRange) {
                case DateRange.IN_THIS_WEEK: {
                    searchMethod(DateRange.Range.WEEK, filter, act);
                    break;
                }
                case DateRange.IN_THIS_MONTH: {
                    searchMethod(DateRange.Range.MONTH, filter, act);
                    break;
                }
                case DateRange.IN_THIS_YEAR: {
                    searchMethod(DateRange.Range.YEAR, filter, act);
                    break;
                }
                case DateRange.ALL: {
                    searchMethod(DateRange.Range.ALL, filter, act);
                    break;
                }
            }
            tblAttendantSheet.getItems().setAll(filter);
        } catch (NullPointerException e) {
        }
    }

    private void searchMethod(DateRange.Range range, ArrayList<AttendantSheetDTO> filter, int act) {
        for (AttendantSheetDTO oneAttendant : allAttendance) {
            if (DateRange.checkInRange(range, oneAttendant.getDate()) && (act == -1 || act == oneAttendant.getAID())) {
                filter.add(oneAttendant);
            }
        }
    }

    public void init(studentProfileController studentProfileController) {
        this.studentProfileController = studentProfileController;
    }

    public void insertStudentID(StudentDTO i) {
        try {
            selectedStudent = i;
            loadIntoRegistration(i);
        } catch (Exception e) {
            Logger.getLogger(AttendanceAndActivityOfStudentController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void loadIntoRegistration(StudentDTO i) throws Exception {
        ObservableList<RegistrationDTO> registrationForThisStudent = registrationBOImpl.getRegistrationForThisStudent(i.getSID());
        ObservableList<ActivityDTO> activityDTOS = registrationBOImpl.getActivityListForThisStudent(i.getSID());
        ObservableList<AttendantSheetDTO> attendantSheetDTOS = attendantSheetBOImpl.getAttendanceSheetForThisStudent(i.getSID());
        cboxActivity.getItems().removeAll(cboxActivity.getItems());
        cboxActivity.getItems().add(new ActivityDTO(-1, "All"));
        cboxTimeRange.getItems().setAll(ageGroup);
        cboxTimeRange.getSelectionModel().select(0);
        cboxActivity.getItems().addAll(activityDTOS);
        cboxActivity.getSelectionModel().select(0);
        allAttendance = attendantSheetDTOS;
        tblRegistration.getItems().setAll(registrationForThisStudent);
        tblAttendantSheet.getItems().setAll(allAttendance);

    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {

        try {
            if (selectedStudent != null) {
                if (activityOfStudentReport == null) {
                    InputStream activityOfStudentFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/ActivityOfStudentReport.jrxml");
                    activityOfStudentReport = JasperCompileManager.compileReport(activityOfStudentFile);
                }
                HashMap activityMap = new HashMap();
                JRBeanCollectionDataSource activities = new JRBeanCollectionDataSource(tblRegistration.getItems());
                activityMap.put("Activities", activities);
                activityMap.put("StudentID", selectedStudent.getSID());
                activityMap.put("StudentName", selectedStudent.getsName());
                JasperPrint activityPrint = JasperFillManager.fillReport(activityOfStudentReport, activityMap, new JREmptyDataSource());

                /////
                ActivityDTO selectedActivity = cboxActivity.getSelectionModel().getSelectedItem();
                if (attendanceOfStudentReport == null) {
                    InputStream attendanceOfStudentFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/AttendanceOfStudentReport.jrxml");
                    attendanceOfStudentReport = JasperCompileManager.compileReport(attendanceOfStudentFile);
                }

                JRBeanCollectionDataSource attendantSheet = new JRBeanCollectionDataSource(tblAttendantSheet.getItems());
                HashMap attendanceMap = new HashMap();
                attendanceMap.put("StudentID", selectedStudent.getSID());
                attendanceMap.put("StudentName", selectedStudent.getsName());
                attendanceMap.put("Attendance", attendantSheet);
                if (selectedActivity.getAID() == -1) {
                    attendanceMap.put("seeActivity", true);
                }
                attendanceMap.put("ActivityName", selectedActivity.getaName());
                attendanceMap.put("TimeRange", cboxTimeRange.getSelectionModel().getSelectedItem());
                JasperPrint attendancePrint = JasperFillManager.fillReport(attendanceOfStudentReport, attendanceMap, new JREmptyDataSource());
                List pages = attendancePrint.getPages();
                for (int j = 0; j < pages.size(); j++) {
                    JRPrintPage object = (JRPrintPage) pages.get(j);
                    activityPrint.addPage(object);
                }
                Reporter.showReport(activityPrint, "Attendance and activity of student");

            } else {
                Common.showError("Please select a student to print.");
            }
        } catch (Exception e) {
            Logger.getLogger(AttendanceAndActivityOfStudentController.class.getName()).log(Level.SEVERE, null, e);

        }

    }
}
