package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.GlobalBoolean;

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
            GlobalBoolean.setLock(false);
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/activity/profile/ActivityPayment.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btStudentPayment_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/student/profile/StudentPayment.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btUpdatePayment_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml",this.acPaymentMenu, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
