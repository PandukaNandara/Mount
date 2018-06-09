package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class Demo extends Application implements Initializable {
    public static void main(String[] args)throws Exception {
//        try {
//            Desktop desktop = java.awt.Desktop.getDesktop();
//            URI oURL = new URI("http://www.google.com");
//            desktop.browse(oURL);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //File f = new File("/sample/report/Chart.jasper");

    }


    public static void loadNewWindow(String url, Initializable controller) {
//        long i = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis());
//        CompetitionBO competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);
//        System.out.println(competitionBOImpl.getCompetitionWithEventList());
//        System.out.println(System.currentTimeMillis() - i);
        //launch(args);
//        Stage window = new Stage();
//        Parent root = FXMLLoader.load(controller.getClass().getResource(url));
//        Scene sc = new Scene(root);
//        window.setScene(sc);
//        window.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/competition/StudentForCompetition.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
