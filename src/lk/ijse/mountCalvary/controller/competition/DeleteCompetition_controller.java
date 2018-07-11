package lk.ijse.mountCalvary.controller.competition;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.mountCalvary.business.custom.CompetitionBO;
import lk.ijse.mountCalvary.business.custom.EventListBO;
import lk.ijse.mountCalvary.business.custom.ParticipationBO;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.OptionPane;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;
import lk.ijse.mountCalvary.model.AgeGroupDTO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.EventListDTO;
import lk.ijse.mountCalvary.model.ParticipationDTO;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCompetition_controller implements Initializable {
    @FXML
    private VBox acDeleteCompetition;
    @FXML
    private JFXButton btDelete;
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
    private JFXComboBox<CompetitionDTO> cboxCompetition;
    @FXML
    private TableView<ParticipationDTO> tblStudentList;
    @FXML
    private TableColumn<ParticipationDTO, String> colStudent_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colResult_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colPerformance_tblStudentList;
    @FXML
    private JFXButton btCancel;

    private ParticipationBO participationBOImpl;
    private EventListBO eventListBOImpl;
    private CompetitionBO competitionBOImpl;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);

        colActivity_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colAgeGroup_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("ageGroupDTO"));
        colEvent_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("genderType"));
        colPerformance_tblStudentList.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colResult_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("result"));
        colStudent_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);
        eventListBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT_LIST);
        participationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PARTICIPATION);

        try {
            loadCompetition();
        } catch (Exception e) {
            Logger.getLogger(DeleteCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void loadCompetition() throws Exception {
        ObservableList<CompetitionDTO> allCompetition = competitionBOImpl.getAllCompetition();
        cboxCompetition.getItems().setAll(allCompetition);
    }

    @FXML
    void btCancel_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acDeleteCompetition, this);
    }

    @FXML
    void btDelete_onAction(ActionEvent event) {
        try {
            CompetitionDTO selectedCompetition = cboxCompetition.getSelectionModel().getSelectedItem();

            if (selectedCompetition == null) {
                OptionPane.showErrorAtSide("Please select a competition.");
            } else {
                int CID = selectedCompetition.getCID();
                int eventListCount = eventListBOImpl.getEventListForThisCompetition(CID).size();
                int participationCount = participationBOImpl.getParticipationForThisCompetition(CID).size();
                String message;

                if (eventListCount > 1 && participationCount > 1) {
                    message = "There are " + eventListCount + " events and " + participationCount + " participation records for the competition.";
                } else {
                    message = "There is " + eventListCount + " event and " + participationCount + " participation records for the competition.";
                }
                boolean yes = OptionPane.askWarning(message + " Do you want to delete this competition? If you delete, all records will be deleted.");
                if (yes) {
                    if (competitionBOImpl.deleteCompetition(CID)) {
                        OptionPane.showDoneAtSide("Competition has successfully deleted");
                        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acDeleteCompetition, this);
                    } else {
                        OptionPane.showWarning("Something's wrong we can't do your request now.");
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(DeleteCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    @FXML
    void cboxCompetition_onAction(ActionEvent event) {
        try {
            CompetitionDTO selectedCompetition = cboxCompetition.getSelectionModel().getSelectedItem();
            ObservableList<EventListDTO> eventListForThisCompetition = eventListBOImpl.getEventListForThisCompetition(selectedCompetition.getCID());
            tblEventInCompetition.getItems().setAll(eventListForThisCompetition);
        } catch (Exception e) {
            Logger.getLogger(DeleteCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    private void tblEventInCompetition_onClick(MouseEvent event) {

        try {
            EventListDTO selectedEventList = tblEventInCompetition.getSelectionModel().getSelectedItem();
            ObservableList<ParticipationDTO> participationForThisEventList = participationBOImpl.getParticipationForThisEventList(selectedEventList.getELID());
            tblStudentList.getItems().setAll(participationForThisEventList);
        } catch (Exception e) {
            Logger.getLogger(DeleteCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    private void tblStudentList_onClick(MouseEvent event) {

    }


}
