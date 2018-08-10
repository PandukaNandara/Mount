package lk.ijse.mountCalvary.controller.student;

import com.healthmarketscience.jackcess.impl.UnsupportedCodecException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/5/2018
 * Time: 3:23 PM
 */

public final class ImportStudentAndPreview_controller extends SuperController implements Initializable {

    ScreenLoader screenLoader = ScreenLoader.getInstance();
    HashMap<String, String> parms = ExternalDatabaseParameters.getInstance().getParms();
    @FXML
    private VBox acImportStudentDetails;
    @FXML
    private JFXTextField txtDatabasePath;
    @FXML
    private JFXButton btImport;
    @FXML
    private JFXCheckBox cbxDefault;
    @FXML
    private JFXTextField txtTableName;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXTextField txtGender;
    @FXML
    private JFXTextField txtDOB;
    @FXML
    private JFXTextField txtClass;
    @FXML
    private JFXTextField txtFatherName;
    @FXML
    private JFXTextField txtMotherName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtQuit;
    @FXML
    private JFXTextField txtBCID;
    @FXML
    private JFXTextField txtDecs;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btPreview;
    private Path path;
    private StudentBO studentBOImpl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acImportStudentDetails);

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        if (cbxDefault.isSelected()) {
            loadStudentDetails();
            disableOrEnableTextField(true);
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {

    }

    @FXML
    void btImport_onAction(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose your database");
            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("MS Access Database (.mdb)", "*.mdb"),
                    new FileChooser.ExtensionFilter("MS Office Access (*.accdb)", "*.accdb"),
                    new FileChooser.ExtensionFilter("All (*)", "*.*")
            );
            File openedFile = fileChooser.showOpenDialog(acImportStudentDetails.getScene().getWindow());
            if (openedFile != null) {
                path = openedFile.toPath();
                txtDatabasePath.setText(openedFile.getAbsolutePath());
            }

//        }catch (Unex){
        } catch (Exception e) {
            OptionPane.showErrorAtSide("Something's wrong. we can't do your request.");
            callLogger(e);
        }
    }

    @FXML
    void btPreview_onAction(ActionEvent event) {
        try {
            if (Objects.isNull(path)) {
                OptionPane.showErrorAtSide("Please select a file.");
            } else if (Files.exists(this.path)) {
                String pathToString = this.path.toString();
                OptionPane.showDoneAtSide(pathToString);
                final String dbPath = path.toString();
                final String tableName = txtTableName.getText();
                final String SID = txtStudentID.getText();
                final String sName = txtStudentName.getText();
                final String gender = txtGender.getText();
                final String DOB = txtDOB.getText();
                final String class_ = txtClass.getText();
                final String fatherName = txtFatherName.getText();
                final String motherName = txtMotherName.getText();
                final String note = txtDecs.getText();
                final String sAddress = txtAddress.getText();
                final String quit = txtQuit.getText();
                final String BCID = txtBCID.getText();

                ObservableList<StudentDTO> allStudentFromExternalDB = studentBOImpl.getAllStudentFromExternalDB(dbPath, tableName,
                        SID, sName, gender,
                        DOB, class_, fatherName,
                        motherName, note, sAddress, quit, BCID);


                openImportingScreen(allStudentFromExternalDB);
            } else {
                OptionPane.showErrorAtSide("The database is not existed.");
            }
        } catch (UnsupportedCodecException e) {
            OptionPane.showErrorAtSide("The file you have selected does not support.");
        } catch (Exception e) {
            OptionPane.showErrorAtSide(e.getMessage());
            callLogger(e);
        }
    }

    private void openImportingScreen(ObservableList<StudentDTO> allStudentFromExternalDB) {

        ImportStudents_controller previewer = screenLoader.loadNewWindow("/lk/ijse/mountCalvary/view/student/ImportStudent.fxml",
                "Preview of Student data");
        previewer.setStudentData(allStudentFromExternalDB);
    }

    @FXML
    void cbxDefault_onAction(ActionEvent event) {
        if (cbxDefault.isSelected()) {
            loadStudentDetails();
            disableOrEnableTextField(true);
        } else {
            clearAllTextField();
            disableOrEnableTextField(false);
        }
    }

    private void loadStudentDetails() {
        txtTableName.setText(parms.get("table_name"));
        txtStudentID.setText(parms.get("SID"));
        txtStudentName.setText(parms.get("sName"));
        txtGender.setText(parms.get("gender"));
        txtDOB.setText(parms.get("DOB"));
        txtClass.setText(parms.get("class"));
        txtFatherName.setText(parms.get("father_name"));
        txtMotherName.setText(parms.get("mother_name"));
        txtAddress.setText(parms.get("address"));
        txtQuit.setText(parms.get("quit"));
        txtBCID.setText(parms.get("BCID"));
        txtDecs.setText(parms.get("desc"));
    }

    private void clearAllTextField() {
        Common.clearTextFields(
                txtTableName,
                txtStudentID,
                txtStudentName,
                txtGender,
                txtDOB,
                txtClass,
                txtFatherName,
                txtMotherName,
                txtAddress,
                txtQuit,
                txtBCID,
                txtDecs
        );
    }

    private void disableOrEnableTextField(boolean shouldDisable) {
        Common.disableTextFields(shouldDisable,
                txtTableName,
                txtStudentID,
                txtStudentName,
                txtGender,
                txtDOB,
                txtClass,
                txtFatherName,
                txtMotherName,
                txtAddress,
                txtQuit,
                txtBCID,
                txtDecs
        );
    }
}
