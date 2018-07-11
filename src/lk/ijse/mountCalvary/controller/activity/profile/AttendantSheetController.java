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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.AttendantSheetBO;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.AttendantSheetDTO;
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

public class AttendantSheetController implements Initializable {

    private static JasperReport attendanceOfStudentReport;
    @FXML
    private VBox attendantSheet;
    @FXML
    private TableView<AttendantSheetDTO> tblAttendantSheet;
    @FXML
    private TableColumn<AttendantSheetDTO, String> colStudentName;
    @FXML
    private TableColumn<AttendantSheetDTO, String> colTeacherInCharge;
    @FXML
    private TableColumn<AttendantSheetDTO, Date> colDate;
    @FXML
    private JFXComboBox<String> cboxTimeRange;
    @FXML
    private JFXTextField txtStudent;
    @FXML
    private JFXButton btPrint;
    @FXML
    private JFXToggleButton btInverse;

    private ActivityProfileController activityProfileController;
    private AttendantSheetBO attendantSheetBOImpl;
    private ActivityBO activityBOImpl;
    private ArrayList<ActivityDTO> allActivity;
    private AutoComplete<RegistrationDTO> autoComplete;
    private ObservableList<RegistrationDTO> allRegistration;
    private ObservableList<AttendantSheetDTO> attendanceSheet;
    private ActivityDTO selectedActivity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(attendantSheet);
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colTeacherInCharge.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        attendantSheetBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANT_SHEET);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        autoComplete = new AutoComplete<>(txtStudent);
        autoComplete.setAutoCompletionsAction(event -> filterData());

        btInverse.setDisable(true);
    }

    public void init(ActivityProfileController activityProfileController) {
        this.activityProfileController = activityProfileController;
        allActivity = activityProfileController.getAllActivity();
    }

    @FXML
    void cobxTimeRange_onAction(ActionEvent event) {
        filterData();
    }

    private void filterData() {
        int timeRange = cboxTimeRange.getSelectionModel().getSelectedIndex();

        //This is for disable or enable the inverse button();
        if (timeRange == DateRange.ALL) {
            btInverse.setDisable(true);
        } else {
            btInverse.setDisable(false);
        }
        btInverse.setSelected(false);
        int reg;
        try {
            reg = autoComplete.getSelectedItemByName().getRID();
        } catch (NullPointerException e) {
            reg = -1;
        }
        ArrayList<AttendantSheetDTO> filter = new ArrayList<>();
        switch (timeRange) {
            case DateRange.IN_THIS_WEEK: {
                searchMethod(DateRange.Range.WEEK, filter, reg);
                break;
            }
            case DateRange.IN_THIS_MONTH: {
                searchMethod(DateRange.Range.MONTH, filter, reg);
                break;
            }
            case DateRange.IN_THIS_YEAR: {
                searchMethod(DateRange.Range.YEAR, filter, reg);
                break;
            }
            case DateRange.ALL: {
                searchMethod(DateRange.Range.ALL, filter, reg);
                break;
            }
        }
        tblAttendantSheet.getItems().setAll(filter);

    }

    private void searchMethod(DateRange.Range range, ArrayList<AttendantSheetDTO> filter, int act) {
        for (AttendantSheetDTO oneAttendant : attendanceSheet) {
            if (DateRange.checkInRange(range, oneAttendant.getDate())) {
                if (act == -1 || act == oneAttendant.getRID()) {
                    filter.add(oneAttendant);
                }
            }
        }
    }

    public void insertActivity(ActivityDTO activityDTO) {
        try {
            selectedActivity = activityDTO;
            attendanceSheet = attendantSheetBOImpl.getAttendanceSheetForThisActivity(activityDTO.getAID());
            allRegistration = activityBOImpl.getRegistrationOfThisActivity(activityDTO.getAID());
            cboxTimeRange.getItems().setAll(DateRange.getDateRanges());
            tblAttendantSheet.getItems().setAll(attendanceSheet);
            cboxTimeRange.getSelectionModel().select(DateRange.ALL);
            autoComplete.changeSuggestion(allRegistration);

        } catch (Exception e) {
            Logger.getLogger(AttendantSheetController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    private void txtStudent_onAction(ActionEvent actionEvent) {
        filterData();
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        if (selectedActivity != null) {
            try {
                Progress.showMessage(attendantSheet, "Loading report", "Now loading");

                String studentName = txtStudent.getText();
                if (attendanceOfStudentReport == null) {
                    InputStream attendanceOfStudentFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/activity/AttendanceOfActivityReport.jrxml");
                    attendanceOfStudentReport = JasperCompileManager.compileReport(attendanceOfStudentFile);

                }
                JRBeanCollectionDataSource attendantSheet = new JRBeanCollectionDataSource(tblAttendantSheet.getItems());
                HashMap attendanceMap = new HashMap();
                attendanceMap.put("Attendance", attendantSheet);
                if (studentName.length() < 2) {
                    attendanceMap.put("seeStudent", true);
                }
                attendanceMap.put("StudentName", studentName.length() < 2 ? "All" : studentName);
                attendanceMap.put("ActivityName", selectedActivity.getaName());
                attendanceMap.put("TimeRange", cboxTimeRange.getSelectionModel().getSelectedItem());

                JasperPrint attendancePrint = JasperFillManager.fillReport(attendanceOfStudentReport, attendanceMap, new JREmptyDataSource());
                Reporter.showReport(attendancePrint, "Attendance sheet");

                Progress.hide();
            } catch (Exception e) {
                Logger.getLogger(AttendantSheetController.class.getName()).log(Level.SEVERE, null, e);

            }
        } else {
            OptionPane.showErrorAtSide("Please select an activity to print.");
        }

    }

    /**
     * We are going to get inverse data from the tblAttendantSheet
     * first we check whether the toggle button is select or not.
     * Then we iterate all registration for this activity and secondly
     * we iterate present attendantSheetDTO that shown in tblAttendantSheet.
     */
    @FXML
    private void btInverse_onAction(ActionEvent actionEvent) {

        boolean isSelected = btInverse.isSelected();
        if (isSelected) try {
            ArrayList<AttendantSheetDTO> inverseFilter = new ArrayList<>();
            ObservableList<AttendantSheetDTO> present = tblAttendantSheet.getItems();
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
                else
                    inverseFilter.add(new AttendantSheetDTO().setStudentName(registrations.get(i).getStudentName()));
            }
            tblAttendantSheet.getItems().setAll(inverseFilter);
        } catch (Exception e) {
            Logger.getLogger(AttendantSheetController.class.getName()).log(Level.SEVERE, null, e);
        }
        else {
            filterData();
        }
    }
}
