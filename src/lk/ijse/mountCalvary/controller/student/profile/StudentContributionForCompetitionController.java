package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.CompContributionBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.Common;
import lk.ijse.mountCalvary.tool.OptionPane;
import lk.ijse.mountCalvary.tool.Reporter;
import lk.ijse.mountCalvary.model.CompContributionDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/29/2018
 * Time: 1:10 AM
 */
public final class StudentContributionForCompetitionController extends SuperController implements Initializable {
    @FXML
    private JFXButton btPrint;
    @FXML
    private TableView<CompContributionDTO> tblCompContribution;
    @FXML
    private TableColumn<CompContributionDTO, String> colCompetition;
    @FXML
    private TableColumn<CompContributionDTO, String> colContribution;

    private StudentProfileController studentProfileController;
    private StudentDTO selectedStudent;
    private CompContributionBO compContributionBOImpl;
    private JasperReport studentContributionForStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCompetition.setCellValueFactory(new PropertyValueFactory<>("competitionName"));
        colContribution.setCellValueFactory(new PropertyValueFactory<>("contribution"));

        compContributionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMP_CONTRIBUTION);
    }

    @FXML
    void btPrint_onAction(ActionEvent event) {
        if (selectedStudent != null) {
            try {
                if (studentContributionForStudent == null) {
                    InputStream studentPersonalDetailFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/StudentForCompetitionContribution.jrxml");
                    studentContributionForStudent = JasperCompileManager.compileReport(studentPersonalDetailFile);
                }
                HashMap map = new HashMap();
                map.put("StudentID", selectedStudent.getSID());
                map.put("StudentName", selectedStudent.getSName());
                map.put("contributions", new JRBeanCollectionDataSource(tblCompContribution.getItems()));

                JasperPrint jasperPrint = JasperFillManager.fillReport(studentContributionForStudent, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Student contribution for competition");

            } catch (Exception e) {
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a student to print.");
        }
    }

    public void init(StudentProfileController studentProfileController) {
        this.studentProfileController = studentProfileController;
    }

    public void insertStudent(StudentDTO studentDTO) {
        this.selectedStudent = studentDTO;
        try {
            ObservableList<CompContributionDTO> contributionForThisStudent
                    = compContributionBOImpl.getContributionForThisStudent(studentDTO.getSID());
            tblCompContribution.getItems().setAll(contributionForThisStudent);
            Common.clearSortOrder(tblCompContribution);
        } catch (Exception e) {
            callLogger(e);
        }
    }
}
