package lk.ijse.mountCalvary.controller.teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.net.URL;
import java.util.ResourceBundle;

public final class NewTeacher_controller extends SuperController implements Initializable {

    @FXML
    private VBox acNewTeacher;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private JFXButton btRemove;

    @FXML
    private JFXTextField txtTeacherName;

    @FXML
    private TableView<TeacherDTO> tblTeacher;

    @FXML
    private TableColumn<TeacherDTO, String> colTeacherName;

    @FXML
    private JFXButton btAdd;


    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    private TeacherBO teacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acNewTeacher);
        teacher = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("tName"));

    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acNewTeacher, this);
    }

    @FXML
    private void btSubmit_onAction(ActionEvent actionEvent) {
        try {
            if (tblTeacher.getItems().size() == 0) {
                OptionPane.showErrorAtSide("Please add a teacher");
            } else if (teacher.addAllTeacher(tblTeacher.getItems())) {
                OptionPane.showMessage("All teachers successfully added");
                screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acNewTeacher, this);
            }

        } catch (Exception e) {
            OptionPane.showErrorAtSide(e.getMessage());
        }
    }

    @FXML
    private void btRemove_tblTeacher_onAction(ActionEvent actionEvent) {
        Common.removeItemFromTable(tblTeacher);
    }

    @FXML
    private void btAdd_onAction(ActionEvent actionEvent) {
        String teacherName = txtTeacherName.getText().trim();
        if (!(teacherName.matches("\\d") || teacherName.length() == 0)) {
            tblTeacher.getItems().add(new TeacherDTO(teacherName));
        } else {
            OptionPane.showErrorAtSide("The teacher name is incorrect");
        }
        txtTeacherName.selectAll();
        txtTeacherName.requestFocus();
    }

    @FXML
    private void txtTeacherName_onAction(ActionEvent actionEvent) {
        btAdd.fire();
    }
}
