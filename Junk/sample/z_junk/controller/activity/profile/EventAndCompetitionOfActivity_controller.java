package sample.z_junk.controller.activity.profile;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class EventAndCompetitionOfActivity_controller {

    @FXML
    private AnchorPane acEventAndCompetitionOfActivity;

    @FXML
    private JFXComboBox<?> cboxEvent;

    @FXML
    private TableView<?> tblParticipation;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colResult;

    @FXML
    private TableColumn<?, ?> colPerformance;

    @FXML
    private TableView<?> tblCompetitionList;

    @FXML
    private TableColumn<?, ?> colCompetition;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    void cboxEvent_onAction(ActionEvent event) {

    }

}
