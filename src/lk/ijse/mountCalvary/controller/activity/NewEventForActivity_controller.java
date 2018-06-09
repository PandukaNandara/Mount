package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.EventBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.EventDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewEventForActivity_controller implements Initializable {

    @FXML
    private AnchorPane acNewEvntForActivity;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXTextField txtEventName;

    @FXML
    private JFXRadioButton rbxMale;

    @FXML
    private JFXRadioButton rbxFemale;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivityName;

    @FXML
    private JFXButton btRemove;

    @FXML
    private TableView<EventDTO> tblOldEvent;

    @FXML
    private TableView<EventDTO> tblNewEvent;

    @FXML
    private TableColumn<EventDTO, String> colNewEventName_tblNewEvent;

    @FXML
    private TableColumn<EventDTO, String> colNewGender_tblNewEvent;

    @FXML
    private TableColumn<EventDTO, String> colOldEventName_tblOldEvent;

    @FXML
    private TableColumn<EventDTO, String> colOldEventGender_tblOldEvent;

    private ActivityBO activityBOImpl;
    private ArrayList<ActivityDTO> allActivity;
    private EventBO eventBOImpl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colOldEventName_tblOldEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colOldEventGender_tblOldEvent.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        colNewEventName_tblNewEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colNewGender_tblNewEvent.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        eventBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT);
        try {
            loadActivityWithOldEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadActivityWithOldEvent() throws Exception {
        allActivity = activityBOImpl.getAllActivityWithEvent();
        cboxActivityName.getItems().addAll(allActivity);
    }

    @FXML
    void btAdd_onAction(ActionEvent event) {
        String event_name = txtEventName.getText();
        boolean male = rbxMale.isSelected();
        boolean female = rbxFemale.isSelected();
        if (event_name.length() < 2) {
            Common.showError("Please enter the event name ");
        } else if (!(male || female)) {
            Common.showError("Please select the gender of the event ");
        } else {
            if (cboxActivityName.getSelectionModel().getSelectedItem() != null) {
                int AID = cboxActivityName.getSelectionModel().getSelectedItem().getAID();

                if (male) {
                    tblNewEvent.getItems().add(new EventDTO(event_name, EventDTO.MALE, AID));
                }
                if (female) {
                    tblNewEvent.getItems().add(new EventDTO(event_name, EventDTO.FEMALE, AID));
                }
            } else {
                Common.showError("Please select the activity");
            }
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = Common.askQuestion("Do you want to cancel?");
        if (answer) {
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewEvntForActivity, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btRemove_onAction(ActionEvent event) {
        Common.removeItemFromTable(tblNewEvent);
    }

    @FXML
    void btSubmit_onAction(ActionEvent event) {
        ActivityDTO activityDTO = cboxActivityName.getSelectionModel().getSelectedItem();
        if (activityDTO != null) {
            ObservableList<EventDTO> eventList = tblNewEvent.getItems();
            if (eventList.size() != 0) {
                try {
                    if (eventBOImpl.addAllEvent(eventList)) {
                        Common.showMessage("Events has successfully added");
                        try {
                            Stage thisWindow =  (Stage) (this.acNewEvntForActivity.getScene().getWindow());
                            String window = thisWindow.getClass().getName();
                            System.out.println(window);
                            if (window.equals("temp")) {
                                thisWindow.close();
                            }else{
                                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acNewEvntForActivity, this);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Common.showError("Something's wrong we can't do your request");
                    }
                } catch (Exception e) {
                    Common.showError("Something's wrong we can't do your request");
                    e.printStackTrace();
                }
            } else {
                Common.showError("Please add some event");
            }
        } else {
            Common.showError("Please select the activity");
        }
    }

    @FXML
    void cboxActivityName_onAction(ActionEvent event) {
        tblOldEvent.setItems(cboxActivityName.getSelectionModel().getSelectedItem().getEventDTOS());
    }

    @FXML
    void txtEventName_onAction(ActionEvent event) {

    }

}
