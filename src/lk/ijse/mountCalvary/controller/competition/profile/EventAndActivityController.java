package lk.ijse.mountCalvary.controller.competition.profile;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.EventListBO;
import lk.ijse.mountCalvary.business.custom.ParticipationBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.report.CertificateMakerController;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.model.AgeGroupDTO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.EventListDTO;
import lk.ijse.mountCalvary.model.ParticipationDTO;
import lk.ijse.mountCalvary.tool.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FilenameUtils;
import org.controlsfx.dialog.Dialogs;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public final class EventAndActivityController extends SuperController implements Initializable {

    private static JasperReport competitionDetailReport;
    private final String[] keys = new String[]{
            "Student_ID",
            "Student_Name",
            "Student_House",
            "Student_Class",
            "Result",
            "Age_group",
            "Performance",
            "Activity_Name",
            "Event_Name",
            "Event_Gender",
            "Competition_Name",
            "Competition_Location",
            "Competition_Date",
            "Year"
    };
    @FXML
    private VBox acEventForActivity;
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
    @FXML
    private TableColumn<ParticipationDTO, Integer> colStudentID_tblStudentLIst;
    @FXML
    private JFXButton btPrint;
    @FXML
    private JFXButton btCertificate;
    private CompetitionProfileController competitionProfileController;
    private ParticipationBO participationBOImpl;
    private EventListBO eventListBOImpl;
    private CompetitionDTO selectedCompetition;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    private EventListDTO selectedItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acEventForActivity);

        colActivity_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        colAgeGroup_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("ageGroupDTO"));
        colEvent_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colGender_tblEventInCompetition.setCellValueFactory(new PropertyValueFactory<>("genderType"));

        colPerformance_tblStudentList.setCellValueFactory(new PropertyValueFactory<>("performance"));
        colResult_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("result"));
        colStudent_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudentID_tblStudentLIst.setCellValueFactory(new PropertyValueFactory<>("SID"));

        eventListBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.EVENT_LIST);
        participationBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.PARTICIPATION);

    }

    protected void init(CompetitionProfileController competitionProfileController) {
        this.competitionProfileController = competitionProfileController;
    }

    public void insertCompetition(CompetitionDTO competitionDTO) {

        try {
            selectedCompetition = competitionDTO;
            int cid = competitionDTO.getCID();
            ObservableList<EventListDTO> eventListForThisCompetition = eventListBOImpl.getEventListForThisCompetition(cid);
            tblEventInCompetition.getItems().setAll(eventListForThisCompetition);
            Common.clearSortOrder(tblEventInCompetition, tblStudentList);
        } catch (Exception e) {
            callLogger(e);
        }

    }

    @FXML
    private void tblEventInCompetition_mouseClick(MouseEvent event) {
        try {
            EventListDTO selectedEventList = tblEventInCompetition.getSelectionModel().getSelectedItem();
            ObservableList<ParticipationDTO> participationForThisEventList = participationBOImpl
                    .getParticipationForThisEventList(selectedEventList.getELID());
            tblStudentList.getItems().setAll(participationForThisEventList);

        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void tblStudentList_mouseClick(MouseEvent event) {
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
//        Desktop.getDesktop().open();
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
                            (!OptionPane.askWarning("There's no participation for this event. Do you want to continue?")))
                        return;
                    JRBeanCollectionDataSource participations = new JRBeanCollectionDataSource(tblStudentList.getItems());
                    if (competitionDetailReport == null) {
                        InputStream eventAndActivityFile = getClass()
                                .getResourceAsStream("/lk/ijse/mountCalvary/report/competition/EventAndActivityReport.jrxml");
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
                    callLogger(e);
                }
            } else {
                OptionPane.showErrorAtSide("Please select an event from the Event list.");
            }
        } else {
            OptionPane.showErrorAtSide("Please select a competition to print.");
        }
    }

    private void openTemplateFile() throws Exception {
        File file = new File("resources/CertificateForEditing.jrxml");
        if(file.exists()){
            try {
                String name = OptionPane.getTextInput(
                        "Name your template.");

                TemporaryFile temporaryFile = new TemporaryFile(
                        file,
                        ".jrxml");
                temporaryFile.getTempFile().renameTo(new File(name));
                Desktop.getDesktop().open(
                        temporaryFile.getTempFile()
                );
            }catch (NoSuchElementException ignored){
                System.err.println(ignored.getMessage());
            }
        }else
            OptionPane.showErrorAtSide("Cannot find template file.");

    }

    private void importAndGenerateJasperFile() throws Exception {
        ///
        ButtonType open_internal_certificate_generator = new ButtonType("Open internal certificate generator");
        ButtonType open_template_using_open_jaspersoft_studio = new ButtonType("Create template and open using Jaspersoft studio");
        ButtonType open_previous_template = new ButtonType("Open previous template");

        ButtonType buttonType = OptionPane.askQuestion("Chose how to interact with template",
                open_internal_certificate_generator,
                open_previous_template,
                open_template_using_open_jaspersoft_studio);
        if (buttonType.equals(open_internal_certificate_generator)){
            useInternalParameterPlacer();
        }else if(buttonType.equals(open_previous_template)){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose your template file.");
            fileChooser.getExtensionFilters().setAll(
                    new FileChooser.ExtensionFilter("JasperReport file (*.jrxml)", "*.jrxml"),
                    new FileChooser.ExtensionFilter("JasperReport compiled file (*.jasper)", "*.jasper")
            );
            File openedFile = fileChooser.showOpenDialog(acEventForActivity.getScene().getWindow());
            createCertificate(openedFile);
        }else if(buttonType.equals(open_template_using_open_jaspersoft_studio)){
            openTemplateFile();
        }

    }

    private void createCertificate(File openedFile) throws Exception {
        if (openedFile != null) {
            String extension = FilenameUtils.getExtension(openedFile.getName());

            InputStream reportFile = new FileInputStream(openedFile);

            JasperPrint competitionPrint;

            HashMap map = new HashMap();

            map.put("ELID", selectedItem.getELID());

            if (extension.equals("jrxml")) {
                JasperReport certificate = JasperCompileManager.compileReport(reportFile);
                competitionPrint = JasperFillManager.fillReport(
                        certificate,
                        new HashMap<>(),
                        DBConnection.getInstance().getConnection()
                );
            } else {
                competitionPrint = JasperFillManager.fillReport(reportFile, map,
                        DBConnection.getInstance().getConnection());
            }
            Reporter.showReport(competitionPrint, "Certificate");
        }
    }

    @FXML
    private void btCertificate_onAction(ActionEvent actionEvent) {
        selectedItem = tblEventInCompetition.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            OptionPane.showErrorAtSide("Please select an event.");
            return;
        } else {
            try {
                importAndGenerateJasperFile();
            } catch (Exception e) {
                callLogger(e);
            }
        }

