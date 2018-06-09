package sample.z_junk.controller.competition.profile;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class CompetitionDetail_controller {

    @FXML
    private AnchorPane acCompetitionDetail;

    @FXML
    private JFXTextField txtCompetitionName;

    @FXML
    private JFXTextField txtLoaction;

    @FXML
    private JFXTextArea txtDesc;

    @FXML
    private TableView<?> tblTeacherInCharge;

    @FXML
    private TableColumn<?, ?> colTeacherInCharge;

    @FXML
    private JFXDatePicker dtDate;
}
