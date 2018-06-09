package sample.z_junk.controller.competition;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewCompetition_controller implements Initializable {

    @FXML
    private AnchorPane acNewCompetition;

    @FXML
    private JFXButton btNext_newCompetition;

    @FXML
    private JFXTextField txtCompetitionName;

    @FXML
    private JFXTextField txtLocation;

    @FXML
    private JFXTextArea txtaDesc;

    @FXML
    private JFXComboBox<?> txtTeacherInCharge;

    @FXML
    private JFXButton btAdd_teacherInCharge;

    @FXML
    private TableView<?> tblTeacherInChargeList;

    @FXML
    private TableColumn<?, ?> colTeacherInCharge;

    @FXML
    private JFXButton btRemove_tblTeacherInChargeList;
    @FXML
    private JFXDatePicker dtDate;

    @FXML
    void btAdd_teacherInCharge_onAction(ActionEvent event) {

    }

    @FXML
    void btNext_newCompetition_onAction(ActionEvent event) {
        try {
            Parent root = ScreenLoader.loadPanel("/sample/z_junk/view/competition/EventForCompetition.fxml", this.acNewCompetition, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_tblTeacherInChargeList_onAction(ActionEvent event) {

    }

    @FXML
    void txtCompetitionName_onAction(ActionEvent event) {

    }

    @FXML
    void txtLocation_onAction(ActionEvent event) {

    }

    @FXML
    void txtTeacherInCharge_onAction(ActionEvent event) {

    }

    @FXML
    void txtaDesc_onAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void dtDate_onAction(ActionEvent actionEvent) {
    }
}
