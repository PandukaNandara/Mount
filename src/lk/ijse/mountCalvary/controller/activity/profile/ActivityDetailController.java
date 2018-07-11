package lk.ijse.mountCalvary.controller.activity.profile;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.TeacherDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityDetailController implements Initializable {

    ActivityProfileController activityProfileController;
    @FXML
    private VBox ActivityDetail;
    @FXML
    private JFXTextField txtActivityName;
    @FXML
    private JFXTextField txtTeacherInCharge;
//    @FXML
//    private JFXRadioButton cboxNonPhysicalActivity;
//    @FXML
//    private JFXRadioButton cboxPhysicalActivity;

    private ArrayList<ActivityDTO> allActitivy;

    private TeacherBO teacherBOImpl;
    private ArrayList<TeacherDTO> allTeacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(ActivityDetail);
        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);
        try {
            loadTeacher();

        } catch (Exception e) {
            Logger.getLogger(ActivityDetailController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void loadTeacher() throws Exception {
        allTeacher = teacherBOImpl.getAllTeacher();
    }

    public void init(ActivityProfileController activityProfileController) {
        this.activityProfileController = activityProfileController;
        allActitivy = activityProfileController.getAllActivity();
    }

    public void insertActivity(ActivityDTO activityDTO) {
        for (TeacherDTO oneTeacher : allTeacher) {
            if (activityDTO.getTID() == oneTeacher.getTID()) {
                txtActivityName.setText(activityDTO.getaName());
                txtTeacherInCharge.setText(oneTeacher.getTName());
            }
        }
    }
}
