package sample.z_junk.controller.activity.profile;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class AttendantSheet_controller {

    @FXML
    private AnchorPane acAttendantSheet;

    @FXML
    private JFXComboBox<?> cboxSearchStudent;

    @FXML
    private TableView<?> tblAttendantSheet;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colTeacherInCharge;

    @FXML
    private TableColumn<?, ?> colJoinedDate;

    @FXML
    private JFXComboBox<?> cobxTimeRange;

    @FXML
    void cboxSearchStudent_onAction(ActionEvent event) {

    }

    @FXML
    void cobxTimeRange_onAction(ActionEvent event) {

    }

}
