package lk.ijse.mountCalvary.controller.competition;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.*;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentForCompetition_controller implements Initializable {
    ObservableList<RegistrationDTO> filteredRegistration;
    @FXML
    private VBox acStudentForCompetition;
    @FXML
    private JFXButton btFinish;
    @FXML
    private JFXButton btAddStudent;
    @FXML
    private TableView<EventListDTO> tblEventInCompetition;
    @FXML
    private TableColumn<EventListDTO, String> colActivity_tblEventInCompetition;
    @FXML
    private TableColumn<EventListDTO, String> colEvent_tblEventInCompetition;
    @FXML
    private TableColumn<EventListDTO, String> colGender_tblEventInCompetition;
    @FXML
    private TableColumn<EventListDTO, AgeGroupDTO> colAgeGroup_tblEventInCompetition;
    @FXML
    private JFXButton btRemove_tblStudentList;
    @FXML
    private JFXTextField txtEvent;
    @FXML
    private TableView<ParticipationDTO> tblStudentList;
    @FXML
    private TableColumn<ParticipationDTO, String> colEvent_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colStudent_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colResult_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colPerformance_tblStudentList;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXComboBox<String> cboxResult;
    @FXML
    private JFXTextField txtPerformance;
    @FXML
    private JFXTextField txtGender;
    @FXML
    private JFXTextField txtAgeGroup;
    @FXML
    private JFXButton btViewStudentList;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXComboBox<CompetitionDTO> cboxCompetition;
    @FXML
    private JFXTextField txtAge;

    private CompetitionBO competitionBOImpl;
    private ActivityBO activityBOImpl;
    private AgeGroupBO ageGroupBOImpl;
    private EventListBO eventListBOImpl;

    private ObservableList<AgeGroupDTO> ageGroupDTOS;
    private AutoComplete<RegistrationDTO> autoCompleteStudentName;
    private ParticipationBO participationBOImpl;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acStudentForCompetition);


        colActivity_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colAgeGroup_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("ageGroupDTO"));
        colEvent_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        colEvent_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colPerformance_tblStudentList.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colResult_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("result"));
        colStudent_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);

        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);

        ageGroupBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.AGE_GROUP);

        eventListBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT_LIST);

        participationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PARTICIPATION);

        try {
            cboxResult.getItems().setAll(Common.getResultSet());
            loadCompetition();
            ageGroupDTOS = ageGroupBOImpl.getAgeGroups();
//            loadCompetitionWithParticipation();
//            loadActivityWithStudent();
        } catch (Exception e) {
            Logger.getLogger(StudentForCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }
//        txtStudentName.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (tblEventInCompetition.getSelectionModel().isEmpty()) {
//                System.out.println("Yes");
//            } else {
//                System.out.println("No");
//            }
//        });
        autoCompleteStudentName = new AutoComplete<>(txtStudentName);
        autoCompleteStudentName.setAutoCompletionsAction(event -> hit_txtStudentName());
    }

    private void loadCompetition() throws Exception {
        cboxCompetition.getItems().setAll(competitionBOImpl.getAllCompetition());
    }
//
//    private void loadActivityWithStudent() throws Exception {
//
//        activityDTOS = activityBOImpl.getActivityWithStudent();
//
//    }

