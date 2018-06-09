package lk.ijse.mountCalvary.controller.basic;

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
import lk.ijse.mountCalvary.model.LogInDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogIn_controller implements Initializable {

    LogInBO logInBOImpl;
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

        logInBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.LOG_IN);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), acLogIn);
        fade.setFromValue(0);
        fade.setToValue(2);
        fade.play();
    }

    @FXML
    void btLogIn_onAction(ActionEvent event) throws Exception {
        if (logInBOImpl.isValid(new LogInDTO(txtUserName.getText(), txtPassword.getText()))){
            try {
                Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/mountCalvary/view/basic/MainMenuFrame.fxml"));
                Scene sc = new Scene(root);
                Stage window = (Stage) this.acLogIn.getScene().getWindow();
                window.setScene(sc);
                window.centerOnScreen();
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR, "Invalid user name or password.",ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void txtUserName_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void txtPassword_onAction(ActionEvent actionEvent) {

    }
}
