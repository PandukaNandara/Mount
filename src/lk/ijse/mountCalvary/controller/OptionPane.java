package lk.ijse.mountCalvary.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 6/21/2018
 * Time: 4:43 PM
 */

public class OptionPane {
    public static void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        setIconToTheAlert(a);
        a.showAndWait();
    }

    public static void showWarning(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        setIconToTheAlert(a);
        a.showAndWait();
    }

    public static boolean askQuestion(String message) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        setIconToTheAlert(a);
        Optional<ButtonType> btSet = a.showAndWait();
        if (btSet.get() == ButtonType.YES)
            return true;
        else if (btSet.get() == ButtonType.NO)
            return false;
        else
            return false;
    }

    public static boolean askWarning(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
        setIconToTheAlert(a);
        Optional<ButtonType> btSet = a.showAndWait();
        if (btSet.get() == ButtonType.YES)
            return true;
        else if (btSet.get() == ButtonType.NO)
            return false;
        else
            return false;
    }

    public static void showMessage(String message) {

        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        setIconToTheAlert(a);
        a.showAndWait();
    }

    private static void setIconToTheAlert(Alert a) {
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/lk/ijse/mountCalvary/assets/defaultIcon.png"));
    }
}
