package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import lk.ijse.mountCalvary.business.custom.RegistrationBO;
import lk.ijse.mountCalvary.controller.AutoComplete;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lk.ijse.mountCalvary.controller.basic.ScreenLoader.loadPanel;


public class AddStudentForActivity_controller implements Initializable {
    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private AnchorPane acAddStudent;

    @FXML
    private TableView<RegistrationDTO> tblStudentList;

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

    private AutoComplete<StudentDTO> autoComplete;
    private boolean firstTime = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        registrationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.REGISTRATION);

        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        autoComplete = new AutoComplete<>(txtStudentName);
        try {
            loadActivityWithStudent();
        } catch (Exception e) {
            Logger.getLogger(AddStudentForActivity_controller.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    private void loadActivityWithStudent() throws Exception {
        ArrayList<ActivityDTO> allActivity = activityBOImpl.getActivityWithStudentNotDoThis();
        cboxActivity.getItems().addAll(allActivity);
    }

    @FXML
    void btAdd_onAction(ActionEvent event) {

        Date joinDate = Common.LocalDateToDate(dtJoinedDate.getValue());
        if (joinDate == null) {
            if (firstTime) {
                firstTime = false;
            } else {
                Common.showError("Please enter the joined date");
            }

        } else if (txtStudentName.getText().length() < 1) {
            Common.showError("Please enter the student name");
        } else {
            ObservableList<StudentDTO> allStudent = (cboxActivity.getSelectionModel().getSelectedItem()).getStudentDTOS();
            StudentDTO selectedStudent = Common.searchStudent(txtStudentName.getText().trim(), allStudent);

            if (selectedStudent == null) {
                Common.showError("This student is already added to the activity or not a correct valid student.");
                txtStudentName.selectAll();
            } else {
                allStudent.remove(selectedStudent);

                autoComplete.changeSuggestion(allStudent);

                int AID = cboxActivity.getSelectionModel().getSelectedItem().getAID();
                tblStudentList.getItems().add(new RegistrationDTO(selectedStudent, AID, joinDate));
                txtStudentName.setText("");
                txtStudentID.setText("");
            }
        }

    }

    @FXML
    void btRemove_onAction(ActionEvent event) {
        try {
            Object o = Common.removeItemFromTable(tblStudentList);
            ObservableList allStudent = cboxActivity.getSelectionModel().getSelectedItem().getStudentDTOS();

            allStudent.add(((RegistrationDTO) o).getStudentDTO());
            autoComplete.changeSuggestion(allStudent);

        } catch (Exception e) {
            Common.showError("Please select a row");
        }
    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        try {
            if (registrationBOImpl.addAllRegistration(tblStudentList.getItems())) {
                Common.showMessage("Registration successful");
                try {
                    loadPanel("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acAddStudent, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Common.showWarning("Somethings wrong");
            }
        } catch (Exception e) {
            Logger.getLogger(AddStudentForActivity_controller.class.getName()).log(Level.SEVERE, null, e);
            Common.showWarning("Something's wrong we can't do your request");
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        ActivityDTO select = cboxActivity.getSelectionModel().getSelectedItem();
        ObservableList<StudentDTO> stList = select.getStudentDTOS();
        autoComplete.changeSuggestion(stList);
        autoComplete.setAutoCompletionsAction(event1 -> {
            StudentDTO studentDTO = Common.searchStudent(txtStudentName.getText().trim(), cboxActivity.getSelectionModel().getSelectedItem().getStudentDTOS());
            txtStudentID.setText(studentDTO.getSID() + "");
        });
        cboxActivity.setDisable(true);
    }

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        performAdd();
    }

    private void performAdd() {
        StudentDTO studentDTO = Common.searchStudent(txtStudentName.getText().trim(), cboxActivity.getSelectionModel().getSelectedItem().getStudentDTOS());
        try {
            txtStudentID.setText(studentDTO.getSID() + "");
        } catch (Exception e) {
        }
        btAdd.fire();
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = Common.askQuestion("Do you want to cancel?");
        if (answer) {
            try {
                loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acAddStudent, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {

    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {

    }

}
