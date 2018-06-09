package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Menu2 implements Initializable {
    @FXML
    JFXTextField txtMenu2;

    private Menu1 menu1;

    @FXML
    private void txtMenu2_onAction(ActionEvent actionEvent) {
        try {
            System.out.println(txtMenu2);
            System.out.println(txtMenu2.getText() + "YYYYYYYYYYYYYYYYYYY");
            menu1.txtMenu1.setText(txtMenu2.getText());

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
