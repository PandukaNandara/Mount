package lk.ijse.mountCalvary.controller.LogIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.LogInBO;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.OptionPane;
import lk.ijse.mountCalvary.controller.ScreenLoader;
import lk.ijse.mountCalvary.model.LogInDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewUser_controller implements Initializable {

    @FXML
    private AnchorPane acNewUser;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtRepeatPassword;

    @FXML
    private JFXButton btSignUp;

    private LogInBO logInBOImpl;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        logInBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.LOG_IN);
    }

    @FXML
    void btSignUp_onAction(ActionEvent event) {
        try {
            if (!logInBOImpl.isNewOne(txtUserName.getText())){
                OptionPane.showError("This user name is already existed.");
                return;
            }else if (!checkPasswordAreSame()) {
                OptionPane.showError("Passwords are mismatched.");
                txtPassword.requestFocus();
                txtRepeatPassword.selectAll();
                txtPassword.selectAll();
                return;
            }
            if(logInBOImpl.add(new LogInDTO(txtUserName.getText(), txtPassword.getText()))){
                OptionPane.showMessage(
                  String.format("A new user has successfully added. %n" +
                          "User name :   %s %n" +
                          "Password  :   %s %n ", txtUserName.getText(), txtPassword.getText())
                );
            }else {
                OptionPane.showWarning("Something's wrong. We can't do your request.");
            }
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/LogIn/LogIn.fxml", acNewUser, this);
        } catch (Exception e) {
            Logger.getLogger(NewUser_controller.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    void txtPassword_onAction(ActionEvent event) {
        txtRepeatPassword.requestFocus();
    }

    @FXML
    void txtRepeatPassword_onAction(ActionEvent event) {
        btSignUp.fire();
    }

    private boolean checkPasswordAreSame() {
        return txtRepeatPassword.getText().equals(txtPassword.getText());
    }

    @FXML
    void txtUserName_onAction(ActionEvent event) {
        try {
            boolean newOne = logInBOImpl.isNewOne(txtUserName.getText());
            if (newOne) {
                txtPassword.requestFocus();
            } else {
                OptionPane.showError("This user name is already existed.");
            }
        } catch (Exception e) {
            Logger.getLogger(NewUser_controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        try {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/LogIn/LogIn.fxml", acNewUser, this);
        } catch (IOException e) {
            Logger.getLogger(NewUser_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
