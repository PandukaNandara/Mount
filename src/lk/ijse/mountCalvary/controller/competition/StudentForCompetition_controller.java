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
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.*;
import lk.ijse.mountCalvary.controller.AutoComplete;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentForCompetition_controller implements Initializable {
    ObservableList<RegistrationDTO> filteredRegistration;
    @FXML
    private AnchorPane acStudentForCompetition;
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
    private JFXTextField txtPerfomence;
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
    private ParticipationBO participationBOImpl;
    private EventListBO eventListBOImpl;

    private ArrayList<ActivityDTO> activityDTOS;
    private ObservableList<AgeGroupDTO> ageGroupDTOS;
    private AutoComplete<RegistrationDTO> autoCompleteStudentName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colActivity_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colAgeGroup_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("ageGroupDTO"));
        colEvent_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colEvent_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("genderType"));
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
//            loadCompetitionWithParticipation();
            loadActivityWithStudent();
            ageGroupDTOS = ageGroupBOImpl.getAgeGroups();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        txtStudentName.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (tblEventInCompetition.getSelectionModel().isEmpty()) {
//                System.out.println("Yes");
//            } else {
//                System.out.println("No");
//            }
//        });
        autoCompleteStudentName = new AutoComplete<>(txtStudentName);

    }

    private void loadCompetition() throws Exception {
        cboxCompetition.getItems().setAll(competitionBOImpl.getAllCompetition());
    }

    private void loadActivityWithStudent() throws Exception {

        activityDTOS = activityBOImpl.getActivityWithStudent();

    }

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
            e.printStackTrace();
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

        String performance = txtPerfomence.getText();
        String result = cboxResult.getValue();
        String studentName = txtStudentName.getText();
        if (studentName.length() < 1) {
            Common.showError("Please select the student");
        } else if (result.length() == 0) {
            Common.showError("Please enter the result of the student");
        } else {
            EventListDTO selectedItem = tblEventInCompetition.getSelectionModel().getSelectedItem();
            String eventName = selectedItem.getEventName() + " - " + selectedItem.getActivityName() + " - " + selectedItem.getGenderType();

            RegistrationDTO registrationDTO = Common.searchRegistration(studentName, filteredRegistration);
            ParticipationDTO newParticipation = new ParticipationDTO(selectedItem.getELID(), registrationDTO, result, performance, eventName);
            newParticipation.setNewOne(true);

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
            e.printStackTrace();

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
                int age = LocalDate.now().getYear() - Common.DateToLocalDate(oneRegi.getDOB()).getYear();
                if (oneRegi.isGender() == eventList.isGender() &&
                        age > ageGroup.getMin() && age < ageGroup.getMax()){
                        oneRegi.setAge(age);
                        filter.add(oneRegi);
                    }
            }
            return FXCollections.observableArrayList(filter);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void clearAll() {
        txtStudentID.setText("");
        txtStudentName.setText("");
        txtAgeGroup.setText("");
        cboxResult.setValue("");
        txtPerfomence.setText("");
    }

    @FXML
    void btCancel(ActionEvent event) {
        boolean answer = Common.askQuestion("Do you want to cancel?");
        if (answer) {
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acStudentForCompetition, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void txtStudentName_onAction(ActionEvent event) {
        RegistrationDTO reg = Common.searchRegistration(txtStudentName.getText(), filteredRegistration);
        try {
            if (reg == null) {

                Common.showError("Please insert the corresponding student or select a event of the competition.");

                txtAge.setText("");
                txtStudentID.setText("");

            } else {
                txtAge.setText(reg.getAge() + "");
                txtStudentID.setText(reg.getSID() + "");
                cboxResult.requestFocus();
                cboxResult.show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
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
                Common.showMessage("All participation are successfully processed");
                try {
                    lk.ijse.mountCalvary.controller.basic.ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acStudentForCompetition, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Common.showWarning("Something's wrong we can't do your request");
            }
        } catch (Exception e) {
            Common.showError("Something's wrong we can't do your request \n Error code  \n" + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void btRemove_tblStudentList_onAction(ActionEvent event) {
        ParticipationDTO selectedItem = tblStudentList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Common.showError("Please select some participation");
        } else if (selectedItem.isNewOne()) {
            Common.removeItemFromTable(tblStudentList);

            EventListDTO selectedEvent = tblEventInCompetition.getSelectionModel().getSelectedItem();


            filteredRegistration = filterRegistration(selectedEvent);
            autoCompleteStudentName.changeSuggestion(filteredRegistration);

        } else {
            Common.showError("This participation is already added. You cannot remove it.");
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
    void txtStudent_onAction(ActionEvent event) {
    }

    @FXML
    private void txtAge_onAction(ActionEvent actionEvent) {
    }

}
