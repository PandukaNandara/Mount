package lk.ijse.mountCalvary.controller.activity.profile;

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
import lk.ijse.mountCalvary.business.custom.AgeGroupBO;
import lk.ijse.mountCalvary.business.custom.EventBO;
import lk.ijse.mountCalvary.business.custom.ParticipationBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class EventAndCompetitionOfActivityController extends SuperController implements Initializable {

    private static JasperReport eventAndCompetitionReport;
    @FXML
    private VBox EventAndCompetitionOfActivity;
    @FXML
    private JFXComboBox<EventDTO> cboxEvent;
    @FXML
    private JFXComboBox<AgeGroupDTO> cboxAgeGroup;
    @FXML
    private TableView<CompetitionDTO> tblCompetitionList;
    @FXML
    private TableColumn<CompetitionDTO, String> colCompetition_tblCompetitionList;
    @FXML
    private TableColumn<CompetitionDTO, Date> colDate_tblCompetitionList;
    @FXML
    private TableView<ParticipationDTO> tblParticipation;
    @FXML
    private TableColumn<ParticipationDTO, String> colStudentName_tblParticipation;
    @FXML
    private TableColumn<ParticipationDTO, String> colResult_tblParticipation;
    @FXML
    private TableColumn<ParticipationDTO, String> colPerformance_tblParticipation;
    @FXML
    private TableColumn<ParticipationDTO, String> colAgeGroup_tblParticipation;
    private ActivityProfileController activityProfileController;

    private AgeGroupBO ageGroupBOImpl;
    private ParticipationBO participationBOImpl;
    private EventBO eventBOImpl;

    private ObservableList<ParticipationDTO> participations;
    @FXML
    private JFXButton btPrint;
    private ActivityDTO selectedActivity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(EventAndCompetitionOfActivity);

        colCompetition_tblCompetitionList.setCellValueFactory(new PropertyValueFactory<>("comName"));
        colDate_tblCompetitionList.setCellValueFactory(new PropertyValueFactory<>("date"));

        colPerformance_tblParticipation.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colResult_tblParticipation.setCellValueFactory(new PropertyValueFactory<>("result"));
        colStudentName_tblParticipation.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAgeGroup_tblParticipation.setCellValueFactory(new PropertyValueFactory<>("groupName"));

        ageGroupBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.AGE_GROUP);

        participationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PARTICIPATION);

        eventBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT);

    }

    private void loadAgeGroup() throws Exception {
        cboxAgeGroup.getItems().setAll(ageGroupBOImpl.getAgeGroups());
        cboxAgeGroup.getItems().add(0, new AgeGroupDTO(-1, "All"));
        cboxAgeGroup.getSelectionModel().select(0);
    }

    public void insertActivity(ActivityDTO activityDTO) {
        try {
            selectedActivity = activityDTO;
            int aid = activityDTO.getAID();
            ObservableList<EventDTO> eventForThisActivity = eventBOImpl.getEventForThisActivity(aid);
            cboxEvent.getItems().setAll(eventForThisActivity);
            tblCompetitionList.getItems().clear();
            tblParticipation.getItems().clear();
            Common.clearSortOrder(tblCompetitionList, tblParticipation);
            loadAgeGroup();
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    void cboxEvent_onAction(ActionEvent event) {
        try {
            try {
                int eid = cboxEvent.getSelectionModel().getSelectedItem().getEID();
                ObservableList<CompetitionDTO> competitionForThisEvent = eventBOImpl.getCompetitionForThisEvent(eid);
                tblCompetitionList.getItems().setAll(competitionForThisEvent);
                tblParticipation.getItems().removeAll(tblParticipation.getItems());
            } catch (NullPointerException ignored) {
            }
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void cboxAgeGroup_onAction(ActionEvent actionEvent) {
        try {
            filterStudentList();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            callLogger(e);
        }
    }

    private void filterStudentList() {
        AgeGroupDTO ageGroupDTO = cboxAgeGroup.getSelectionModel().getSelectedItem();
        int GID = ageGroupDTO.getGID();
        ArrayList<ParticipationDTO> filter = new ArrayList<>();
        for (ParticipationDTO oneParticipation : participations) {
            if (GID == -1 || GID == oneParticipation.getGID())
                filter.add(oneParticipation);
        }
        tblParticipation.getItems().setAll(filter);
    }

    @FXML
    private void tblCompetitionList_mouseClick(MouseEvent event) {

        try {
            int CID = tblCompetitionList.getSelectionModel().getSelectedItem().getCID();
            int EID = cboxEvent.getSelectionModel().getSelectedItem().getEID();
            participations = participationBOImpl.getParticipationForThisEventAndCompetition(EID, CID);
            filterStudentList();
            //tblParticipation.getItems().setAll(participations);
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            callLogger(e);
        }
    }

    public void init(ActivityProfileController activityProfileController) {
        this.activityProfileController = activityProfileController;
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {

        EventDTO eventDTO = cboxEvent.getSelectionModel().getSelectedItem();
        CompetitionDTO competitionDTO = tblCompetitionList.getSelectionModel().getSelectedItem();
        ObservableList<ParticipationDTO> participationDTOS = tblParticipation.getItems();

        if (selectedActivity == null) {
            OptionPane.showErrorAtSide("Please select an activity to print.");
        } else if (eventDTO == null) {
            OptionPane.showErrorAtSide("Please select an event.");
        } else if (competitionDTO == null) {
            OptionPane.showErrorAtSide("Please select a competition from the competition table.");
        } else if (participationDTOS.size() == 0) {
            boolean ask = OptionPane.askWarning("There's no participation for this event in this competition. Do you wish to continue?");
            if (ask) {
                generateReport(participationDTOS, competitionDTO, eventDTO);
            }
        } else {
            generateReport(participationDTOS, competitionDTO, eventDTO);
        }
    }

    private void generateReport(ObservableList<ParticipationDTO> participationDTOS, CompetitionDTO competitionDTO, EventDTO eventDTO) {
        try {
            if (eventAndCompetitionReport == null) {
                InputStream eventAndCompetitionFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/activity/EventAndCompetitionReport.jrxml");
                eventAndCompetitionReport = JasperCompileManager.compileReport(eventAndCompetitionFile);
            }
            JRBeanCollectionDataSource participation = new JRBeanCollectionDataSource(participationDTOS);
            HashMap eventMap = new HashMap();
            eventMap.put("ActivityName", selectedActivity.getaName());
            eventMap.put("EventName", eventDTO.getEventName());
            eventMap.put("comDate", competitionDTO.getDate().toString());
            eventMap.put("CompetitionName", competitionDTO.getComName());
            if (cboxAgeGroup.getSelectionModel().getSelectedItem().getGID() == -1)
                eventMap.put("seeAgeGroup", true);
            eventMap.put("AgeGroup", cboxAgeGroup.getSelectionModel().getSelectedItem().getGroupName());
            eventMap.put("Participation", participation);

            JasperPrint eventAndCompetitionPrint = JasperFillManager.fillReport(eventAndCompetitionReport, eventMap, new JREmptyDataSource());
            Reporter.showReport(eventAndCompetitionPrint, "Event and competition");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
