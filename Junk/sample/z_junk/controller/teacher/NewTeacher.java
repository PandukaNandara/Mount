package sample.z_junk.controller.teacher;

import com.jfoenix.controls.JFXButton;
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

public class NewTeacher implements Initializable {
    @FXML
    private AnchorPane acNewTeacher;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btSubmit;
    @FXML
    private JFXButton btRemove;
    @FXML
    private JFXTextField txtTeacherName;
    @FXML
    private TableView<?> tblTeacher;
    @FXML
    private TableColumn<?, ?> tblTeacherName;
    @FXML
    private JFXButton btAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", this.acNewTeacher, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btSubmit_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml", this.acNewTeacher, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btRemove_tblTeacher_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void btAdd_onAction(ActionEvent actionEvent) {

    }
}
