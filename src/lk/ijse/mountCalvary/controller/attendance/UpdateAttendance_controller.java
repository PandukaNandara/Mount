package lk.ijse.mountCalvary.controller.attendance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.AttendantSheetBO;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.AttendantSheetDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateAttendance_controller implements Initializable {

    @FXML
    private AnchorPane acUpdateAttendance;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private JFXComboBox<TeacherDTO> cboxTeacherInCharge;

    @FXML
    private JFXDatePicker dpDate;

    @FXML
    private TableView<RegistrationDTO> tblStudentList;

    @FXML
    private TableColumn<RegistrationDTO, String> colStudentList;

    @FXML
    private TableView<AttendantSheetDTO> tblAttendance;

    @FXML
    private TableColumn<AttendantSheetDTO, String> colStudent;

    @FXML
    private TableColumn<AttendantSheetDTO, String> colActivity;

    @FXML
    private TableColumn<AttendantSheetDTO, String> colTeacherInCharge;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXButton btUpdate;

    @FXML
    private JFXButton btRemove;

    @FXML
    private JFXButton btAddAll;

    private ActivityBO activityBOImpl;
    private TeacherBO teacherBOImpl;
    private AttendantSheetBO attendantSheetBOImpl;
    @FXML
    private TableColumn<AttendantSheetDTO, Date> colDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);
        attendantSheetBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANT_SHEET);

        colStudentList.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colTeacherInCharge.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colActivity.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblStudentList.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        try {
            loadActivityWithStudent();
            loadTeacherInCharge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeacherInCharge() throws Exception {
        cboxTeacherInCharge.getItems().setAll(teacherBOImpl.getAllTeacher());
    }

    private void loadActivityWithStudent() throws Exception {
        ArrayList<ActivityDTO> activityWithStudent = activityBOImpl.getActivityWithStudent();
        cboxActivity.getItems().setAll(activityWithStudent);
    }

    @FXML
    void btAddAll_onAction(ActionEvent event) {
        Date day = Common.LocalDateToDate(dpDate.getValue());
        ObservableList<RegistrationDTO> selectedItems = tblStudentList.getSelectionModel().getSelectedItems();
        TeacherDTO selectedTeacher = cboxTeacherInCharge.getSelectionModel().getSelectedItem();
        if (day == null) {
            Common.showError("Please enter the date");
        } else if (selectedItems.size() == 0) {
            Common.showError("Please select students");

        } else if (selectedTeacher == null) {
            Common.showError("Please select the teacher in charge");
        } else {
            for (RegistrationDTO oneReg : selectedItems) {
                System.out.println(oneReg.hashCode());
                tblAttendance.getItems().add(new AttendantSheetDTO(
                        oneReg, selectedTeacher, day
                ));
                tblStudentList.getItems().remove(oneReg);
                cboxActivity.getSelectionModel().getSelectedItem().getRegistrationDTOS().remove(oneReg);
            }
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = Common.askWarning("Do you want to cancel?");
        if (answer) {
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenuFrame.fxml", this.acUpdateAttendance, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btRemove_onAction(ActionEvent event) {
        AttendantSheetDTO removeItem = (AttendantSheetDTO) Common.removeItemFromTable(tblAttendance);
        if (removeItem != null) {
            cboxActivity.getSelectionModel().select(removeItem.getRegistrationDTO().getActivity());
            removeItem.getRegistrationDTO().getActivity().getRegistrationDTOS().add(removeItem.getRegistrationDTO());
            tblStudentList.getItems().add(removeItem.getRegistrationDTO());
        } else {
            Common.showError("Please select an Student to remove");
        }
    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        ObservableList<AttendantSheetDTO> attendantSheetDTOS = tblAttendance.getItems();
        if (attendantSheetDTOS.size() == 0) {
            Common.showError("Please add some attendant detail");
        } else {
            if (Common.askQuestion("Do you want to update attendance?")) {
                try {
                    if (attendantSheetBOImpl.saveAllAttendantSheet(attendantSheetDTOS)) {
                        Common.showMessage("Attendant sheet successfully updated");

                        try {
                            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenuFrame.fxml", this.acUpdateAttendance, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        tblStudentList.getItems().setAll(cboxActivity.getSelectionModel().getSelectedItem().getRegistrationDTOS());
    }

    @FXML
    void cboxTeacherInCharge_onAction(ActionEvent event) {
    }

    @FXML
    void dpDate_onAction(ActionEvent event) {
    }
}
