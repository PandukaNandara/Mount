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

public class PaymentMenu_controller implements Initializable {

    @FXML
    private AnchorPane acPaymentMenu;

    @FXML
    private JFXButton btUpdatePayment;

    @FXML
    private JFXButton btActivityPayment;

    @FXML
    private JFXButton btBack;

    @FXML
    private JFXButton btStudentPayment;

    @FXML
    void btActivityPayment_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/payment/ActivityPayment.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/basic/MainMenu.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudentPayment_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/payment/StudentPayment.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdatePayment_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/sample/z_junk/view/payment/UpdatePayment.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
