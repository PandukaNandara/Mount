package lk.ijse.mountCalvary.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


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

    public void loadWindow(String url, Pane pane, Initializable controller) throws IOException {
        Parent root = FXMLLoader.load(controller.getClass().getResource(url));

        Scene sc = new Scene(root);
        Stage window = (Stage) pane.getScene().getWindow();

        window.setScene(sc);
        window.show();
    }

    public Parent loadOnCenterOfBorderPane(String url, Pane where, Initializable controller) throws IOException {

        FadeTransition t = new FadeTransition(Duration.millis(200), where);
        t.setFromValue(0.0);
        t.setToValue(5.0);
        t.play();

        Parent root = FXMLLoader.load(controller.getClass().getResource(url));

        borderPane.setCenter(root);

        return root;
//       System.out.println("Pass");
//        where.getChildren().setAll(root);
    }

    public void loadOnCenterOfBorderPane(String url, BorderPane borderPane, Initializable controller) throws IOException {
        this.borderPane = borderPane;
        Parent root = FXMLLoader.load(controller.getClass().getResource(url));
        borderPane.setCenter(root);

        FadeTransition t = new FadeTransition(Duration.millis(200), borderPane.getCenter());
        t.setFromValue(0.0);
        t.setToValue(5.0);
        t.play();

    }

    public void loadInsidePanel(String url, Pane where, Initializable controller) throws IOException {
        Pane root = FXMLLoader.load(controller.getClass().getResource(url));
        where.getChildren().add(root);
    }

    public void loadNewWindow(String url, Initializable controller) throws IOException {
        Stage window = new Stage();
        Parent root = FXMLLoader.load(controller.getClass().getResource(url));
        Scene sc = new Scene(root);
        window.setScene(sc);
        window.show();
    }

}
