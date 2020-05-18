package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.mountCalvary.business.custom.PhysicalTestBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.model.PhysicalTestDTO;
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
 * Date: 7/26/2018
 * Time: 9:38 AM
 */
public final class PhysicalEvaluationTestForStudentController extends SuperController implements Initializable {

    @FXML
    private VBox physicalEvaluationTestForStudent;
    @FXML
    private JFXComboBox<Year> cboxYear;
    @FXML
    private TableView<PhysicalTestDTO> tblMarks;
    @FXML
    private TableColumn<PhysicalTestDTO, String> colTerm;
    @FXML
    private TableColumn<PhysicalTestDTO, String> colClass;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colAttendance;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colSkill;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colProgressEffort;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colAttitudes;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colPerformance;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colTotal;

    private PhysicalTestBO physicalTestBOImpl;
    private StudentProfileController studentProfileController;
    private StudentDTO selectedStudent;
    private ObservableList<PhysicalTestDTO> marks;
    @FXML
    private JFXButton btPrint;
    private JasperReport studentPhysicalEvaluationMarks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(physicalEvaluationTestForStudent);

        colTerm.setCellValueFactory(new PropertyValueFactory<>("termDTO"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("class_"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colSkill.setCellValueFactory(new PropertyValueFactory<>("skill"));
        colProgressEffort.setCellValueFactory(new PropertyValueFactory<>("progressEffort"));
        colAttitudes.setCellValueFactory(new PropertyValueFactory<>("attitudes"));
        colPerformance.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        physicalTestBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PHYSICAL_TEST);
        loadYear();

    }

    private void loadYear() {
        cboxYear.getItems().setAll(Year.ALL);
        cboxYear.getItems().addAll(Year.getAllYear());
    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {
        filterData();
    }

    private void filterData() {
        Year year = cboxYear.getSelectionModel().getSelectedItem();
        ObservableList<PhysicalTestDTO> filter = FXCollections.observableArrayList();
        if (year.isForAll())
            tblMarks.getItems().setAll(marks);
        else {
            int year_value = year.getYear();
            for (PhysicalTestDTO one : marks) {
                if (year_value == one.getTermDTO().getYear())
                    filter.add(one);
            }
            tblMarks.getItems().setAll(filter);
        }

    }

    public void init(StudentProfileController studentProfileController) {
        this.studentProfileController = studentProfileController;
    }

    public void insertStudent(StudentDTO studentDTO) {
        selectedStudent = studentDTO;
        try {
            marks = physicalTestBOImpl
                    .getPhysicalTestForThisStudent(studentDTO.getSID());
            tblMarks.getItems().setAll(marks);
            Common.clearSortOrder(tblMarks);
            cboxYear.getSelectionModel().select(Year.ALL);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        if (selectedStudent != null) {
            try {
                if (studentPhysicalEvaluationMarks == null) {
                    InputStream studentPersonalDetailFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/PhysicalEvaluationTest.jrxml");
                    studentPhysicalEvaluationMarks = JasperCompileManager.compileReport(studentPersonalDetailFile);
                }
                HashMap map = new HashMap();
                map.put("StudentID", selectedStudent.getSID());
                map.put("StudentName", selectedStudent.getSName());
                map.put("year", cboxYear.getSelectionModel().getSelectedItem().toString());
                map.put("marks", new JRBeanCollectionDataSource(tblMarks.getItems()));

                JasperPrint jasperPrint = JasperFillManager.fillReport(studentPhysicalEvaluationMarks, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Physical evaluation test");

            } catch (Exception e) {
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a student to print.");
        }
    }
}
