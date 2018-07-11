package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;

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
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @FXML
    void btActivityPayment_onAction(ActionEvent event) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acPaymentMenu);
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/activity/profile/ActivityPayment.fxml", this.acPaymentMenu, this);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acPaymentMenu, this);
    }

    @FXML
    void btStudentPayment_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/profile/StudentPayment.fxml", this.acPaymentMenu, this);
    }

    @FXML
    void btUpdatePayment_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml", this.acPaymentMenu, this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
