package lk.ijse.mountCalvary.controller.activity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.EventBO;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.EventDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewEventForActivity_controller implements Initializable {

    @FXML
    private VBox acNewEvntForActivity;

    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXTextField txtEventName;

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
    @FXML
    private JFXCheckBox cbxMale;
    @FXML
    private JFXCheckBox cbxFemale;
    @FXML
    private JFXCheckBox cbxMix;

    private ActivityBO activityBOImpl;
    private ArrayList<ActivityDTO> allActivity;
    private EventBO eventBOImpl;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acNewEvntForActivity);
        colOldEventName_tblOldEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colOldEventGender_tblOldEvent.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        colNewEventName_tblNewEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colNewGender_tblNewEvent.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        eventBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT);
        try {
            loadActivityWithOldEvent();
        } catch (Exception e) {
            Logger.getLogger(NewEventForActivity_controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void loadActivityWithOldEvent() throws Exception {
        allActivity = activityBOImpl.getAllActivityWithEvent();
        cboxActivityName.getItems().addAll(allActivity);
    }

    @FXML
    void btAdd_onAction(ActionEvent event) {
        String event_name = txtEventName.getText();

        boolean male = cbxMale.isSelected();
        boolean female = cbxFemale.isSelected();
        boolean mix = cbxMix.isSelected();

        if (event_name.length() < 2) {
            OptionPane.showErrorAtSide("Please enter the event name ");
        } else if (!(male || female || mix)) {
            OptionPane.showErrorAtSide("Please select a gender gender");
        } else {
            if (cboxActivityName.getSelectionModel().getSelectedItem() != null) {
                int AID = cboxActivityName.getSelectionModel().getSelectedItem().getAID();

                if (male) tblNewEvent.getItems().add(new EventDTO(event_name, EventDTO.MALE, AID));

                if (female) tblNewEvent.getItems().add(new EventDTO(event_name, EventDTO.FEMALE, AID));

                if (mix) tblNewEvent.getItems().add(new EventDTO(event_name, EventDTO.MIXED, AID));

            } else {
                OptionPane.showErrorAtSide("Please select the activity");
            }
        }
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askQuestion("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewEvntForActivity, this);
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
                        OptionPane.showDoneAtSide("Events has successfully added");
                        //What the hell I did here???
//
//                        Stage thisWindow = (Stage) (this.acNewEvntForActivity.getScene().getWindow());
//                        String window = thisWindow.getClass().getName();
//                        System.out.println(window);
//                        if (window.equals("temp")) {
//                            thisWindow.close();
//                        } else {
//
//                        }
                        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/ActivityMenu.fxml", this.acNewEvntForActivity, this);

                    } else {
                        OptionPane.showErrorAtSide("Something's wrong we can't do your request");
                    }
                } catch (Exception e) {
                    Logger.getLogger(NewEventForActivity_controller.class.getName()).log(Level.SEVERE, null, e);
                    OptionPane.showErrorAtSide("Something's wrong we can't do your request");
                }
            } else {
                OptionPane.showErrorAtSide("Please add some event");
            }
        } else {
            OptionPane.showErrorAtSide("Please select the activity");
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
