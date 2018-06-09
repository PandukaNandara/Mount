package sample.z_junk.controller.activity.profile;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class JoinedStudent_controller {

    @FXML
    private AnchorPane acJoinedStudent;

    @FXML
    private TableView<?> tblStudentList;

    @FXML
    private TableColumn<?, ?> colStudent;

    @FXML
    private TableColumn<?, ?> colJoinedDate;

    @FXML
    private JFXComboBox<?> cboxTimeRange;

}
