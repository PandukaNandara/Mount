package lk.ijse.mountCalvary.controller.competition;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.*;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventForCompetition_controller implements Initializable {
    @FXML
    private Label lblHead;

    @FXML
    private JFXButton btSubmit;

    @FXML
    private AnchorPane acEventForCompetition;


    @FXML
    private JFXButton btAddEvent_EventForActivity;

    @FXML
    private TableView<EventListDTO> tblEventList;

    @FXML
    private TableColumn<EventListDTO, EventDTO> colEvent_tblEventList;

    @FXML
    private TableColumn<EventListDTO, String> colGender_tblEventList;

    @FXML
    private TableColumn<EventListDTO, AgeGroupDTO> colAgeGroup_tblEventList;

    @FXML
    private JFXButton btRemove_tblEventList;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivity;

    @FXML
    private TableView<EventDTO> tblEventListOfActivity;

    @FXML
    private TableColumn<EventDTO, String> colEvent_tblEventListOfActivity;

    @FXML
    private TableColumn<EventDTO, String> colGender_tblEventListOfActivity;


    @FXML
    private JFXComboBox<AgeGroupDTO> cboxAgeGroup;

    @FXML
    private JFXButton btCreateNewGroup;
    @FXML
    private JFXComboBox<CompetitionDTO> cboxCompetition;
    @FXML
    private JFXButton btCreateNewEvent;
    @FXML
    private JFXButton btCancel;


    private CompetitionBO competitionBOImpl;
    private AgeGroupBO ageGroupBOImpl;
    private ActivityBO activityBOImpl;
    private EventListBO eventListBOImpl;
    private EventBO eventBOImpl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //columns of event of activity
        colEvent_tblEventListOfActivity.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender_tblEventListOfActivity.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        //columns of event list
        colAgeGroup_tblEventList.setCellValueFactory(new PropertyValueFactory<>("AgeGroupDTO"));
        colEvent_tblEventList.setCellValueFactory(new PropertyValueFactory<>("EventDTO"));
        colGender_tblEventList.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);
        ageGroupBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.AGE_GROUP);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        eventListBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT_LIST);
        eventBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT);

        try {
            loadCompetition();
            loadAgeGroup();
            loadActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hitComboBox() {
        tblEventList.getItems().removeAll(tblEventList.getItems());
        ObservableList<EventListDTO> eventListDTOS = null;
        try {
            eventListDTOS = eventListBOImpl.getEventListForThisCompetition(cboxCompetition.getSelectionModel().getSelectedItem().getCID());
            tblEventList.getItems().setAll(eventListDTOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadCompetition() throws Exception {
        cboxCompetition.getItems().setAll(competitionBOImpl.getAllCompetition());
    }

    private void loadAgeGroup() throws Exception {
        cboxAgeGroup.getItems().setAll(ageGroupBOImpl.getAgeGroups());
    }

    private void loadActivity() throws Exception {
        ObservableList<ActivityDTO> allActivity = FXCollections.observableArrayList(activityBOImpl.getAllActivity());
        cboxActivity.getItems().setAll(allActivity);
    }

    @FXML
    void btAddEvent_EventForActivity_onAction(ActionEvent event) {
        AgeGroupDTO ageGroupDTO = cboxAgeGroup.getSelectionModel().getSelectedItem();
        EventDTO eventDTO = tblEventListOfActivity.getSelectionModel().getSelectedItem();

        ObservableList<EventListDTO> eventListDTOS = tblEventList.getItems();
        if (eventDTO == null) {
            Common.showError("Please select an event from event table of the activity");
        } else if (ageGroupDTO == null) {
            Common.showError("Please select the age group");
        } else {
            CompetitionDTO competitionDTO = cboxCompetition.getSelectionModel().getSelectedItem();
            EventListDTO adding = new EventListDTO(eventDTO, competitionDTO, ageGroupDTO);
            int i = 0;
            boolean isAdded = false;
            for (EventListDTO oneEventListDTO : eventListDTOS) {

                if (adding.getEID() == oneEventListDTO.getEID() && adding.getGID() == oneEventListDTO.getGID()) {
                    isAdded = true;
                    break;
                }
                i++;
            }
            if (isAdded) {
                Common.showError("This event has been already added for this age group");
                tblEventList.scrollTo(i);
                tblEventList.getSelectionModel().select(i);
            } else {
                adding.setNewEventList(true);
                tblEventList.getItems().add(adding);
            }
        }
    }

    @FXML
    private void cboxCompetition_onAction(ActionEvent actionEvent) {
        hitComboBox();
    }

    @FXML
    void btCreateNewGroup_onAction(ActionEvent event) {
    }

    @FXML
    void btRemove_tblEventList_onAction(ActionEvent event) {
        EventListDTO selectedItem = tblEventList.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            if(selectedItem.isNewEventList()){
                Common.removeItemFromTable(tblEventList);
            }else {
                Common.showError("This event has already added. You cannot remove it.");
            }
        }else{
            Common.showError("Please select an event to remove.");
        }
    }

    @FXML
    void cboxActivity_onAction(ActionEvent event) {
        try {
            ObservableList<EventDTO> eventDTOS = eventBOImpl.getEventForThisActivity(cboxActivity.getSelectionModel().getSelectedItem().getAID());
            tblEventListOfActivity.getItems().setAll(eventDTOS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void colAgeGroup_onAction(ActionEvent event) {

    }

    @FXML
    void colEventName_onAction(ActionEvent event) {

    }

    public void setLblHead(String text) {
        lblHead.setText(text);
    }

    @FXML
    private void btSubmit_onAction(ActionEvent actionEvent) {
        CompetitionDTO competitionDTO = cboxCompetition.getSelectionModel().getSelectedItem();

        if (competitionDTO != null) {
            ObservableList<EventListDTO> allEventList = tblEventList.getItems();
            ArrayList<EventListDTO> newEventList = new ArrayList<>();
            for (EventListDTO oneEvent : allEventList) {
                if (oneEvent.isNewEventList()) {
                    newEventList.add(oneEvent);
                }
            }
            if (newEventList.size() == 0) {
                if (Common.askWarning("You don't add any event for the competition. Do you wish to continue to add student for the list?"))
                    goAhead();
            } else {
                try {
                    if (eventListBOImpl.addAllEventList(newEventList)) {
                        boolean next = Common.askQuestion("Event List has successfully updated. Do you want add student for competition? You can add them later.");
                        if (next) {
                            goAhead();
                        } else {
                            ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acEventForCompetition, this);
                        }
                    } else {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    Common.showError("Something's wrong we can't do your request now");
                    e.printStackTrace();

                }
            }
        } else {
            Common.showError("Please select the competition");
        }
    }

    private void goAhead() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();

            Pane p = fxmlLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/competition/StudentForCompetition.fxml").openStream());

            StudentForCompetition_controller fooController = (StudentForCompetition_controller) fxmlLoader.getController();

            this.acEventForCompetition.getChildren().setAll(p);

            fooController.setSelectedItem(cboxCompetition.getSelectionModel().getSelectedIndex());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btCancel(ActionEvent actionEvent) {
        boolean answer = Common.askQuestion("Do you want to cancel?");
        if (answer) {
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acEventForCompetition, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void btCreateNewEvent_onAction(ActionEvent actionEvent) {
        try {

            ///Parent parent = ScreenLoader.loadNewWindow("", this);
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            Pane p = fxmlLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/activity/NewEventForActivity.fxml").openStream());
//            NewEventForActivity_controller fooController = (NewEventForActivity_controller) fxmlLoader.getController();
//
//            Stage stage = new Stage();
//            stage.setScene(new Scene(p));
//            stage.showAndWait();

            ScreenLoader.loadNewWindow("/lk/ijse/mountCalvary/view/activity/NewEventForActivity.fxml", this);

            loadActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setSelectedItem(int i) {
        try {
            cboxCompetition.getSelectionModel().select(i);
        } catch (IndexOutOfBoundsException e) {
            cboxCompetition.getSelectionModel().selectFirst();
        }
        hitComboBox();
    }

}

//Junk
//        tblEventList.setRowFactory(row -> new TableRow<EventListDTO>() {
//            @Override
//            public void updateItem(EventListDTO item, boolean empty) {
//                super.updateItem(item, empty);
//                System.out.println(getChildren());
//                if (item != null && !empty && !item.isNewEventList()) {
//                    System.out.println(getChildren());
//                    System.out.println(tblEventList.getItems().indexOf(item) + " == " + item);
//                    getChildren().get(tblEventList.getItems().indexOf(item) + 1).setStyle("-fx-background-color: yellow");
//                    tblEventList.setStyle("-fx-background-color: green");
//                }
//                System.out.println(item + "  IsEmpty = " + empty);
//                if (!item.isNewEventList()) {
//                    System.out.println(item);
//                }
//            }
//        });
