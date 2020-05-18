package lk.ijse.mountCalvary.controller.competition;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.CompContributionBO;
import lk.ijse.mountCalvary.business.custom.CompetitionBO;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.model.CompContributionDTO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:49 AM
 */

public final class CompetitionContribution_controller extends SuperController implements Initializable {

    private final int MAX_CHARS = 5000;
    @FXML
    private VBox acCompContribution;
    @FXML
    private JFXComboBox<CompetitionDTO> cboxCompetition;
    @FXML
    private JFXTextArea txtaContribution;
    @FXML
    private JFXButton btAdd;
    @FXML
    private JFXButton btRemove;
    @FXML
    private TableView<CompContributionDTO> tblCompContribution;
    @FXML
    private TableColumn<CompContributionDTO, Integer> colStudentID;
    @FXML
    private TableColumn<CompContributionDTO, String> colStudentName;
    @FXML
    private TableColumn<CompContributionDTO, String> colContribution;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btSubmit;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXTextField txtStudentName;
    private StudentDTO selectedStudent;
    private ObservableList<StudentDTO> studentList;
    private AutoComplete<StudentDTO> studentAutoComplete;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    private CompetitionBO competitionBOImpl;
    private StudentBO studentBOImpl;
    private CompContributionBO compContributionBOImpl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acCompContribution);

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colContribution.setCellValueFactory(new PropertyValueFactory<>("contribution"));

        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);
        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        compContributionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMP_CONTRIBUTION);

        studentAutoComplete = new AutoComplete<>(txtStudentName);
        studentAutoComplete.setAutoCompletionsAction(event -> searchStudentByName());


        txtaContribution.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));
        try {
            loadCompetitions();
        } catch (Exception e) {
            callLogger(e);
        }
    }

    private void searchStudentByName() {
        selectedStudent = studentAutoComplete.getSelectedItemByName();
        if (isStudentCorrect()) {
            txtaContribution.requestFocus();
        } else OptionPane.showErrorAtSide("Student name is invalid or already added.");
    }

    private void loadCompetitions() throws Exception {
        cboxCompetition.getItems().setAll(competitionBOImpl.getAllCompetition());
    }

    @FXML
    private void btCancel(ActionEvent event) {
        boolean answer = OptionPane.askWarning("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane(
                    "/lk/ijse/mountCalvary/view/competition/CompetitionContribution.fxml",
                    this.acCompContribution, this);
        }
    }

    @FXML
    private void btSubmit_onAction(ActionEvent event) {
        int count = 0;
        ObservableList<CompContributionDTO> newContributions = FXCollections.observableArrayList();
        for (CompContributionDTO compContributionDTO : tblCompContribution.getItems())
            if (compContributionDTO.isNewOne()) {
                count++;
                newContributions.add(compContributionDTO);
            }

        if (count == 0)
            OptionPane.showErrorAtSide("Please add some new data.");
        else if (OptionPane.askQuestion("Do you want to add all new contribution?")) {
            try {
                if (compContributionBOImpl.addAllContribution(newContributions)) {
                    OptionPane.showDoneAtSide("All new contributions has successfully added.");
                    screenLoader.loadOnCenterOfBorderPane(
                            "/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml",
                            this.acCompContribution, this);
                } else {
                    OptionPane.showWarningAtSide("Something's wrong we can't do your request.");
                }

            } catch (Exception e) {
                OptionPane.showWarning("Something's wrong we can't do your request.");
                callLogger(e);

            }
        }
    }

    @FXML
    private void cboxCompetition_onAction(ActionEvent event) {
        cboxCompetition.setDisable(true);
        int cid = cboxCompetition.getSelectionModel().getSelectedItem().getCID();
        try {
            studentList = studentBOImpl.getStudentWhoIsNotInContributionListOfThisCompetition(cid);
            studentAutoComplete.changeSuggestion(studentList);
            ObservableList<CompContributionDTO> alreadyAddedList
                    = compContributionBOImpl.getContributionForThisCompetition(cid);
            tblCompContribution.getItems().setAll(alreadyAddedList);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void txtStudentName_onAction(ActionEvent actionEvent) {
        searchStudentByName();
    }

    @FXML
    private void txtStudentID_onAction(ActionEvent actionEvent) {
        String studentID = txtStudentID.getText().trim();
        if (studentID.length() == 0) {
            txtStudentName.requestFocus();
        } else if (Common.isInteger(studentID)) {
            int SID = Integer.parseInt(studentID);
            selectedStudent = studentAutoComplete.searchByID(SID);
            if (isStudentCorrect())
                txtStudentName.requestFocus();
            else OptionPane.showErrorAtSide("This Student is already added.");
        } else {
            OptionPane.showErrorAtSide("The Student ID is invalid.");
        }
    }

    private boolean isStudentCorrect() {
        if (selectedStudent != null) {
            txtStudentID.setText(String.valueOf(selectedStudent.getSID()));
            txtStudentName.setText(selectedStudent.getSName());
            return true;
        } else return false;
    }

    @FXML
    private void btAdd_onAction(ActionEvent actionEvent) {
        if (selectedStudent == null)
            OptionPane.showErrorAtSide("Please select a student.");
        else if (txtaContribution.getText().length() < 2) {
            OptionPane.showErrorAtSide("Please explain the contribution.");
            txtaContribution.requestFocus();
        } else {
            String contributionText = txtaContribution.getText().replaceAll("\n", System.getProperty("line.separator"));
            int cid = cboxCompetition.getSelectionModel().getSelectedItem().getCID();
            CompContributionDTO compContributionDTO = new CompContributionDTO(
                    selectedStudent.getSID(), cid, contributionText, selectedStudent.getSName());
            compContributionDTO.setNewOne(true);
            tblCompContribution.getItems().add(compContributionDTO);
            clearTextField();
            studentList.remove(selectedStudent);
            studentAutoComplete.changeSuggestion(studentList);
        }
    }

    public void clearTextField() {
        txtStudentID.clear();
        txtStudentName.clear();
    }

    @FXML
    private void btRemove_onAction(ActionEvent actionEvent) {
        try {
            CompContributionDTO selectContribution = tblCompContribution.getSelectionModel().getSelectedItem();
            if (selectContribution == null) {
                OptionPane.showErrorAtSide("Please select an item from the table.");
            } else if (!selectContribution.isNewOne())
                OptionPane.showWarningAtSide("This contribution detail has been already added.");
            else {
                CompContributionDTO o = (CompContributionDTO) Common.removeItemFromTable(tblCompContribution);
                studentList.add(new StudentDTO(o.getSID(), o.getStudentName()));
            }

        } catch (NullPointerException ignored) {
        }
    }
}
