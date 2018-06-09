package sample.z_junk.controller.competition.profile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EventAndActivity_controller implements Initializable {

    @FXML
    private AnchorPane acEventForActivity;

    @FXML
    private TableView<?> tblEventAndActivity;

    @FXML
    private TableColumn<?, ?> colActivity;

    @FXML
    private TableColumn<?, ?> colEvent;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colAgeGroup;

    @FXML
    private TableView<?> tblStudentResult;

    @FXML
    private TableColumn<?, ?> colStudent;

    @FXML
    private TableColumn<?, ?> colResult;

    @FXML
    private TableColumn<?, ?> colPerformance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
