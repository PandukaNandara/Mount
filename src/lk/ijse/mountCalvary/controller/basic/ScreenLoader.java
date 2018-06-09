package lk.ijse.mountCalvary.controller.basic;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class ScreenLoader {

//    public enum Menu{
//        MAIN_MENU, ACTIVITY_MENU, TEACHER, COMPETITION_MENU, STUDENT_MENU, UPDATE_ATTENDANCE, SPECIAL_REPORT
//    }
//    public enum Activity{
//        ACTIVITY_PROFILE, NEW_ACTIVITY, NEW_EVENT, ADD_STUDENT, UPDATE_ACTIVITY
//    }

    public static void loadWindow(String url, Pane pane, Initializable controller) throws IOException {
        Parent root = FXMLLoader.load(controller.getClass().getResource(url));
        Scene sc = new Scene(root);
        Stage window = (Stage) pane.getScene().getWindow();
        window.setScene(sc);
        window.show();
    }

    public static Parent loadPanel(String url, Pane where, Initializable controller) throws IOException {
//        JFXSpinner spinner = new JFXSpinner();
//        where.getChildren().setAll(spinner);
//

        FadeTransition t = new FadeTransition(Duration.millis(200), where);
        t.setFromValue(0.0);
        t.setToValue(5.0);
        t.play();

//        RotateTransition rt = new RotateTransition(Duration.millis(500), where);
//        rt.setByAngle(180);
//        rt.setCycleCount(4);
//        rt.play();

        //!!!!
//        ScaleTransition st = new ScaleTransition(Duration.millis(100), where);
//        st.setByX(1f);
//        st.setByY(1f);
//        st.setCycleCount(2);
//        st.setAutoReverse(true);
//        st.play();

//        TranslateTransition tt = new TranslateTransition(Duration.millis(150), where);
//        tt.setByX(200f);
//        tt.setCycleCount(2);
//        tt.setAutoReverse(true);
//        tt.play();

        Parent root = FXMLLoader.load(controller.getClass().getResource(url));
        where.getChildren().setAll(root);
        return root;
    }

    public static void loadInsidePanel(String url, Pane where, Initializable controller) throws IOException {
        Pane root = FXMLLoader.load(controller.getClass().getResource(url));
        where.getChildren().add(root);
    }

    public static void loadNewWindow(String url, Initializable controller) throws IOException {
        Stage window = new Stage();
        Parent root = FXMLLoader.load(controller.getClass().getResource(url));
        Scene sc = new Scene(root);
        window.setScene(sc);
        window.show();
    }
}
