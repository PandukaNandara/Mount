package lk.ijse.mountCalvary.controller.student;

import com.healthmarketscience.jackcess.impl.UnsupportedCodecException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.model.StudentDTO;
import net.ucanaccess.jdbc.UcanaccessSQLException;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
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
    @FXML
    private JFXButton btShowParms;
    @FXML
    private JFXButton btTest;
    private StringProperty bcid = new SimpleStringProperty();
    private StringProperty desc = new SimpleStringProperty();
    private StringProperty quit = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty mother_name = new SimpleStringProperty();
    private StringProperty father_name = new SimpleStringProperty();
    private StringProperty aClass = new SimpleStringProperty();
    private StringProperty dob = new SimpleStringProperty();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty sName = new SimpleStringProperty();
    private StringProperty sid = new SimpleStringProperty();
    private StringProperty table_name = new SimpleStringProperty();

    private Stage paramsShower = new Stage();
    private JFXTreeView dbTable = new JFXTreeView();

    private ObservableList<StringProperty> parameters = FXCollections.observableArrayList();

    {
        paramsShower.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (paramsShower.isShowing())
                btShowParms.setDisable(true);
            else
                btShowParms.setDisable(false);
        });
        paramsShower.getIcons().setAll(ApplicationIcons.getDefaultIcon());
        dbTable.setStyle("-fx-border-color: transparent");
        dbTable.getStylesheets().add("/lk/ijse/mountCalvary/css/table-view.css");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acImportStudentDetails);

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        loadParameters();
        if (cbxDefault.isSelected()) {
            disableOrEnableTextField(true);
        }
    }

    private void loadParameters() {
        loadStudentDetails();
        parameters.addAll(table_name, sid, sName, gender, dob, aClass,
                father_name, mother_name, address, quit, bcid, desc);
        table_name.bind(txtTableName.textProperty());

        sid.bind(txtStudentID.textProperty());
        sName.bind(txtStudentName.textProperty());
        gender.bind(txtGender.textProperty());
        dob.bind(txtDOB.textProperty());
        aClass.bind(txtClass.textProperty());
        father_name.bind(txtFatherName.textProperty());
        mother_name.bind(txtMotherName.textProperty());
        address.bind(txtAddress.textProperty());
        quit.bind(txtQuit.textProperty());
        bcid.bind(txtBCID.textProperty());
        desc.bind(txtDecs.textProperty());

    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askQuestion("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml",
                    this.acImportStudentDetails, this);
        }
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
            if (isRightPath())
                if (checkParameters()) {
                    if (Files.exists(this.path)) {
                        String pathToString = this.path.toString();
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
                        OptionPane.showErrorAtSide("The database does not existed.");
                    }
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
            clearAllTextField();
            loadParameters();
            disableOrEnableTextField(true);
        } else {
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

    @FXML
    private void btShowParms_onAction(ActionEvent actionEvent) {

        if (!isRightPath()) {
            return;
        }
        String tableName = txtTableName.getText();
        try {
            ArrayList<String> list = studentBOImpl.showTablesAndDescTables(
                    path.toAbsolutePath().toString(),
                    tableName);
            showTablesAndDescTables(list, path.getFileName().toString(), tableName);
        } catch (UcanaccessSQLException e) {
            OptionPane.showWarningAtSide("cannot find a table called " + tableName);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    private boolean isRightPath() {
        if (path == null) {
            OptionPane.showErrorAtSide("Please specify the path of database.");
            return false;
        } else return true;

    }

    @FXML
    private void btTest_onAction(ActionEvent actionEvent) {
        if (checkParameters())
            OptionPane.showDoneAtSide("All parameters are valid.");
    }

    private boolean checkParameters() {
        if (!checkTableName())
            return false;
        for (StringProperty parameter : parameters) {
            String parm = parameter.get();
            if(parm.equals(txtTableName.getText()))
                continue;
            String[] subParms = parm.split("[+]");
            for (String oneParm : subParms)
                if (!checkColumnName(oneParm))
                    return false;
        }
        return true;
    }

    private boolean checkTableName() {
        try {
            if (isRightPath()) {
                if (!studentBOImpl.checkTableName(path.toAbsolutePath().toString(), table_name.get())) {
                    OptionPane.showErrorAtSide(String.format("Cannot find a table called '%s'", table_name.get()));
                    return false;
                } else
                    return true;
            }
        } catch (Exception e) {
            OptionPane.showErrorAtSide("Something's wrong we can't do your request.");
            callLogger(e);
            return false;
        }
        return false;
    }

    private boolean checkColumnName(String columnName) {
        try {
            if (isRightPath()) {
                if (!studentBOImpl.checkColumnName(path.toAbsolutePath().toString(), table_name.get(), columnName)) {
                    OptionPane.showErrorAtSide(String.format("Cannot find a column called '%s'", columnName));
                    return false;
                } else
                    return true;
            } else return false;
        } catch (Exception e) {
            OptionPane.showErrorAtSide("Something's wrong we can't do your request.");
            callLogger(e);
            return false;
        }
    }

    private void showTablesAndDescTables(ArrayList<String> list, String title, String tableName) {
        if (paramsShower.isShowing())
            return;

        TreeItem root = new TreeItem(tableName);
        dbTable.setRoot(root);
        root.setExpanded(true);
        for (String column : list) {
            makeBranch(column, root);
        }
        dbTable.setPrefSize(400, 550);
        paramsShower.setScene(new Scene(new VBox(dbTable), 400, 400));
        paramsShower.setResizable(false);
        paramsShower.setTitle(title);
        paramsShower.showAndWait();
    }

    private void makeBranch(String name, TreeItem parent) {
        TreeItem treeItem = new TreeItem(name);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
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
