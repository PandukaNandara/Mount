package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateActivity_controller implements Initializable {

    @FXML
    private AnchorPane acUpdateActivity;

    @FXML
    private JFXComboBox<TeacherDTO> cboxTeacherInCharge;

    @FXML
    private JFXButton btUpdate;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private JFXButton btAddNewTeacher;
    private TeacherBO teacherBOImpl;
    private ActivityBO activityBOImpl;
    @FXML
    private Label lblTacherIncharge;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);

        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        cboxTeacherInCharge.setDisable(true);

        try {
            loadTeacher();
            loadActivity();
        } catch (Exception e) {
        }
    }

    private void loadActivity() throws Exception {
        ArrayList<TeacherDTO> allTeacher = teacherBOImpl.getAllTeacher();
        cboxTeacherInCharge.getItems().addAll(allTeacher);
    }

    private void loadTeacher() throws Exception {
        ArrayList<ActivityDTO> allActivity = activityBOImpl.getAllActivity();
        cboxActivity.getItems().addAll(allActivity);
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        try {
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acUpdateActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxTeacherInCharge_onAction(ActionEvent event) {

    }

    @FXML
    void btUpdate_onAction(ActionEvent event) {
        try {
            ActivityDTO upAct = cboxActivity.getSelectionModel().getSelectedItem();
            upAct.setTID(cboxTeacherInCharge.getSelectionModel().getSelectedItem().getTID());
            try {
                if (activityBOImpl.updateTeacherOfActivity(upAct)) {
                    Common.showMessage("Activity has Successfully updated");
                }
            } catch (Exception e) {
                Logger.getLogger(UpdateActivity_controller.class.getName()).log(Level.SEVERE, null, e);

            }
            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acUpdateActivity, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        cboxTeacherInCharge.setDisable(false);
        int TID = cboxActivity.getSelectionModel().getSelectedItem().getTID();
        ObservableList<TeacherDTO> allTeacher = cboxTeacherInCharge.getItems();
        for (TeacherDTO oneTeacher : allTeacher) {
            if (oneTeacher.getTID() == TID) {
                cboxTeacherInCharge.setValue(oneTeacher);
            }
        }
    }

}
