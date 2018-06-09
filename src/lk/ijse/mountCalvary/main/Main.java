package lk.ijse.mountCalvary.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/basic/MainMenuFrame.fxml"));
        primaryStage.setTitle("Mount Calvary Extra curriculum activity management system");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