//    private void loadCompetitionWithParticipation() throws Exception {
//
//        cboxCompetition.getItems().setAll(competitionBOImpl.getAllCompetitionWithParticipation());
//    }

    @FXML
    void cboxCompetition_onAction(ActionEvent event) {
        hitComboBox();
    }

    private void hitComboBox() {
        tblStudentList.getItems().removeAll(tblStudentList.getItems());
        tblEventInCompetition.getItems().removeAll(tblEventInCompetition.getItems());

        int CID = cboxCompetition.getSelectionModel().getSelectedItem().getCID();
        try {
            ObservableList<EventListDTO> eventListDTOS = eventListBOImpl.getEventListForThisCompetition(CID);
            tblEventInCompetition.getItems().setAll(eventListDTOS);
            tblEventInCompetition.getSelectionModel().select(0);
            showResultInTextField(tblEventInCompetition.getSelectionModel().getSelectedItem());
            ObservableList<ParticipationDTO> participationDTOS = participationBOImpl.getParticipationForThisCompetition(CID);
            tblStudentList.getItems().setAll(participationDTOS);
        } catch (Exception e) {
            Logger.getLogger(StudentForCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    private void showResultInTextField(EventListDTO select) {
        txtEvent.setText(select.getEventName());
        txtGender.setText(select.getGenderType());
        txtAgeGroup.setText(ageGroupDTOS.get(select.getGID() - 1).getGroupName());
    }

    public void setSelectedItem(int i) {
        try {
            cboxCompetition.getSelectionModel().select(i);
        } catch (IndexOutOfBoundsException e) {
            cboxCompetition.getSelectionModel().selectFirst();
        }
        hitComboBox();
    }

    @FXML
    void btAddStudent_onAction(ActionEvent event) {
        String performance = txtPerformance.getText().trim();
        if (performance.length() < 2) {
            performance = "-";
        }
        String result = cboxResult.getValue();
        String studentName = txtStudentName.getText();
        if (studentName.length() < 1) {
            OptionPane.showErrorAtSide("Please select the student");
        } else if (result.length() == 0) {
            OptionPane.showErrorAtSide("Please enter the result of the student");
        } else {
            EventListDTO selectedItem = tblEventInCompetition.getSelectionModel().getSelectedItem();
            String eventName = selectedItem.getEventName() + " - " + selectedItem.getActivityName() + " - " + selectedItem.getGenderType();
            RegistrationDTO registrationDTO = autoCompleteStudentName.getSelectedItemByName();
            ParticipationDTO newParticipation = new ParticipationDTO(selectedItem.getELID(), registrationDTO, result, performance, eventName);

            newParticipation.setAsNewOne(true);

            tblStudentList.getItems().add(newParticipation);

            filteredRegistration = filterRegistration(selectedItem);
            autoCompleteStudentName.changeSuggestion(filteredRegistration);
            clearAll();
        }
    }

    @FXML
    private void tblEventInCompetition_mouseClicked(MouseEvent event) {
        hit_tblEventList();
    }

    private void hit_tblEventList() {
        try {
            clearAll();
            EventListDTO selectedItem = tblEventInCompetition.getSelectionModel().getSelectedItem();
            showResultInTextField(selectedItem);

            filteredRegistration = filterRegistration(selectedItem);

            autoCompleteStudentName.changeSuggestion(filteredRegistration);
        } catch (Exception e) {
            Logger.getLogger(StudentForCompetition_controller.class.getName()).log(Level.SEVERE, null, e);


        }
    }

    private ObservableList<RegistrationDTO> filterRegistration(EventListDTO eventList) {
        try {
            ObservableList<ParticipationDTO> participationDTOS = tblStudentList.getItems();
            AgeGroupDTO ageGroup = ageGroupDTOS.get(eventList.getGID() - 1);
            ObservableList<RegistrationDTO> registrationDTOS = activityBOImpl.getRegistrationOfThisActivity(eventList.getAID());
            ArrayList<RegistrationDTO> filter = new ArrayList<>();

            L1:
            for (RegistrationDTO oneRegi : registrationDTOS) {
                for (ParticipationDTO oneParticipationDTO : participationDTOS) {
                    if (oneParticipationDTO.getRID() == oneRegi.getRID() && oneParticipationDTO.getELID() == eventList.getELID()) {
                        continue L1;
                    }
                }
                int age = LocalDate.now().getYear() - Objects.requireNonNull(Common.dateToLocalDate(oneRegi.getDOB())).getYear();
//                int age = LocalDate.now().getYear() - Common.dateToLocalDate(oneRegi.getDOB()).getYear();
                //Later

                if ((oneRegi.isDeserveForEvent(eventList.getGender()) &&
                        age > ageGroup.getMin() && age < ageGroup.getMax())) {
                    oneRegi.setAge(age);
                    filter.add(oneRegi);
                }
            }
            System.out.println(filter);
            return FXCollections.observableArrayList(filter);
        } catch (Exception e) {
            Logger.getLogger(StudentForCompetition_controller.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
        }
    }

    private void clearAll() {
        txtStudentID.clear();
        txtStudentName.clear();
        txtAgeGroup.clear();
        cboxResult.setValue("");
        txtPerformance.clear();
        txtAge.clear();
    }

    @FXML
    void btCancel(ActionEvent event) {
        boolean answer = OptionPane.askQuestion("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acStudentForCompetition, this);
        }
    }

    @FXML
    void txtStudentName_onAction(ActionEvent event) {
        hit_txtStudentName();
    }

    private void hit_txtStudentName() {
        RegistrationDTO reg = autoCompleteStudentName.getSelectedItemByName();
        try {
            if (reg == null) {

                OptionPane.showErrorAtSide("Please insert the corresponding student or select a event of the competition.");

                txtAge.setText("");
                txtStudentID.setText("");

            } else {
                showRegistrationDetail(reg);

            }
        } catch (Exception e) {
            Logger.getLogger(StudentForCompetition_controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void showRegistrationDetail(RegistrationDTO reg) {
        txtAge.setText(String.valueOf(reg.getAge()));
        txtStudentID.setText(String.valueOf(reg.getSID()));
        txtStudentName.setText(reg.getStudentName());
        cboxResult.requestFocus();
        cboxResult.show();
    }

    @FXML
    void btFinish_onAction(ActionEvent event) {

        ObservableList<ParticipationDTO> allParticipation = tblStudentList.getItems();
        ArrayList<ParticipationDTO> newParticipation = new ArrayList<>();

        for (ParticipationDTO oneParticipation : allParticipation) {
            if (oneParticipation.isNewOne())
                newParticipation.add(oneParticipation);
        }
        try {
            if (participationBOImpl.addAllParticipation(FXCollections.observableArrayList(newParticipation))) {
                OptionPane.showDoneAtSide("All participation are successfully processed");
                screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acStudentForCompetition, this);
            } else {
                OptionPane.showWarning("Something's wrong we can't do your request");
            }
        } catch (Exception e) {
            Logger.getLogger(StudentForCompetition_controller.class.getName()).log(Level.SEVERE, null, e);
            OptionPane.showErrorAtSide("Something's wrong we can't do your request \n Error code  \n" + e.getMessage());
        }

    }

    @FXML
    void btRemove_tblStudentList_onAction(ActionEvent event) {
        ParticipationDTO selectedItem = tblStudentList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            OptionPane.showErrorAtSide("Please select some participation");
        } else if (selectedItem.isNewOne()) {
            Common.removeItemFromTable(tblStudentList);

            EventListDTO selectedEvent = tblEventInCompetition.getSelectionModel().getSelectedItem();

            filteredRegistration = filterRegistration(selectedEvent);
            autoCompleteStudentName.changeSuggestion(filteredRegistration);

        } else {
            OptionPane.showErrorAtSide("This participation is already added. You cannot remove it.");
        }
    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        if (Common.isInteger(txtStudentID.getText())) {
            try {
                showRegistrationDetail(autoCompleteStudentName.searchByID(txtStudentID.getText()));
            } catch (NullPointerException e) {
                if(autoCompleteStudentName.isResultSetEmpty())
                    OptionPane.showErrorAtSide("Please select competition.");
                else
                    OptionPane.showErrorAtSide("Please insert the corresponding student ID for the event that you have selected in event list.");
            }
        } else {
            OptionPane.showErrorAtSide("Please enter a valid student ID.");
            txtStudentName.clear();
            txtStudentID.selectAll();
            txtAge.clear();
        }
    }
    @FXML
    void btViewStudentList_onAction(ActionEvent event) {

    }


    @FXML
    void cboxResult_onAction(ActionEvent event) {
    }

    @FXML
    void txtAgeGroup_onAction(ActionEvent event) {
    }

    @FXML
    void txtEvent_onAction(ActionEvent event) {
    }

    @FXML
    void txtGender_onAction(ActionEvent event) {
    }

    @FXML
    void txtPerformance_onAction(ActionEvent event) {
        btAddStudent.fire();
    }

    @FXML
    private void txtAge_onAction(ActionEvent actionEvent) {
    }

}
