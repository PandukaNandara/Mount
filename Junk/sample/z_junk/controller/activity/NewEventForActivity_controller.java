package sample.z_junk.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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

public class NewEventForActivity_controller implements Initializable {
    @FXML
    private AnchorPane acNewEvntForActivity;
    @FXML
    private TableView<?> tblNewEvent;
    @FXML
    private TableColumn<?, ?> colNewEventName;
    @FXML
    private TableColumn<?, ?> colNewGender;
    @FXML
    private JFXButton btAdd;
    @FXML
    private JFXButton btSubmit;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXTextField txtEventName;
    @FXML
    private JFXRadioButton rbxMale;
    @FXML
    private JFXRadioButton rbxFemale;
    @FXML
    private TableView<?> tblOldEvent;
    @FXML
    private TableColumn<?, ?> tblOldEventName;
    @FXML
    private TableColumn<?, ?> tblOldEventGender;
    @FXML
    private JFXComboBox<?> cboxActivityName;
    @FXML
    private JFXButton btRemove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAdd_onAction(ActionEvent event) {

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acNewEvntForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_onAction(ActionEvent event) {

    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/ActivityMenu.fxml", this.acNewEvntForActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivityName_onAction(ActionEvent event) {

    }

    @FXML
    void txtEventName_onAction(ActionEvent event) {

    }

}
