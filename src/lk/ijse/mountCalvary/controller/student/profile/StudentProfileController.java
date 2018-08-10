package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.net.URL;
import java.util.ResourceBundle;

public final class StudentProfileController extends SuperController implements Initializable {

    @FXML
    private BorderPane bpStudentProfile;

    @FXML
    private JFXButton btSearch;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private VBox personalDetail;
    @FXML
    private VBox attendanceAndActivityOfStudent;
    @FXML
    private VBox competitionForStudent;
    @FXML
    private VBox studentPayment;
    @FXML
    private VBox physicalEvaluationTestForStudent;
    @FXML
    private VBox studentContributionForCompetition;

    @FXML
    private CompetitionForStudentController competitionForStudentController;
    @FXML
    private AttendanceAndActivityOfStudentController attendanceAndActivityOfStudentController;
    @FXML
    private PersonalDetailController personalDetailController;
    @FXML
    private StudentPaymentController studentPaymentController;
    @FXML
    private PhysicalEvaluationTestForStudentController physicalEvaluationTestForStudentController;
    @FXML
    private StudentContributionForCompetitionController studentContributionForCompetitionController;

    private AutoComplete<StudentDTO> autoCompleteStudent;
    private StudentBO studentBOImpl;
    private ObservableList<StudentDTO> allStudentDetail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(bpStudentProfile);
        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        try {
            loadStudentDetail();
            autoCompleteStudent = new AutoComplete<>(txtStudentName, allStudentDetail);
            autoCompleteStudent.setAutoCompletionsAction(event -> btSearch.fire());
        } catch (Exception e) {
            callLogger(e);
        }
        personalDetailController.init(this);
        attendanceAndActivityOfStudentController.init(this);
        competitionForStudentController.init(this);
        studentPaymentController.init(this);
        physicalEvaluationTestForStudentController.init(this);
        studentContributionForCompetitionController.init(this);
    }

    private void loadStudentDetail() throws Exception {
        allStudentDetail = studentBOImpl.getAllStudentNameAndNumber();
    }

    @FXML
    private void btSearch_onAction(ActionEvent actionEvent) {
        StudentDTO i = autoCompleteStudent.getSelectedItemByName();
        if (i == null) {
            OptionPane.showErrorAtSide("Please select the student");
        } else {
            txtStudentID.setText("" + i.getSID());
            showDataOnTabs(i);
        }
    }

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        btSearch.fire();
    }

    private void showDataOnTabs(StudentDTO studentDTO) {
        personalDetailController.insertStudent(studentDTO);
        attendanceAndActivityOfStudentController.insertStudent(studentDTO);
        competitionForStudentController.insertStudent(studentDTO);
        studentPaymentController.insertStudent(studentDTO);
        physicalEvaluationTestForStudentController.insertStudent(studentDTO);
        studentContributionForCompetitionController.insertStudent(studentDTO);
    }

    @FXML
    private void btStudentID_onAction(ActionEvent actionEvent) {
        String studentID = txtStudentID.getText().trim();

        if (Common.isInteger(studentID)) {
            int SID = Integer.parseInt(studentID);
            StudentDTO studentDTO = autoCompleteStudent.searchByID(SID);
            if (studentDTO != null) {
                txtStudentName.setText(studentDTO.getSName());
                showDataOnTabs(studentDTO);
            } else {
                OptionPane.showErrorAtSide("the student ID is not existed.");
            }

        } else {
            OptionPane.showErrorAtSide("The Student ID is invalid.");
        }

    }

}
