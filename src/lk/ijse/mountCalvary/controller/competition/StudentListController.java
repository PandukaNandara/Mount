package lk.ijse.mountCalvary.controller.competition;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 26-Sep-2018
 * Time: 7:45 PM
 */
public class StudentListController extends SuperController implements Initializable {

    @FXML
    private TableColumn<RegistrationDTO, Integer> colStudentID;

    @FXML
    private TableColumn<RegistrationDTO, String> colStudentName;

    @FXML
    private TableColumn<RegistrationDTO, String> colGender;

    @FXML
    private TableColumn<RegistrationDTO, Date> colDOB;
    @FXML
    private TableView<RegistrationDTO> tblRegistration;
    @FXML
    private VBox acStudentList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));

    }

    void setStudentList(ObservableList<RegistrationDTO> list){
        tblRegistration.itemsProperty().setValue(list);
    }
    void close(){
        ((Stage)(acStudentList.getScene().getWindow())).close();
    }
}
