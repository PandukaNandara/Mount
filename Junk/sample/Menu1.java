package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu1 implements Initializable {
    @FXML
    JFXTextField txtMenu1;

    @FXML
    private JFXButton btClick;
    @FXML
    private AnchorPane acMenu1;

    private Menu2 menu2;
    @FXML
    private JFXDatePicker dtDOB;
    @FXML
    private JFXButton btDate;

    @FXML
    private void txtMenu1_onAction(ActionEvent actionEvent) {
        try{
            System.out.println(txtMenu1);
            System.out.println(txtMenu1 == null);
            System.out.println(txtMenu1.getText() + "XXXXXXXXXXXXXXXXXXXXXX");
            menu2.txtMenu2.setText(txtMenu1.getText());

        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void btClick_onAction(ActionEvent actionEvent) {
        try {
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void dtDOB_onAction(ActionEvent actionEvent) {
        btDate.fire();
    }

    @FXML
    private void btDate_onAction(ActionEvent actionEvent) {
        java.sql.Date date = java.sql.Date.valueOf(dtDOB.getValue());
        System.out.println(date.toString());
    }
}
