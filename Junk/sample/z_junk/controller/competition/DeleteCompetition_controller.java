package sample.z_junk.controller.competition;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCompetition_controller implements Initializable {
    @FXML
    private AnchorPane acDeleteCompetition;
    @FXML
    private JFXButton btDelete;
    @FXML
    private TableView<?> tblEventOfCompetition;
    @FXML
    private TableColumn<?, ?> colActivity;
    @FXML
    private TableColumn<?, ?> colEvent;
    @FXML
    private TableColumn<?, ?> colGender;
    @FXML
    private TableColumn<?, ?> colAgeGroup;
    @FXML
    private JFXComboBox<?> cboxCompetition;
    @FXML
    private TableView<?> tblParticipation;
    @FXML
    private TableColumn<?, ?> colStudent;
    @FXML
    private TableColumn<?, ?> colResult;
    @FXML
    private TableColumn<?, ?> colPerfomance;
    @FXML
    private JFXButton btCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/CompetitionMenu.fxml", this.acDeleteCompetition, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btDelete_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/DeleteCompetition.fxml", this.acDeleteCompetition, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxCompetition_onAction(ActionEvent event) {

    }

}