//        try {
////            openTemplateFile();
////            OptionPane.showMessageAtSide("Template file has been generated!");
////        } catch (Exception e) {
////            OptionPane.showErrorAtSide("Somethings wrong we can't do your request.");
////            callLogger(e);
////        }
    }

    private void useInternalParameterPlacer() {
        CertificateMakerController certificateMakerController = screenLoader.loadNewWindowAndWait(
                "/lk/ijse/mountCalvary/view/report/CertificateMaker.fxml",
                "Certificate Designer");
        Map<String, Text> parameterMap = certificateMakerController.getParameterMap();
        if (parameterMap.size() == 0) {
            OptionPane.showWarningAtSide("You didn't put any parameter into the certificate.");
            return;
        }

        System.out.println(parameterMap);
        HashMap map = new HashMap();

        try {
            TemporaryFile fileCreator = new TemporaryFile(
                    "/lk/ijse/mountCalvary/report/competition/certificate/Landscape_certificate.jrxml",
                    "Certificate",
                    ".jrxml");
            for (String key : keys) {
                Text text = parameterMap.get(String.format("{%s}", key));
                double x;
                double y;
                boolean shouldSee;
                if (text == null) {
                    x = 0;
                    y = 0;
                    shouldSee = false;
                } else {
                    x = text.getX();
                    y = text.getY();
                    shouldSee = true;
                }

                final int X = (int) Math.round(x);
                final int Y = (int) Math.round(y);

                fileCreator.putParameter(String.format("%s_X", key), String.valueOf(X));
                fileCreator.putParameter(String.format("%s_Y", key), String.valueOf(Y));
                map.put(String.format("see_%s", key), shouldSee);
            }

            fileCreator.readFile(fileCreator.getTempFile());

            map.put("ELID", selectedItem.getELID());
            InputStream certificateFile = new FileInputStream(fileCreator.getTempFile());

            JasperReport certificate = JasperCompileManager.compileReport(certificateFile);
            JasperPrint competitionPrint = JasperFillManager.fillReport(certificate, map, DBConnection.getInstance().getConnection());

            Reporter.showReport(competitionPrint, "Certificate");
            System.out.println(fileCreator.getTempFile().getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
