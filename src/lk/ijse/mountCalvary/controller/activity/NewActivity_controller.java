package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.*;
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
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.entity.Gender;
import lk.ijse.mountCalvary.model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewActivity_controller implements Initializable {

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

    private TeacherBO teacherBOImpl;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private VBox acNewActivity;

    private AutoComplete<StudentDTO> studentAuto;

    private ObservableList<StudentDTO> allStudent;

    private ArrayList<TeacherDTO> allTeacher;

    private StudentDTO selectedStudent;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);

        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);

        //Table
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentDTO"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));

        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));
        try {
            allStudent = studentBOImpl.getAllStudentNameAndNumber();
            allTeacher = teacherBOImpl.getAllTeacher();
            studentAuto = new AutoComplete<>(txtStudentName, allStudent);
            studentAuto.setAutoCompletionsAction(event -> setStudentID());

        } catch (Exception e) {
            Logger.getLogger(NewActivity_controller.class.getName()).log(Level.SEVERE, null, e);
        }
        loadTeachers();
    }

    private void setStudentID() {
        try {
            selectedStudent = studentAuto.getSelectedItemByName();
            txtStudentID.setText(String.valueOf(selectedStudent.getSID()));
        } catch (NullPointerException e) {
            OptionPane.showErrorAtSide("Invalid student name or name is not existed");
        } catch (Exception e) {
            Logger.getLogger(NewActivity_controller.class.getName()).log(Level.SEVERE, null, e);
        }
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
            OptionPane.showErrorAtSide("Please enter the event name ");
        } else if (!(male || female)) {
            OptionPane.showErrorAtSide("Please select the gender of the event.");
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
        Date joinDate = Common.localDateToDate(dtJoinedDate.getValue());
        if (joinDate == null) {
            OptionPane.showErrorAtSide("Please enter the joined date");
        } else if (txtStudentName.getText().length() < 1) {
            OptionPane.showErrorAtSide("Please enter the student name");
        } else {
            selectedStudent = studentAuto.getSelectedItemByName();
            if (selectedStudent == null) {
                OptionPane.showErrorAtSide("This student is not a valid student.");
                txtStudentName.selectAll();
                txtStudentName.requestFocus();
            } else {
                allStudent.remove(selectedStudent);
                studentAuto.changeSuggestion(allStudent);
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
        studentAuto.changeSuggestion(allStudent);
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askWarning("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewActivity, this);
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
        if (aName.length() > 0) {
            TeacherDTO teachInCharge = cboxTeacher.getSelectionModel().getSelectedItem();
            if (teachInCharge != null) {
                ObservableList<RegistrationDTO> regList = tblStudentList.getItems();
                ObservableList<EventDTO> evenList = tblEvent.getItems();
                //   System.out.println(regList.toString());
                try {
                    if (evenList.size() == 0) {
                        if (rbtMale.isSelected())
                            evenList.add(new EventDTO("Default " + aName + " event", Gender.MALE));
                        if (rbtFemale.isSelected())
                            evenList.add(new EventDTO("Default " + aName + " event", Gender.FEMALE));
                    }
                    if (activityBOImpl.addActivityWithStudentAndEvent(new ActivityDTO(aName, teachInCharge.getTID(), regList, evenList))) {
                        OptionPane.showDoneAtSide("Activity has successfully added");
                        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewActivity, this);
                    } else {
                        OptionPane.showWarning("Something's wrong we can't do your request");
                    }
                } catch (Exception e) {
                    Logger.getLogger(NewActivity_controller.class.getName()).log(Level.SEVERE, null, e);
                    OptionPane.showWarning("Something's wrong we can't do your request");
                }
            } else {
                OptionPane.showErrorAtSide("Please select the teacher in charge");
            }
        } else {
            OptionPane.showErrorAtSide("The Activity name is incorrect");
        }
    }

    @FXML
    private void txtStudentID_onAction(ActionEvent actionEvent) {
        if (Common.isInteger(txtStudentID.getText())) {
            int id = Integer.parseInt(txtStudentID.getText());
            selectedStudent = studentAuto.searchByID(id);
            if (selectedStudent != null) {
                txtStudentName.setText(selectedStudent.getsName());
                dtJoinedDate.requestFocus();
            } else {
                OptionPane.showErrorAtSide("No suitable student for this student ID.");
            }
        } else {
            OptionPane.showErrorAtSide("Student ID is invalid.");
            txtStudentID.selectAll();
        }

    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {
        btAdd_Student.fire();
    }

    @FXML
    void btRemove_tblEvent_onAction(ActionEvent event) {
        Common.removeItemFromTable(tblEvent);
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
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        dtJoinedDate.requestFocus();
    }
}
