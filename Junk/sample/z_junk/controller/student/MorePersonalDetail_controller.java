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

public class MorePersonalDetail_controller implements Initializable {
    @FXML
    private AnchorPane acMorePersonalDetail;
    @FXML
    private JFXButton btNext_morePersonalDetail;
    @FXML
    private TableView<?> tblTelNo;
    @FXML
    private TableColumn<?, ?> colTelNo_tblTelNo;
    @FXML
    private JFXButton btRemove_tblTelNo;
    @FXML
    private JFXButton btAddTelNo;
    @FXML
    private JFXTextField txtTelNo;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXButton btBack_morePersonalDetail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btAddTelNo_onAction(ActionEvent event) {

    }


    @FXML
    void btBack_morePersonalDetail_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/NewStudent.fxml", this.acMorePersonalDetail, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btRemove_tblTelNo_onAction(ActionEvent event) {

    }

    @FXML
    void txtAddress_onAction(ActionEvent event) {

    }

    @FXML
    void txtTelNo_onAction(ActionEvent event) {

    }

    @FXML
    private void btNext_morePersonalDetail_onAction(ActionEvent actionEvent) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/student/ActivityForStudent.fxml", this.acMorePersonalDetail, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
