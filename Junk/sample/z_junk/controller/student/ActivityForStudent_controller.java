package sample.z_junk.controller.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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

public class ActivityForStudent_controller implements Initializable {

    @FXML
    private AnchorPane acActivityForEvent;


    @FXML
    private JFXButton btFinish_activityForStudent;

    @FXML
    private TableView<?> tblActivity;

    @FXML
    private TableColumn<?, ?> colActivity;

    @FXML
    private TableColumn<?, ?> colJoinedDate;

    @FXML
    private JFXButton btRemove_tblActivity;

    @FXML
    private JFXComboBox<?> txtActivityName;

    @FXML
    private JFXDatePicker dtJoinedDate;

    @FXML
    private JFXButton btAddActivity;

    @FXML
    private JFXButton btBack_activityForStudent;

    @FXML
    private JFXButton btSkipAndFinish;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAddActivity_onAction(ActionEvent event) {

    }

    @FXML
    void btBack_activityForStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/MorePersonalDetail.fxml", this.acActivityForEvent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btFinish_activityForStudent_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/StudentMenu.fxml", this.acActivityForEvent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {

    }

    @FXML
    void txtActivityName_onAction(ActionEvent event) {

    }

    @FXML
    private void btRemove_tblActivity_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void btSkipAndFinish_onAction(ActionEvent actionEvent) {

    }
}
