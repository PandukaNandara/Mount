package lk.ijse.mountCalvary.controller.settings;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.db.DBConnection;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings_controller implements Initializable {

    @FXML
    private AnchorPane acSettings;

    @FXML
    private JFXButton btBackup;

    @FXML
    private JFXButton btRestore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
    }

    @FXML
    void btBackup_onAction(ActionEvent event) {
        try {
            if (!Common.askQuestion("Are you sure want to backup your data?"))
                return;
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Choose your location to save backup");

            String fileName = String.format("Backup - %s - %s",
                    DBConnection.getDbDetails().get("db"),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("SQL file (*.sql)", "*.sql"));

            fileChooser.setInitialFileName(fileName);

            File saveFile = fileChooser.showSaveDialog(acSettings.getScene().getWindow());

            System.out.println(saveFile);
            if (saveFile != null) {

                boolean writeBackup = BackpAndRestore.writeBackup(saveFile.getAbsolutePath());

                if (writeBackup) {


                    Common.showMessage("Successfully backup file is created.");
                } else {
                    Common.showMessage("Something's wrong. we can't do your request.");
                }
            }

        } catch (Exception e) {
            Common.showMessage("Something's wrong. we can't do your request.");
            Logger.getLogger(Settings_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void btRestore_onAction(ActionEvent event) {
        try {
            if (!Common.askQuestion("Are you sure want to restore your data? Your current data will be deleted."))
                return;
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Choose your location to save backup");

            fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("SQL file (*.sql)", "*.sql"));

            File openFile = fileChooser.showOpenDialog(acSettings.getScene().getWindow());

            System.out.println(openFile);
            if (openFile != null) {
                boolean restoreBackup = BackpAndRestore.restoreBackup(openFile.getAbsolutePath());
                if (restoreBackup) {
                    Common.showMessage("Successfully backup file is restored.");
                } else {
                    Common.showMessage("Something's wrong. we can't do your request.");
                }
            }

        } catch (Exception e) {
            Common.showMessage("Something's wrong. we can't do your request.");
            Logger.getLogger(Settings_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
