package lk.ijse.mountCalvary.controller.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.PhysicalTestBO;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TermBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.OptionPane;
import lk.ijse.mountCalvary.model.PhysicalTestDTO;
import lk.ijse.mountCalvary.model.TermDTO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/20/2018
 * Time: 8:07 PM
 */

public final class PhysicalTest_controller extends SuperController implements Initializable {

    private static int messageCount;
    @FXML
    private VBox acPhysicalTest;
    @FXML
    private JFXButton btSave;
    @FXML
    private JFXComboBox<TermDTO> cboxTerm;
    @FXML
    private JFXComboBox<String> cboxClass;
    @FXML
    private TableView<PhysicalTestDTO> tblMarks;
    @FXML
    private TableColumn<PhysicalTestDTO, Integer> colStudentID;
    @FXML
    private TableColumn<PhysicalTestDTO, String> colStudentName;
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
    private StudentBO studentBOImpl;
    private PhysicalTestBO physicalTestBOImpl;
    private TermBO termBOImpl;
    private TermDTO selectedTerm;
    private String selectedClass;
    private ObservableList<PhysicalTestDTO> allStudentWithMarks;
    private int presentEditingValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acPhysicalTest);

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        physicalTestBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PHYSICAL_TEST);
        termBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TERM);

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colSkill.setCellValueFactory(new PropertyValueFactory<>("skill"));
        colProgressEffort.setCellValueFactory(new PropertyValueFactory<>("progressEffort"));
        colAttitudes.setCellValueFactory(new PropertyValueFactory<>("attitudes"));
        colPerformance.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        setTextFieldIntoTables();
        tblMarks.getFocusModel().focusedCellProperty().addListener((observable1, oldValue1, newValue1) -> {
            switchColumn();
            if (newValue1.getTableColumn() != null)
                newValue1.getTableColumn().setStyle("-fx-background-color: #C1D3FF");
        });

        loadClasses();
        loadTerms();
    }

    private void setTextFieldIntoTables() {
        colAttendance.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        colSkill.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        colProgressEffort.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        colAttitudes.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        colPerformance.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        colTotal.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
    }

    private void loadTerms() {
        try {
            cboxTerm.getItems().setAll(termBOImpl.getAllTerms());
        } catch (Exception e) {
            callLogger(e);
        }
    }

    private void loadClasses() {
        try {
            cboxClass.getItems().setAll(studentBOImpl.getAllDistinctClasses());
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void btSave_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void cboxTerm_onAction(ActionEvent actionEvent) {
        selectAndLoadMarks();
    }

    @FXML
    private void cboxClass_onAction(ActionEvent actionEvent) {
        selectAndLoadMarks();
    }

    private void selectAndLoadMarks() {
        selectedTerm = cboxTerm.getSelectionModel().getSelectedItem();
        selectedClass = cboxClass.getSelectionModel().getSelectedItem();
        try {
            if (selectedClass != null && selectedTerm != null) {
                allStudentWithMarks = physicalTestBOImpl.getPhysicalTestForThisClassAndTerm(selectedTerm.getTERM_ID(), selectedClass);
                tblMarks.getItems().setAll(allStudentWithMarks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void colAttendance_onEditCommit(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAttendance(event.getNewValue());
        tblMarks.requestFocus();
    }

    @FXML
    private void colSkill_onEditCommit(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        try {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSkill(event.getNewValue());
            tblMarks.requestFocus();
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    private void colProgressEffort_onEditCommit(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        try {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProgressEffort(event.getNewValue());
            tblMarks.requestFocus();
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    private void colAttitudes_onEditCommit(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        try {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAttitudes(event.getNewValue());
            tblMarks.requestFocus();
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    private void colPerformance_onEditCommit(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        try {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPerformance(event.getNewValue());
            tblMarks.requestFocus();
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    private void onStartEdit(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        presentEditingValue = event.getOldValue();
    }

    @FXML
    private void onEditCancel(TableColumn.CellEditEvent<PhysicalTestDTO, Integer> event) {
        if (messageCount < 3) {
            OptionPane.showWarningAtSide("Values might not be changed. You must press enter \n after changing values.");
            messageCount++;
        }

    }

    private void switchColumn() {
        for (TableColumn<PhysicalTestDTO, ?> columns : tblMarks.getColumns()) {
            columns.setStyle("-fx-background-color: transparent");
        }
    }

    private final class IntegerConverter extends StringConverter<Integer> {
        @Override
        public String toString(Integer value) {
            // If the specified value is null, return a zero-length String
            if (value == null) {
                return null;
            }
            return Integer.toString(value);

        }

        @Override
        public Integer fromString(String value) {
            // If the specified value is null or zero-length, return null
            if (value == null) {
                return null;
            }
            value = value.trim();
            if (value.length() < 1) {
                return null;
            }
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException | NullPointerException e) {
                return presentEditingValue;
            }
        }
    }
}
