package lk.ijse.mountCalvary.controller.competition.profile;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.mountCalvary.business.custom.EventListBO;
import lk.ijse.mountCalvary.business.custom.ParticipationBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.Reporter;
import lk.ijse.mountCalvary.model.AgeGroupDTO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.EventListDTO;
import lk.ijse.mountCalvary.model.ParticipationDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EventAndActivityController implements Initializable {

    @FXML
    private AnchorPane acEventForActivity;
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
    private TableView<ParticipationDTO> tblStudentList;
    @FXML
    private TableColumn<ParticipationDTO, String> colStudent_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colResult_tblStudentLIst;
    @FXML
    private TableColumn<ParticipationDTO, String> colPerformance_tblStudentList;
    private static JasperReport competitionDetailReport;

    private CompetitionProfileController competitionProfileController;

    private ParticipationBO participationBOImpl;
    private EventListBO eventListBOImpl;
    @FXML
    private JFXButton btPrint;
    private CompetitionDTO selectedCompetition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colActivity_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colAgeGroup_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("ageGroupDTO"));
        colEvent_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        colPerformance_tblStudentList.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colResult_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("result"));
        colStudent_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        eventListBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT_LIST);
        participationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PARTICIPATION);

    }

    public void init(CompetitionProfileController competitionProfileController) {
        this.competitionProfileController = competitionProfileController;
    }

    public void insertCompetition(CompetitionDTO competitionDTO) {

        try {
            selectedCompetition = competitionDTO;
            int cid = competitionDTO.getCID();
            ObservableList<EventListDTO> eventListForThisCompetition = eventListBOImpl.getEventListForThisCompetition(cid);
            tblEventInCompetition.getItems().setAll(eventListForThisCompetition);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void tblEventInCompetition_mouseClick(MouseEvent event) {
        try {
            EventListDTO selectedEventList = tblEventInCompetition.getSelectionModel().getSelectedItem();
            ObservableList<ParticipationDTO> participationForThisEventList = participationBOImpl.getParticipationForThisEventList(selectedEventList.getELID());
            tblStudentList.getItems().setAll(participationForThisEventList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tblStudentList_mouseClick(MouseEvent event) {
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        if (selectedCompetition != null) {
            EventListDTO selectedEventList = tblEventInCompetition.getSelectionModel().getSelectedItem();
            if (selectedEventList != null) {
                try {
                    String compName = selectedCompetition.getComName();
                    String activityName = selectedEventList.getActivityName();
                    String eventName = selectedEventList.getEventName();
                    String ageGroup = selectedEventList.getAgeGroupDTO().toString();
                    String gender_string = selectedEventList.getGenderType();
                    if (tblStudentList.getItems().size() == 0 &&
                            (!Common.askWarning("There's no participation for this event. Do you want to continue?")))
                        return;
                    JRBeanCollectionDataSource participations = new JRBeanCollectionDataSource(tblStudentList.getItems());
                    if (competitionDetailReport == null) {
                        InputStream eventAndActivityFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/competition/EventAndActivityReport.jrxml");
                        competitionDetailReport = JasperCompileManager.compileReport(eventAndActivityFile);
                    }
                    HashMap competitionMap = new HashMap();

                    competitionMap.put("Participation", participations);
                    competitionMap.put("ActivityName", activityName);
                    competitionMap.put("EventName", eventName);
                    competitionMap.put("AgeGroup", ageGroup);
                    competitionMap.put("CompetitionName", compName);
                    competitionMap.put("Gender", gender_string);

                    JasperPrint competitionPrint = JasperFillManager.fillReport(competitionDetailReport, competitionMap, new JREmptyDataSource());
                    //Reporter.showReport(competitionPrint, false);
                    Reporter.showReport(competitionPrint, "Event and participation");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Common.showError("Please select an event from the Event list.");
            }
        } else {
            Common.showError("Please select a competition to print.");
        }
    }
}
