package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.*;
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
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class NewActivity_controller implements Initializable {

    ArrayList<StudentDTO> allStudent;
    ArrayList<TeacherDTO> allTeacher;
    @FXML
    private TableView<EventDTO> tblEvent;
    @FXML
    private TableColumn<EventDTO, String> colEventName;
    @FXML
    private TableColumn<EventDTO, String> colGender;
    @FXML
    private JFXButton btRemove_tblEvent;
    @FXML
    private JFXTextField txtActivityName;
    @FXML
    private JFXComboBox<TeacherDTO> cboxTeacher;
    @FXML
    private JFXButton btRemove_tblStudentList1;
    @FXML
    private TableView<RegistrationDTO> tblStudentList;
    @FXML
    private TableColumn<RegistrationDTO, String> colStudentName;
    @FXML
    private TableColumn<RegistrationDTO, Date> colJoinedDate;
    @FXML
    private JFXButton btAdd_Event;
    @FXML
    private JFXDatePicker dtJoinedDate;
    @FXML
    private JFXButton btRemove_tblStudentList;
    @FXML
    private JFXTextField txtEventName;
    @FXML
    private JFXRadioButton rbtMale;
    @FXML
    private JFXRadioButton rbtFemale;
    @FXML
    private JFXButton btAdd_Student;
    @FXML
    private JFXButton btFinish;
    @FXML
    private JFXButton btCancel;
    private StudentBO studentBOImpl;
    private ActivityBO activityBOImpl;
    private RegistrationBO regBOImpl;
    private TeacherBO teacherBOImpl;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private AnchorPane acNewActivity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        regBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.REGISTRATION);
        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);

        //Table
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentDTO"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));

        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));
        try {
            allStudent = studentBOImpl.getAll();
            allTeacher = teacherBOImpl.getAllTeacher();
            AutoCompletionBinding<StudentDTO> studentDTOAutoCompletionBind = TextFields.bindAutoCompletion(txtStudentName, allStudent);
            studentDTOAutoCompletionBind.setOnAutoCompleted(event -> {
                try {
                    txtStudentID.setText(Common.searchStudent(txtStudentName.getText(), allStudent).getSID() + "");
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadTeachers();
    }

    private void loadTeachers() {
        cboxTeacher.getItems().addAll(allTeacher);
    }



    @FXML
    void btAdd_Event_onAction(ActionEvent event) {
        String event_name = txtEventName.getText();
        boolean male = rbtMale.isSelected();
        boolean female = rbtFemale.isSelected();
        if (event_name.length() < 2) {
            Common.showError("Please enter the event name ");
        } else if (!(male || female)) {
            Common.showError("Please select the gender of the event " + male + "  " + female);
        } else {
            if (male) {
                tblEvent.getItems().add(new EventDTO(event_name, EventDTO.MALE));
            }
            if (female) {
                tblEvent.getItems().add(new EventDTO(event_name, EventDTO.FEMALE));
            }
            System.out.println(tblEvent.getItems().toString());
        }
    }

    @FXML
    void btAdd_Student_onAction(ActionEvent event) {
        Date joinDate = Common.LocalDateToDate(dtJoinedDate.getValue());
        if (joinDate == null) {
            Common.showError("Please enter the joined date");
        } else if (txtStudentName.getText().length() < 1) {
            Common.showError("Please enter the student name");
        } else {
            StudentDTO selectedStudent = Common.searchStudent(txtStudentName.getText().trim(), allStudent);
            if (selectedStudent == null) {
                Common.showError("This student is already added to the activity or not a correct valid student.");
                txtStudentName.selectAll();
            } else {
                allStudent.remove(selectedStudent);
                tblStudentList.getItems().add(new RegistrationDTO(selectedStudent, joinDate));
                txtStudentName.setText("");
                txtStudentID.setText("");
            }
        }
    }

    @FXML
    void btRemove_tblStudentList_onAction(ActionEvent event) {
        Object o = Common.removeItemFromTable(tblStudentList);
        allStudent.add(((RegistrationDTO) o).getStudentDTO());

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = Common.askWarning("Do you want to cancel?");
        if (answer) {
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewActivity, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void txtEventName_onAction(ActionEvent event) {
        btAdd_Event.fire();
    }

    @FXML
    void btFinish_onAction(ActionEvent event) {
        addActivity();
    }

    private void addActivity() {
        String aName = txtActivityName.getText();
        if(aName.length() > 0){
            TeacherDTO teachInCharge = cboxTeacher.getSelectionModel().getSelectedItem();
            if(teachInCharge != null){
                ObservableList<RegistrationDTO> regList = tblStudentList.getItems();
                ObservableList<EventDTO> evenList = tblEvent.getItems();
             //   System.out.println(regList.toString());
                try {
                    if(activityBOImpl.addActivityWithStudentAndEvent(new ActivityDTO(aName, teachInCharge.getTID(), regList, evenList))){
                        Common.showMessage("Activity has successfully added");
                        try {
                            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewActivity, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Common.showWarning("Something's wrong we can't do your request");
                    }
                } catch (Exception e) {
                    Common.showWarning("Something's wrong we can't do your request");
                    e.printStackTrace();
                }
            }else{
                Common.showError("Please select the teacher in charge");
            }
        }else{
            Common.showError("The Activity name is incorrect");
        }
    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {

    }

    @FXML
    void btPhysicalActivity_onAction(ActionEvent event) {

    }

    @FXML
    void btRemove_tblEvent_onAction(ActionEvent event) {
        Common.removeItemFromTable(tblEvent);
    }


    @FXML
    void btStudentID_onAction(ActionEvent event) {

    }

    @FXML
    void cboxStudent(ActionEvent event) {

    }

    @FXML
    void cboxTeacher_onAction(ActionEvent event) {

    }

    @FXML
    void rbtFemale_onAction(ActionEvent event) {

    }

    @FXML
    void rbtMale_onAction(ActionEvent event) {

    }

    @FXML
    void rbxNonPhysicalActivity_onAction(ActionEvent event) {

    }

    @FXML
    void txtActivityName_onAction(ActionEvent event) {

    }


    @FXML
    private void txtStudentID_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        dtJoinedDate.requestFocus();
    }
}
