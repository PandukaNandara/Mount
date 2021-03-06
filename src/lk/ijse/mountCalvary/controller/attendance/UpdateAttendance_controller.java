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
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.AttendantSheetBO;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.basic.MainMenuFrame_controller;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.AttendantSheetDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public final class UpdateAttendance_controller extends SuperController implements Initializable {

    @FXML
    private VBox acUpdateAttendance;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private JFXComboBox<TeacherDTO> cboxTeacherInCharge;

    @FXML
    private JFXDatePicker dpDate;

    @FXML
    private TableView<RegistrationDTO> tblStudentList;

    @FXML
    private TableColumn<RegistrationDTO, String> colClass;

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

    @FXML
    private TableColumn<AttendantSheetDTO, Date> colDate;


    private ActivityBO activityBOImpl;
    private TeacherBO teacherBOImpl;
    private AttendantSheetBO attendantSheetBOImpl;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acUpdateAttendance);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);
        attendantSheetBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANT_SHEET);

        colStudentList.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("studentClass"));

        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colTeacherInCharge.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colActivity.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblStudentList.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        try {
            loadActivity();
            loadTeacherInCharge();
        } catch (Exception e) {
            callLogger(e);


        }
    }

    private void loadTeacherInCharge() throws Exception {
        cboxTeacherInCharge.getItems().setAll(teacherBOImpl.getAllTeacher());
    }

    private void loadActivity() throws Exception {
        ArrayList<ActivityDTO> activityWithStudent = activityBOImpl.getAllActivity();
        cboxActivity.getItems().setAll(activityWithStudent);
    }

    @FXML
    void btAddAll_onAction(ActionEvent event) {
        Date day = Common.localDateToDate(dpDate.getValue());
        ObservableList<RegistrationDTO> selectedItems = tblStudentList.getSelectionModel().getSelectedItems();
        System.out.println("@@@@@@@@@@@@@ selectedItems = " + selectedItems);
        TeacherDTO selectedTeacher = cboxTeacherInCharge.getSelectionModel().getSelectedItem();
        if (day == null) {
            OptionPane.showErrorAtSide("Please enter the date");
        } else if (selectedItems.size() == 0) {
            OptionPane.showErrorAtSide("Please select students");

        } else if (selectedTeacher == null) {
            OptionPane.showErrorAtSide("Please select the teacher in charge");
        } else {
            for (RegistrationDTO oneReg : selectedItems) {
                System.out.println("OneReg + = " + oneReg);
                tblAttendance.getItems().add(new AttendantSheetDTO(
                        oneReg, selectedTeacher, day
                ));

                try {

                    tblStudentList.getItems().remove(oneReg);
                    System.out.println("tblStudentList.getItems() = " + tblStudentList.getItems());
//                    cboxActivity.getSelectionModel().getSelectedItem().getRegistrationDTOS().remove(oneReg);
                } catch (NullPointerException e) {
                    System.err.println(e);
                }
            }
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askWarning("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdateAttendance, this);
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
            OptionPane.showErrorAtSide("Please select an Student to remove");
        }
    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        ObservableList<AttendantSheetDTO> attendantSheetDTOS = tblAttendance.getItems();
        if (attendantSheetDTOS.size() == 0) {
            OptionPane.showErrorAtSide("Please add some attendant detail");
        } else {
            if (OptionPane.askQuestion("Do you want to update attendance?")) {
                try {
                    if (attendantSheetBOImpl.saveAllAttendantSheet(attendantSheetDTOS)) {
                        OptionPane.showDoneAtSide("Attendant sheet successfully updated");
                        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acUpdateAttendance, this);
                    }
                } catch (Exception e) {
                    callLogger(e);


                }
            }
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        try {
            tblStudentList.getItems().setAll(activityBOImpl.getRegistrationOfThisActivity(cboxActivity.getSelectionModel().getSelectedItem().getAID()));
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    void cboxTeacherInCharge_onAction(ActionEvent event) {
    }

    @FXML
    void dpDate_onAction(ActionEvent event) {
    }
}
