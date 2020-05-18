package lk.ijse.mountCalvary.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.tool.ApplicationIcons;
import lk.ijse.mountCalvary.tool.OptionPane;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class Main extends Application {

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
                OptionPane.showErrorAtSide("The log file has been deleted.");
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//Test
            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/logIn/LogIn.fxml"));

//            Main
//            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/logIn/LogIn.fxml"));

            primaryStage.setTitle("Log in");
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();


            primaryStage.getIcons().add(ApplicationIcons.getDefaultIcon());
//            primaryStage.setResizable(false);

            primaryStage.show();

        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
