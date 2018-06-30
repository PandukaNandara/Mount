package lk.ijse.mountCalvary.controller.LogIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.LogInBO;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.OptionPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.LogInDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogIn_controller implements Initializable {

    private LogInBO logInBOImpl;
    @FXML
    private AnchorPane acLogIn;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btLogIn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);

        txtUserName.setText("");
        txtPassword.setText("");
        logInBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.LOG_IN);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), acLogIn);
        fade.setFromValue(0);
        fade.setToValue(2);
        fade.play();
    }

    @FXML
    void btLogIn_onAction(ActionEvent event){
        try {
            if (logInBOImpl.isValidPassword(new LogInDTO(txtUserName.getText(), txtPassword.getText()))) {
                try {
                    Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/mountCalvary/view/basic/MainMenuFrame.fxml"));
                    Scene sc = new Scene(root);
                    Stage window = (Stage) this.acLogIn.getScene().getWindow();
                    window.setTitle("Mount Calvary Extra curriculum activity management system");
                    window.setScene(sc);
                    window.centerOnScreen();
                    window.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Invalid user name or password.", ButtonType.OK);
                a.showAndWait();
            }
        }catch (NullPointerException e){
            OptionPane.showError("This user is no longer available.");
        }catch (Exception e) {
            Logger.getLogger(NewUser_controller.class.getName()).log(Level.SEVERE, null, e);
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
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/LogIn/NewUser.fxml", acLogIn, this);
        } catch (IOException e) {
            Logger.getLogger(NewUser_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
