package lk.ijse.mountCalvary.controller.logIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.LogInBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.OptionPane;
import lk.ijse.mountCalvary.tool.ScreenLoader;
import lk.ijse.mountCalvary.model.LogInDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class LogIn_controller extends SuperController implements Initializable {

    private LogInBO logInBOImpl;
    @FXML
    private VBox acLogIn;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btLogIn;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @FXML
    private JFXButton btNewUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acLogIn);
        txtUserName.clear();
        txtPassword.clear();
        logInBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.LOG_IN);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), acLogIn);

        fade.setFromValue(0);
        fade.setToValue(2);
        fade.play();
    }

    @FXML
    void btLogIn_onAction(ActionEvent event) {
        try {
            if (logInBOImpl.isValidPassword(new LogInDTO(txtUserName.getText(), txtPassword.getText()))) {
                try {
                    Parent root = FXMLLoader.load(this.getClass().getResource(
                            "/lk/ijse/mountCalvary/view/basic/MainMenuFrame.fxml"));
                    Scene sc = new Scene(root);
                    Stage window = (Stage) this.acLogIn.getScene().getWindow();
                    window.setTitle("Mount Calvary Extra curriculum activity management system");
                    window.setScene(sc);
                    window.centerOnScreen();
                    window.setResizable(true);
                    window.setMaximized(true);
                    window.show();
                } catch (IOException e) {
                    callLogger(e);
                }
            } else {
                OptionPane.showErrorWherever("Invalid user name or password.",
                        Pos.TOP_CENTER, (Stage) acLogIn.getScene().getWindow());
            }
        } catch (NullPointerException e) {
            OptionPane.showErrorAtSide("This user is no longer available.");
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void txtUserName_onAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    @FXML
    private void txtPassword_onAction(ActionEvent actionEvent) {
        btLogIn.fire();
    }

    @FXML
    private void btNewUser_onAction(ActionEvent actionEvent) {
        screenLoader.loadPanel("/lk/ijse/mountCalvary/view/logIn/NewUser.fxml", acLogIn, this);
    }
}
