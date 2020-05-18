package lk.ijse.mountCalvary.controller.activity.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.tool.GlobalBoolean;
import lk.ijse.mountCalvary.tool.OptionPane;
import lk.ijse.mountCalvary.model.ActivityDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public final class ActivityProfileController extends SuperController implements Initializable {

    @FXML
    private BorderPane ActivityProfile;
    @FXML
    private HBox bpActivityProfileTop;
    @FXML
    private JFXButton btSearch;
    @FXML
    private VBox activityDetail;
    @FXML
    private VBox attendantSheet;
    @FXML
    private VBox joinedStudent;
    @FXML
    private VBox eventAndCompetitionOfActivity;
    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;
    @FXML
    private VBox activityPayment;

    @FXML
    private ActivityDetailController activityDetailController;
    @FXML
    private AttendantSheetController attendantSheetController;
    @FXML
    private EventAndCompetitionOfActivityController eventAndCompetitionOfActivityController;
    @FXML
    private JoinedStudentController joinedStudentController;
    @FXML
    private ActivityPaymentController activityPaymentController;

    private ActivityBO activityBOImpl;
    private ArrayList<ActivityDTO> allActivity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(bpActivityProfileTop);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        try {
            loadAllActivity();
        } catch (Exception e) {
            callLogger(e);
        }
        activityDetailController.init(this);
        attendantSheetController.init(this);
        eventAndCompetitionOfActivityController.init(this);
        joinedStudentController.init(this);
        activityPaymentController.init(this);
    }

    private void loadAllActivity() throws Exception {
        allActivity = activityBOImpl.getAllActivity();
        cboxActivity.getItems().setAll(allActivity);
    }

    @FXML
    private void cboxActivity_onAction(ActionEvent actionEvent) {
        btSearch.fire();

    }

    private void showDataOnTabs(ActivityDTO activityDTO) {
        activityDetailController.insertActivity(activityDTO);
        attendantSheetController.insertActivity(activityDTO);
        joinedStudentController.insertActivity(activityDTO);
        activityPaymentController.insertActivity(activityDTO);
        eventAndCompetitionOfActivityController.insertActivity(activityDTO);
    }

    @FXML
    private void txtActivityName_onAction(ActionEvent actionEvent) {
    }

    public ArrayList<ActivityDTO> getAllActivity() {
        return allActivity;
    }

    @FXML
    private void btSearch_onAction(ActionEvent actionEvent) {
        ActivityDTO activityDTO = cboxActivity.getSelectionModel().getSelectedItem();
        if (activityDTO != null) {
            showDataOnTabs(activityDTO);
        } else {
            OptionPane.showErrorAtSide("Please select an activity");
        }
    }

}
