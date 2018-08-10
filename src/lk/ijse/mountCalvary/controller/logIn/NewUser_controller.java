package lk.ijse.mountCalvary.controller.logIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.LogInBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.OptionPane;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;
import lk.ijse.mountCalvary.model.LogInDTO;

import java.net.URL;
import java.util.ResourceBundle;


public final class NewUser_controller extends SuperController implements Initializable {

    @FXML
    private VBox acNewUser;

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
    @FXML
    private JFXButton btCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acNewUser);

        logInBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.LOG_IN);
    }

    @FXML
    void btSignUp_onAction(ActionEvent event) {
        try {
            if (!logInBOImpl.isNewOne(txtUserName.getText())) {
                OptionPane.showErrorAtSide("This user name is already existed.");
                return;
            } else if (!checkPasswordAreSame()) {
                OptionPane.showErrorAtSide("Passwords are mismatched.");
                txtPassword.requestFocus();
                txtRepeatPassword.selectAll();
                txtPassword.selectAll();
                return;
            }
            if (logInBOImpl.add(new LogInDTO(txtUserName.getText(), txtPassword.getText()))) {
                OptionPane.showMessage(
                        String.format("A new user has successfully added. %n" +
                                "User name :   %s %n" +
                                "Password  :   %s %n ", txtUserName.getText(), txtPassword.getText())
                );
            } else {
                OptionPane.showWarning("Something's wrong. We can't do your request.");
            }
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/logIn/LogIn.fxml", acNewUser, this);
        } catch (Exception e) {
            callLogger(e);
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
                OptionPane.showErrorAtSide("This user name is already existed.");
            }
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        screenLoader.loadPanel("/lk/ijse/mountCalvary/view/logIn/LogIn.fxml", acNewUser, this);
    }
}
