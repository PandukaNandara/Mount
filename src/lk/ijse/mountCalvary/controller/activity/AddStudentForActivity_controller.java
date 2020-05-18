package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import lk.ijse.mountCalvary.business.custom.RegistrationBO;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.tool.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public final class AddStudentForActivity_controller extends SuperController implements Initializable {
    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private VBox acAddStudent;

    @FXML
    private TableView<RegistrationDTO> tblStudentList;
    @FXML
    private TableColumn colStudentID;
    @FXML
    private TableColumn<RegistrationDTO, String> colStudentName;

    @FXML
    private TableColumn<RegistrationDTO, Date> colJoinedDate;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXDatePicker dtJoinedDate;

    @FXML
    private JFXTextField txtStudentID;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private JFXButton btRemove;

    private ActivityBO activityBOImpl;
    private RegistrationBO registrationBOImpl;
    private StudentBO studentBOImpl;

    private AutoComplete<StudentDTO> autoComplete;
    private boolean firstTime = true;
    private ObservableList<StudentDTO> allStudent;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    private StudentDTO selectedStudent;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acAddStudent);

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        registrationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.REGISTRATION);
        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        autoComplete = new AutoComplete<>(txtStudentName);

        try {
            loadActivityWithStudent();
        } catch (Exception e) {
            callLogger(e);
        }

    }

    private void loadActivityWithStudent() throws Exception {
        ArrayList<ActivityDTO> allActivity = activityBOImpl.getAllActivity();
        cboxActivity.getItems().addAll(allActivity);
    }

    @FXML
    void btAdd_onAction(ActionEvent event) {
        Date joinDate = Common.localDateToDate(dtJoinedDate.getValue());
        if (joinDate == null) {
            if (firstTime) {
                firstTime = false;
            } else {
                OptionPane.showErrorAtSide("Please enter the joined date.");
            }
        } else if (selectedStudent == null) {
            OptionPane.showErrorAtSide("Please select a student.");
        } else {
            allStudent.remove(selectedStudent);
            autoComplete.changeSuggestion(allStudent);
            int AID = cboxActivity.getSelectionModel().getSelectedItem().getAID();
            RegistrationDTO newReg = new RegistrationDTO(selectedStudent, AID, joinDate);
            newReg.setNewOne(true);
            tblStudentList.getItems().add(newReg);
            txtStudentName.clear();
            txtStudentID.clear();
        }

    }

    @FXML
    void btRemove_onAction(ActionEvent event) {
        try {
            RegistrationDTO selectedItem = tblStudentList.getSelectionModel().getSelectedItem();
            if (selectedItem.isNewOne()) {
                Object o = Common.removeItemFromTable(tblStudentList);
                allStudent.add(((RegistrationDTO) o).getStudentDTO());
                autoComplete.changeSuggestion(allStudent);
            } else OptionPane.showWarningAtSide("This student has been already added.");

        } catch (NullPointerException e) {
            OptionPane.showErrorAtSide("Please select item.");
        }
    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        try {
            ObservableList<RegistrationDTO> allRegistrationDTOS = tblStudentList.getItems();
            ObservableList<RegistrationDTO> newRegs = FXCollections.observableArrayList();
            for (RegistrationDTO one :
                    allRegistrationDTOS) {
                if (one.isNewOne())
                    newRegs.add(one);
            }
            if (OptionPane.askQuestion("Do you want to add new students?"))
                if (registrationBOImpl.addAllRegistration(newRegs)) {
                    OptionPane.showDoneAtSide("New Registration successfully added.");
                    screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml",
                            this.acAddStudent, this);
                } else {
                    OptionPane.showWarning("Somethings wrong");
                }
        } catch (Exception e) {
            callLogger(e);
            OptionPane.showWarning("Something's wrong we can't do your request");

        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        try {
            ActivityDTO selectActivity = cboxActivity.getSelectionModel().getSelectedItem();
            allStudent = studentBOImpl.getStudentNotDoThisActivity(
                    selectActivity.getAID());
            ObservableList<RegistrationDTO> registrationOfThisActivity
                    = activityBOImpl.getRegistrationOfThisActivity(selectActivity.getAID());
            tblStudentList.getItems().setAll(registrationOfThisActivity);
            autoComplete.changeSuggestion(allStudent);
            autoComplete.setAutoCompletionsAction(event1 -> {
                StudentDTO studentDTO = autoComplete.getSelectedItemByName();
                txtStudentID.setText(String.valueOf(studentDTO.getSID()));
            });
            cboxActivity.setDisable(true);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        selectedStudent = autoComplete.getSelectedItemByName();
        if (selectedStudent == null) {
            OptionPane.showErrorAtSide("The student name is not available or already added.");
        }
        btAdd.fire();
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askQuestion("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml",
                    this.acAddStudent, this);
        }
    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {
    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        if (Common.isInteger(txtStudentID.getText())) {
            selectedStudent = autoComplete.searchByID(txtStudentID.getText());
            if (selectedStudent == null)
                OptionPane.showErrorAtSide("Student ID might be already added.");
            else {
                txtStudentName.setText(selectedStudent.getSName());
                txtStudentName.selectAll();
                txtStudentName.requestFocus();
            }
        } else
            OptionPane.showErrorAtSide("Student ID is invalid.");
    }

}
