package lk.ijse.mountCalvary.controller.competition.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.CompetitionBO;
import lk.ijse.mountCalvary.business.custom.TeacherInChargeListBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.Reporter;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.TeacherInChargeListDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CompetitionDetailController implements Initializable {

    @FXML
    private AnchorPane acCompetitionDetail;

    @FXML
    private JFXTextField txtCompetitionName;

    private static JasperReport competitionDetailReport;
    @FXML
    private JFXTextField txtLocation;

    @FXML
    private TableView<TeacherInChargeListDTO> tblTeacherInCharge;

    @FXML
    private TableColumn<TeacherInChargeListDTO, String> colTeacherInCharge;
    @FXML
    private JFXTextArea txtaDesc;
    @FXML
    private JFXTextField txtDate;

    private CompetitionProfileController competitionProfileController;

    private CompetitionBO competitionBOImpl;
    private TeacherInChargeListBO teacherInChargeListBOImpl;
    @FXML
    private JFXButton btPrint;
    private CompetitionDTO selectedCompetition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTeacherInCharge.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);
        teacherInChargeListBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER_IN_CHARGE_LIST);
    }

    public void init(CompetitionProfileController competitionProfileController) {
        this.competitionProfileController = competitionProfileController;
    }

    public void insertCompetition(CompetitionDTO competitionDTO) {
        try {
            selectedCompetition = competitionDTO;
            int CID = competitionDTO.getCID();

            CompetitionDTO comp = competitionBOImpl.getCompetitionDetail(CID);

            txtCompetitionName.setText(comp.getComName());
            txtLocation.setText(comp.getLocation());
            txtaDesc.setText(comp.getDesc());
            txtDate.setText(comp.getDate().toString());
            tblTeacherInCharge.getItems().setAll(teacherInChargeListBOImpl.getTeacherInChargeListForThisCompetition(CID));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        if (selectedCompetition != null) {
            try {
                String compName = txtCompetitionName.getText();
                String date = txtDate.getText();
                String desc = txtaDesc.getText();
                String location = txtLocation.getText();
                JRBeanCollectionDataSource teacherInCharge = new JRBeanCollectionDataSource(tblTeacherInCharge.getItems());

                if (competitionDetailReport == null) {
                    InputStream competitionDetailFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/competition/CompetitionDetailReport.jrxml");
                    competitionDetailReport = JasperCompileManager.compileReport(competitionDetailFile);
                }

                HashMap competitionMap = new HashMap();

                competitionMap.put("TeacherInCharge", teacherInCharge);
                competitionMap.put("Desc", desc);
                competitionMap.put("Location", location);
                competitionMap.put("Date", date);
                competitionMap.put("CompetitionName", compName);

                JasperPrint competitionPrint = JasperFillManager.fillReport(competitionDetailReport, competitionMap, new JREmptyDataSource());
                Reporter.showReport(competitionPrint, "Competition details");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Common.showError("Please select a competition to print.");
        }

    }
}