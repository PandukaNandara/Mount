package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.ScreenLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 10:13 PM
 */

public final class TestMenu_controller extends SuperController implements Initializable {

    @FXML
    private VBox acTestMenu;

    @FXML
    private JFXButton btBack;

    @FXML
    private JFXButton btTestProfile;

    @FXML
    private JFXButton btNewTerm;

    @FXML
    private JFXButton btPhysicalEvaluationTest;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acTestMenu);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acTestMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().hideSideBar();
    }

    @FXML
    void btNewTerm_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/test/NewTerm.fxml", this.acTestMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

    @FXML
    void btPhysicalEvaluationTest_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/test/PhysicalTest.fxml", this.acTestMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

    @FXML
    void btTestProfile_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/test/profile/TestProfile.fxml", this.acTestMenu, this);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
    }

}
