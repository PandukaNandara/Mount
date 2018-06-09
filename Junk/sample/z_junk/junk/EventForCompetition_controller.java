package sample.z_junk.junk;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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

public class EventForCompetition_controller implements Initializable {
    @FXML
    private AnchorPane acEventForCompetition;
    @FXML
    private JFXButton btNext;
    @FXML
    private JFXButton btAddEvent_EventForActivity;
    @FXML
    private TableView<?> tblEventList;
    @FXML
    private TableColumn<?, ?> colEvent_tblEventList;
    @FXML
    private TableColumn<?, ?> colGender_tblEventList;
    @FXML
    private TableColumn<?, ?> colAgeGroup_tblEventList;
    @FXML
    private JFXButton btRemove_tblEventList;
    @FXML
    private JFXComboBox<?> cboxActivity;
    @FXML
    private TableView<?> tblEventListOfActivity;
    @FXML
    private TableColumn<?, ?> colEvent_tblEventListOfActivity;
    @FXML
    private TableColumn<?, ?> colGender_tblEventListOfActivity;
    @FXML
    private JFXTextField cboxEventName;
    @FXML
    private JFXComboBox<?> cboxAgeGroup;
    @FXML
    private JFXButton btCreateNewGroup;
    @FXML
    private JFXButton btBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAddEvent_EventForActivity_onAction(ActionEvent event) {

    }

    @FXML
    void btBack_EventForActivity_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/junk/NewCompetition.fxml", this.acEventForCompetition, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btCreateNewGroup_onAction(ActionEvent event) {

    }

    @FXML
    void btNext_EventForActivity_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/competition/StudentForCompetition.fxml", this.acEventForCompetition, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_tblEventList_onAction(ActionEvent event) {

    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {

    }

    @FXML
    void colAgeGroup_onAction(ActionEvent event) {

    }

    @FXML
    void colEventName_onAction(ActionEvent event) {

    }

}
