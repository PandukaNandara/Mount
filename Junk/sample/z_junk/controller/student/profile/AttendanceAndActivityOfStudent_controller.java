package sample.z_junk.controller.student.profile;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class AttendanceAndActivityOfStudent_controller {

    @FXML
    private AnchorPane acAttendanceAndActivityOfStudent;

    @FXML
    private TableView<?> tblActivity;

    @FXML
    private TableColumn<?, ?> colActivity1_tblActivity;

    @FXML
    private TableColumn<?, ?> colJoinedDate_tblActivity;

    @FXML
    private TableView<?> tblAttendantSheet;

    @FXML
    private TableColumn<?, ?> colDate_tblAttendantSheet;

    @FXML
    private TableColumn<?, ?> colActivity_tblAttendantSheet;

    @FXML
    private TableColumn<?, ?> colTeacherInCharge_tblAttendantSheet;

    @FXML
    private JFXComboBox<?> cboxTimeRange;

    @FXML
    private JFXComboBox<?> cboxActivity;

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxTimeRange_onAction(ActionEvent event) {

    }

}
