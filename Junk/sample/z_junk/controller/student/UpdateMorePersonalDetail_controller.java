package sample.z_junk.controller.student;

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

public class UpdateMorePersonalDetail_controller implements Initializable {
    @FXML
    private AnchorPane acUpdateMorePresonalDeatil;
    @FXML
    private JFXButton btUpdate;
    @FXML
    private TableView<?> tblTelNo;
    @FXML
    private TableColumn<?, ?> colTelNo_tblTelNo;
    @FXML
    private JFXButton btRemove_telNo;
    @FXML
    private JFXButton btUpdate_telNo;
    @FXML
    private JFXTextField txtNewTelNo;
    @FXML
    private JFXButton btUpdate_address;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXButton btBack_updateMorePresonalDeatil;
    @FXML
    private TableView<?> tblUpdatedTelNo;
    @FXML
    private TableColumn<?, ?> colOdlTelNo_tblUpdatedTelNo;
    @FXML
    private TableColumn<?, ?> colNewTelNo_tblUpdatedTelNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btBack_updateMorePresonalDeatil_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/UpdateStudent.fxml", this.acUpdateMorePresonalDeatil, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_telNo_onAction(ActionEvent event) {

    }

    @FXML
    void btUpdate_address_onAction(ActionEvent event) {

    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/StudentMenu.fxml", this.acUpdateMorePresonalDeatil, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdate_telNo_onAction(ActionEvent event) {

    }

    @FXML
    void txtAddress_onAction(ActionEvent event) {

    }

    @FXML
    void txtNewTelNo_onAction(ActionEvent event) {

    }

}
