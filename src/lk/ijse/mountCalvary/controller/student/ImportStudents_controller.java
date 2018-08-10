package lk.ijse.mountCalvary.controller.student;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.House;
import lk.ijse.mountCalvary.controller.tool.OptionPane;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/8/2018
 * Time: 6:52 PM
 */

public class ImportStudents_controller extends SuperController implements Initializable {

    @FXML
    private VBox acImportStudent;

    @FXML
    private TableView<StudentDTO> tblImportedStudent;

    @FXML
    private TableColumn<StudentDTO, Integer> colSID;

    @FXML
    private TableColumn<StudentDTO, String> colStudentName;

    @FXML
    private TableColumn<StudentDTO, String> colGender;

    @FXML
    private TableColumn<StudentDTO, Date> colDOB;

    @FXML
    private TableColumn<StudentDTO, String> colClass;

    @FXML
    private TableColumn<StudentDTO, String> colFather;

    @FXML
    private TableColumn<StudentDTO, String> colMother;

    @FXML
    private TableColumn<StudentDTO, String> colNote;

    @FXML
    private TableColumn<StudentDTO, House> colHouse;

    @FXML
    private TableColumn<StudentDTO, String> colAddress;

    @FXML
    private TableColumn<StudentDTO, Integer> colBCID;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXButton btUpdate;

    private Stage window;
    private StudentBO studentBOImpl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

        ButtonFireForEnterSetter.setGlobalEventHandler(acImportStudent);

        colSID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("sClass"));
        colFather.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        colMother.setCellValueFactory(new PropertyValueFactory<>("motherName"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colHouse.setCellValueFactory(new PropertyValueFactory<>("houseName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colBCID.setCellValueFactory(new PropertyValueFactory<>("BCID"));
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        window.close();
    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        try {
            if (OptionPane.askQuestion("Are you sure want to import all student data?")) {

                if (studentBOImpl.addUpdateStudentList(tblImportedStudent.getItems()))
                    OptionPane.showDoneAtSide("All student data successfully imported.");
                else
                    OptionPane.askQuestion("All student data successfully imported.");
            }
        } catch (Exception e) {
            callLogger(e);
        }
    }

    protected void setStudentData(ObservableList<StudentDTO> studentDTOS) {
        if (studentDTOS.size() == 0)
            OptionPane.showErrorAtSide("Table name is invalid or cannot be found.");
        tblImportedStudent.getItems().setAll(studentDTOS);

    }
}
