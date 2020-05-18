package lk.ijse.mountCalvary.controller.test.profile;


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
import lk.ijse.mountCalvary.model.TermDTO;
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
 * Date: 7/25/2018
 * Time: 4:01 PM
 */

public final class PhysicalEvaluationTestController extends SuperController implements Initializable {

    private static JasperReport competitionReport;
    @FXML
    private VBox physicalEvaluation;
    @FXML
    private JFXComboBox<String> cboxGrade;
    @FXML
    private JFXComboBox<String> cboxClass;
    @FXML
    private TableView<PhysicalTestDTO> tblMarks;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colStudentID;
    @FXML
    private TableColumn<PhysicalTestDTO, String> colStudentName;
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
    @FXML
    private JFXButton btPrint;
    private TestProfileController testProfileController;
    private PhysicalTestBO physicalTestBOImpl;
    private TermDTO selectTerm;
    private ObservableList<PhysicalTestDTO> selectedPhysicalDTO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(physicalEvaluation);


        colStudentID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("class_"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colSkill.setCellValueFactory(new PropertyValueFactory<>("skill"));
        colProgressEffort.setCellValueFactory(new PropertyValueFactory<>("progressEffort"));
        colAttitudes.setCellValueFactory(new PropertyValueFactory<>("attitudes"));
        colPerformance.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        physicalTestBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PHYSICAL_TEST);
        Common.loadClassForFiler(cboxClass);
        Common.loadGradeForFiler(cboxGrade);

    }

    @FXML
    void cboxClass_onAction(ActionEvent event) {
        if (cboxGrade.getSelectionModel().getSelectedItem() != null)
            filterData();
    }

    @FXML
    void cboxGrade_onAction(ActionEvent event) {
        if (cboxClass.getSelectionModel().getSelectedItem() != null)
            filterData();
    }

    private void filterData() {
        tblMarks.getSortOrder().clear();
        ObservableList<PhysicalTestDTO> filterData = FXCollections.observableArrayList();

        String class_ = cboxClass.getSelectionModel().getSelectedItem();
        String grade = cboxGrade.getSelectionModel().getSelectedItem();
        boolean isGradeForAll = grade.equals("All");
        boolean isClassForAll = class_.equals("All");
        for (PhysicalTestDTO one : selectedPhysicalDTO) {
            if (isClassForAll && isGradeForAll)
                tblMarks.getItems().setAll(selectedPhysicalDTO);
            else {
                try {
                    String one_grade = one.getClass_().split("-")[0];
                    String one_class = one.getClass_().split("-")[1];
                    if (isClassForAll && one_grade.equals(grade)) {
                        filterData.add(one);
                    } else if (one_class.equals(class_) && isGradeForAll) {
                        filterData.add(one);
                    } else if (one_class.equals(class_) && one_grade.equals(grade))
                        filterData.add(one);
                } catch (StringIndexOutOfBoundsException ignored) {
                }
                tblMarks.getItems().setAll(filterData);
            }
        }

    }

    public void insertTerm(TermDTO selectTerm) {
        this.selectTerm = selectTerm;
        try {

            selectedPhysicalDTO = physicalTestBOImpl.getPhysicalTestForThisTerm(
                    selectTerm.getTERM_ID()
            );
            cboxClass.getSelectionModel().select(0);
            cboxGrade.getSelectionModel().select(0);
            tblMarks.getItems().setAll(selectedPhysicalDTO);
            tblMarks.getSortOrder().clear();
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {

        if (selectTerm != null) {
            try {

                if (competitionReport == null) {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/test/PhysicalEvaluationTest.jrxml");
                    competitionReport = JasperCompileManager.compileReport(resourceAsStream);
                }
                JRBeanCollectionDataSource marksList = new JRBeanCollectionDataSource(tblMarks.getItems());

                HashMap map = new HashMap();

                map.put("term", selectTerm.getTermName());
                map.put("grade", cboxGrade.getSelectionModel().getSelectedItem());
                map.put("class", cboxClass.getSelectionModel().getSelectedItem());
                map.put("year", selectTerm.getYear());
                map.put("marks", marksList);

                JasperPrint jasperPrint = JasperFillManager.fillReport(competitionReport, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Physical Evaluation test result sheet");

            } catch (Exception e) {
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a term to print.");
        }
    }

    public void init(TestProfileController testProfileController) {
        this.testProfileController = testProfileController;
    }

}
