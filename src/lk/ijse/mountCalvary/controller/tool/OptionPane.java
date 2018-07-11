package lk.ijse.mountCalvary.controller.tool;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 6/21/2018
 * Time: 4:43 PM
 */

public class OptionPane {

    public static void showErrorAtSide(String message) {
        final Notifications notifications = Notifications.create()
                .title("Error")
                .position(Pos.CENTER_RIGHT)
                .text(message)
                .graphic(new ImageView(ApplicationIcons.getErrorIcon()))
                .hideCloseButton();
        notifications.show();
    }

    public static void showErrorWherever(String message, Pos pos, Stage stage) {
        final Notifications notifications = Notifications.create()
                .title("Error")
                .position(pos)
                .text(message)
                .owner(stage)
                .graphic(new ImageView(ApplicationIcons.getErrorIcon()))
                .hideCloseButton();
        notifications.hideAfter(Duration.millis(950));
        notifications.show();
    }

    public static void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.setGraphic(new ImageView(ApplicationIcons.getErrorIcon()));
        setIconToTheAlert(a);
        a.showAndWait();
    }

    public static void showWarning(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        a.setGraphic(new ImageView(ApplicationIcons.getWarningIcon()));
        setIconToTheAlert(a);
        a.showAndWait();
    }

    public static boolean askQuestion(String message) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        a.setGraphic(new ImageView(ApplicationIcons.getQuestionIcon()));
        setIconToTheAlert(a);
        return returnButtonSetResult(a.showAndWait());
    }


    public static boolean askWarning(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
        a.setGraphic(new ImageView(ApplicationIcons.getWarningIcon()));
        setIconToTheAlert(a);
        return returnButtonSetResult(a.showAndWait());
    }

    private static boolean returnButtonSetResult(Optional<ButtonType> btSet) {
        if (btSet.get() == ButtonType.YES)
            return true;
        else if (btSet.get() == ButtonType.NO)
            return false;
        else
            return false;
    }

    public static void showMessage(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        a.setGraphic(new ImageView(ApplicationIcons.getInfoIcon()));
        setIconToTheAlert(a);
        a.showAndWait();
    }

    public static void showDoneAtSide(String message) {
        Notifications notifications = Notifications.create()
                .title("Done")
                .position(Pos.CENTER_RIGHT)
                .text(message)
                .graphic(new ImageView(ApplicationIcons.getDoneIcon()))
                .hideCloseButton();
        notifications.show();
    }

    public static void showWarningAtSide(String message) {
        Notifications notifications = Notifications.create()
                .title("Warning")
                .position(Pos.CENTER_RIGHT)
                .text(message)
                .graphic(new ImageView(ApplicationIcons.getWarningIcon()))
                .hideCloseButton();
        notifications.show();
    }

    private static void setIconToTheAlert(Alert a) {
        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ApplicationIcons.getDefaultIcon());
    }
}
