package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ParticipationBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.ParticipationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class CompetitionForStudentController extends SuperController implements Initializable {

    private static JasperReport competitionReport;
    @FXML
    protected VBox competitionForStudent;
    @FXML
    protected TableView<ParticipationDTO> tblParticipation;
    @FXML
    protected TableColumn<ParticipationDTO, String> colCompetition;
    @FXML
    protected TableColumn<ParticipationDTO, Date> colDate;
    @FXML
    protected TableColumn<ParticipationDTO, String> colActivity;
    @FXML
    protected TableColumn<ParticipationDTO, String> colEvent;
    @FXML
    protected TableColumn<ParticipationDTO, String> colResult;

    @FXML
    protected JFXComboBox<?> cboxCompetition;
    @FXML
    protected JFXComboBox<?> cboxActivity;
    @FXML
    protected TableColumn<ParticipationDTO, String> colPerformance;

    @FXML
    private JFXButton btPrint;

    private StudentProfileController studentProfileController;
    private ParticipationBO participationBOImpl;
    private StudentDTO selectedStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(competitionForStudent);

        colActivity.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colCompetition.setCellValueFactory(new PropertyValueFactory<>("competitionName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colPerformance.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        participationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PARTICIPATION);
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {

        if (selectedStudent != null) {
            try {

                if (competitionReport == null) {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/CompetitionForStudentReport.jrxml");
                    competitionReport = JasperCompileManager.compileReport(resourceAsStream);
                }
                JRBeanCollectionDataSource competition = new JRBeanCollectionDataSource(tblParticipation.getItems());

                HashMap map = new HashMap();
                map.put("StudentID", selectedStudent.getSID());
                map.put("StudentName", selectedStudent.getSName());
                map.put("Competition", competition);

                JasperPrint jasperPrint = JasperFillManager.fillReport(competitionReport, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Competition for student");


            } catch (Exception e) {
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a student to print.");
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxCompetition_onAction(ActionEvent event) {
    }

    public VBox getCompetitionForStudent_controller() {
        return competitionForStudent;
    }

    public void setCompetitionForStudent_controller(VBox competitionForStudent_controller) {
        this.competitionForStudent = competitionForStudent_controller;
    }

    public void init(StudentProfileController studentProfileController) {
        this.studentProfileController = studentProfileController;
    }

    protected void insertStudent(StudentDTO student) {
        try {
            selectedStudent = student;
            ObservableList<ParticipationDTO> achievement = participationBOImpl.getCompetitionAndAchievementOfThisStudent(student.getSID());
            tblParticipation.getItems().setAll(achievement);
            Common.clearSortOrder(tblParticipation);
        } catch (Exception e) {
            callLogger(e);
        }
    }


}
