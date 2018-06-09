package sample.z_junk.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompetitionMenu_controller implements Initializable {
    @FXML
    private AnchorPane acCompetitionMenu;
    @FXML
    private JFXButton btCreateCompetition;
    @FXML
    private JFXButton btDeleteCompetition;
    @FXML
    private JFXButton btBack;
    @FXML
    private JFXButton btCompetitionProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml",this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btCreateCompetition_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/NewCompetition.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btDeleteCompetition_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/DeleteCompetition.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btProfileProfile_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/competition/profile/CompetitionProfile.fxml", this.acCompetitionMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
