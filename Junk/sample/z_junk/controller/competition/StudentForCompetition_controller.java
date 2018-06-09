package sample.z_junk.controller.competition;

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

public class StudentForCompetition_controller implements Initializable {
    @FXML
    private AnchorPane acStudentForCompetition;
    @FXML
    private JFXButton btFinish;
    @FXML
    private JFXButton btAddStudent;
    @FXML
    private TableView<?> tblEventInCompetition;
    @FXML
    private TableColumn<?, ?> colActivity_tblEventInCompetition;
    @FXML
    private TableColumn<?, ?> colEvent_tblEventInCompetition;
    @FXML
    private TableColumn<?, ?> colGender_tblEventInCompetition;
    @FXML
    private TableColumn<?, ?> colAgeGroup_tblEventInCompetition;
    @FXML
    private JFXButton btRemove_tblStudentList;
    @FXML
    private JFXTextField txtEvent;
    @FXML
    private JFXButton btBack;
    @FXML
    private TableView<?> tblStudentLIst;
    @FXML
    private TableColumn<?, ?> colEvent_tblStudentLIst;
    @FXML
    private TableColumn<?, ?> colStudent_tblStudentLIst;
    @FXML
    private TableColumn<?, ?> colResult_tblStudentLIst;
    @FXML
    private TableColumn<?, ?> colPerformance_tblStudentLIst;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXComboBox<?> cboxStudentName;
    @FXML
    private JFXComboBox<?> cboxResult;
    @FXML
    private JFXTextField txtPerfomence;
    @FXML
    private JFXTextField txtGender;
    @FXML
    private JFXTextField txtAgeGroup;
    @FXML
    private JFXButton btViewStudentList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAddStudent_onAction(ActionEvent event) {

    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/EventForCompetition.fxml", this.acStudentForCompetition, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btFinish_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/CompetitionMenu.fxml", this.acStudentForCompetition, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_tblStudentList_onAction(ActionEvent event) {

    }

    @FXML
    void btViewStudentList_onAction(ActionEvent event) {

    }

    @FXML
    void cboxResult_onAction(ActionEvent event) {

    }

    @FXML
    void cboxStudentName_onAction(ActionEvent event) {

    }

    @FXML
    void txtAgeGroup_onAction(ActionEvent event) {

    }

    @FXML
    void txtEvent_onAction(ActionEvent event) {

    }

    @FXML
    void txtGender_onAction(ActionEvent event) {

    }

    @FXML
    void txtPerfomence_onAction(ActionEvent event) {

    }

    @FXML
    void txtStudent_onAction(ActionEvent event) {

    }

}
