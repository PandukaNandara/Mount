package lk.ijse.mountCalvary.controller.basic;

import javafx.fxml.Initializable;
import lk.ijse.mountCalvary.controller.GlobalBoolean;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBar_controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
    }
}
