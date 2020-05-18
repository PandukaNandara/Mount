package lk.ijse.mountCalvary.controller.settings;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.basic.MainMenuFrame_controller;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.OptionPane;
import lk.ijse.mountCalvary.db.DBConnection;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/10/2018
 * Time: 9:15 PM
 */

public final class Settings_controller extends SuperController implements Initializable {

    @FXML
    private VBox acSettings;

    @FXML
    private JFXButton btBackup;

    @FXML
    private JFXButton btRestore;

    @FXML
    private JFXButton btImport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        MainMenuFrame_controller.getMainMenuFrame().showSideBar();
        ButtonFireForEnterSetter.setGlobalEventHandler(acSettings);
    }

    @FXML
    void btBackup_onAction(ActionEvent event) {
        try {
            if (!OptionPane.askQuestion("Are you sure want to backup your data?"))
                return;
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Choose your location to save backup");

            String fileName = String.format("Backup - %s - %s",
                    DBConnection.getInstance().getDbDetails().get("db"),
                    new SimpleDateFormat("dd.MM.yyyy - hh.mm aa").format(new Date()));

            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("SQL file (*.sql)", "*.sql"));

            fileChooser.setInitialFileName(fileName);

            File saveFile = fileChooser.showSaveDialog(acSettings.getScene().getWindow());

            if (saveFile != null) {
                boolean writeBackup = BackupAndRestore.writeBackup(saveFile);
                if (writeBackup) {
                    OptionPane.showDoneAtSide("Backup file is Successfully created.");
                } else {
                    OptionPane.showWarningAtSide("Something's wrong. we can't do your request.");
                }
            }

        } catch (Exception e) {
            OptionPane.showWarningAtSide("Something's wrong. we can't do your request.");
            callLogger(e);
        }
    }

    @FXML
    void btRestore_onAction(ActionEvent event) {
        try {
            if (!OptionPane.askWarning("Are you sure want to restore your data? Your current data will be deleted."))
                return;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose your file to restore the database.");
            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("SQL file (*.sql)", "*.sql")
            );
            File openFile = fileChooser.showOpenDialog(acSettings.getScene().getWindow());
            if (openFile != null) {
                boolean restoreBackup = BackupAndRestore.restoreBackup(openFile.getAbsolutePath());
                if (restoreBackup) {
                    OptionPane.showMessage("Successfully backup file is restored.");
                } else {
                    OptionPane.showMessage("Something's wrong. we can't do your request.");
                }
            }
        } catch (Exception e) {
            OptionPane.showMessage("Something's wrong. we can't do your request.");
            callLogger(e);
        }
    }

    @FXML
    private void btImport_onAction(ActionEvent actionEvent) {
        try {
//            FileChooser fileChooser = new FileChooser();
//
//            fileChooser.setTitle("Choose your location ");
//
//            fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("All file (*)", "*.*"));
//
//            String absolutePath = fileChooser.showOpenDialog(acSettings.getScene().getWindow()).getAbsolutePath();
//
//            MSDBConnection.getInstance().getConnection(absolutePath);
////            if (openFile != null) {
////                boolean restoreBackup = BackupAndRestore.restoreBackup(openFile.getAbsolutePath());
////                if (restoreBackup) {
////                    OptionPane.showMessage("Successfully backup file is restored.");
////                } else {
////                    OptionPane.showMessage("Something's wrong. we can't do your request.");
////                }
////            }
        } catch (Exception e) {
            OptionPane.showMessage("Something's wrong. we can't do your request.");
            callLogger(e);
        }
    }
}
