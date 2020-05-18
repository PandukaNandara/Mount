package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.ScreenLoader;

import java.net.URL;
import java.util.ResourceBundle;

public final class PaymentMenu_controller extends SuperController implements Initializable {

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
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acPaymentMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

    @FXML
    void btStudentPayment_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/student/profile/StudentPayment.fxml", this.acPaymentMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

    @FXML
    void btUpdatePayment_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/payment/MakePayment.fxml", this.acPaymentMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
