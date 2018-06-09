package sample.z_junk.controller.student.profile;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class CompetitionForStudent_controller {

    @FXML
    private AnchorPane acCompetitionForStudent;

    @FXML
    private TableView<?> tblParticipation;

    @FXML
    private TableColumn<?, ?> colCompetition;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colActivity;

    @FXML
    private TableColumn<?, ?> colEvent;

    @FXML
    private TableColumn<?, ?> colResult;

    @FXML
    private TableColumn<?, ?> colPerformance;

    @FXML
    private JFXComboBox<?> cboxCompetition;

    @FXML
    private JFXComboBox<?> cboxActivity;

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void cboxCompetition_onAction(ActionEvent event) {

    }

}
