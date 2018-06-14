package lk.ijse.mountCalvary.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.controller.Common;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            try {
                Logger errorLogger = Logger.getLogger("");
                FileHandler fileHandler = new FileHandler("log/error.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                errorLogger.addHandler(fileHandler);
            } catch (IOException e) {
                Common.showError("The log file has been deleted.");
                e.printStackTrace();

            }

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/LogIn/LogIn.fxml"));
            primaryStage.setTitle("Mount Calvary Extra curriculum activity management system");
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}
