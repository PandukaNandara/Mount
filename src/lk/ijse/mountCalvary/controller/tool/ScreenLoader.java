package lk.ijse.mountCalvary.controller.tool;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ScreenLoader {

    private static ScreenLoader ourInstance;

    static {
        ourInstance = new ScreenLoader();
    }

    private BorderPane borderPane;

    private ScreenLoader() {
    }

    public static ScreenLoader getInstance() {
        return ourInstance;
    }

    public void loadWindow(String url, Pane pane, Initializable controller) {
        try {
            Parent root = FXMLLoader.load(controller.getClass().getResource(url));

            Scene sc = new Scene(root);
            Stage window = (Stage) pane.getScene().getWindow();

            window.setScene(sc);
            window.show();
        } catch (Exception e) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Parent loadOnCenterOfBorderPane(String url, Pane where, Initializable controller) {
        try {
            FadeTransition t = new FadeTransition(Duration.millis(200), where);
            t.setFromValue(0.0);
            t.setToValue(5.0);
            t.play();

            Parent root = FXMLLoader.load(controller.getClass().getResource(url));
            borderPane.setCenter(root);

            return root;
        } catch (Exception e) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
//       System.out.println("Pass");
//        where.getChildren().setAll(root);
    }

    public void loadPanel(String url, Pane where, Initializable controller) {
        try {
            FadeTransition t = new FadeTransition(Duration.millis(200), where);
            t.setFromValue(0.0);
            t.setToValue(5.0);
            t.play();

            Parent root = FXMLLoader.load(controller.getClass().getResource(url));
            where.getChildren().setAll(root);

        } catch (Exception e) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadOnCenterOfBorderPane(String url, BorderPane borderPane, Initializable controller) {
        try {
            this.borderPane = borderPane;
            Parent root = FXMLLoader.load(controller.getClass().getResource(url));
            borderPane.setCenter(root);

            FadeTransition t = new FadeTransition(Duration.millis(200), borderPane.getCenter());
            t.setFromValue(0.0);
            t.setToValue(5.0);
            t.play();
        } catch (Exception e) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadInsidePanel(String url, Pane where, Initializable controller) {
        try {
            Pane root = FXMLLoader.load(controller.getClass().getResource(url));
            where.getChildren().add(root);
        } catch (Exception e) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Object loadNewWindow(String url, Pane where, String title) {
        try {
//            Stage window = new Stage();
//            Parent root = FXMLLoader.load(controller.getClass().getResource(url));
//            Scene sc = new Scene(root);
//            window.setScene(sc);
//            window.show();
            FXMLLoader fxmlLoader = new FXMLLoader();

            Pane p = fxmlLoader.load(getClass().getResourceAsStream(url));

            p.getStylesheets().setAll(where.getStylesheets());
            Stage window = new Stage();
            window.setScene(new Scene(p));
            window.setTitle(title);

            window.initStyle(StageStyle.UTILITY);

            window.getIcons().add(ApplicationIcons.getDefaultIcon());
            window.setMaximized(false);
            window.show();
            return fxmlLoader.getController();
        } catch (Exception e) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
