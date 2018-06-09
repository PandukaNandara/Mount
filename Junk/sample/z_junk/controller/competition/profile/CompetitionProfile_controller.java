package sample.z_junk.controller.competition.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompetitionProfile_controller implements Initializable {

    @FXML
    private BorderPane bpCompetitionProfile;

    @FXML
    private VBox vbxSideBar;

    @FXML
    private JFXButton btCompetitionDetails;

    @FXML
    private JFXButton btEventDetail;

    @FXML
    private JFXButton btPrint;

    @FXML
    private JFXButton btExcel;

    @FXML
    private JFXButton btSearch;

    @FXML
    private JFXComboBox<?> cboxCompetitionName;

    @FXML
    private AnchorPane acCompetitionRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/profile/CompetitionDetail.fxml", this.acCompetitionRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btCompetitionDetails_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/profile/CompetitionDetail.fxml", this.acCompetitionRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btEventDetail_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/profile/EventAndActivity.fxml", this.acCompetitionRoot, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btExcel_onAction(ActionEvent event) {

    }

    @FXML
    void btSearch_onAction(ActionEvent event) {

    }

    @FXML
    void cboxCompetitionName_onAction(ActionEvent event) {

    }


}
