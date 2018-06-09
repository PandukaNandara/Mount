package lk.ijse.mountCalvary.controller.activity.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.model.ActivityDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ActivityProfileController implements Initializable {

    @FXML
    private BorderPane ActivityProfile;
    //    @FXML
//    private JFXButton btPrint;
//    @FXML
//    private JFXButton btExcel;
    @FXML
    private AnchorPane bpActivityProfileTop;
    @FXML
    private JFXButton btSearch;
    @FXML
    private AnchorPane activityDetail;
    @FXML
    private AnchorPane attendantSheet;
    @FXML
    private AnchorPane joinedStudent;
    @FXML
    private AnchorPane eventAndCompetitionOfActivity;
    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;
    @FXML
    private AnchorPane activityPayment;

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
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);

        try {
            loadAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
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

//    @FXML
//    private void btPrint_onAction(ActionEvent actionEvent) {
//
//    }
//
//    @FXML
//    private void btExcel_onAction(ActionEvent actionEvent) {
//
//    }

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
            Common.showError("Please select an activity");
        }
    }

}