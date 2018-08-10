package lk.ijse.mountCalvary.controller.competition.profile;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.CompContributionBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.CompContributionDTO;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:50 PM
 */
public final class CompetitionContributionViewerController extends SuperController implements Initializable {

    @FXML
    private VBox acCompContribution;

    @FXML
    private JFXButton btPrint;

    @FXML
    private TableView<CompContributionDTO> tblCompContribution;

    @FXML
    private TableColumn<CompContributionDTO, Integer> colStudentID;

    @FXML
    private TableColumn<CompContributionDTO, String> colStudentName;

    @FXML
    private TableColumn<CompContributionDTO, String> colContribution;

    private CompetitionProfileController competitionProfileController;
    private CompetitionDTO selectCompetition;
    private CompContributionBO compContributionBOImpl;
    private JasperReport competitionContribution;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acCompContribution);

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colContribution.setCellValueFactory(new PropertyValueFactory<>("contribution"));

        compContributionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMP_CONTRIBUTION);
    }

    @FXML
    private void btPrint_onAction(ActionEvent event) {
        if (selectCompetition != null) {
            try {
                if (competitionContribution == null) {
                    InputStream competitionContribution = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/competition/CompetitionContribution.jrxml");
                    this.competitionContribution = JasperCompileManager.compileReport(competitionContribution);
                }
                HashMap map = new HashMap();
                map.put("competitionName", selectCompetition.getComName());
                map.put("contributions", new JRBeanCollectionDataSource(tblCompContribution.getItems()));

                JasperPrint jasperPrint = JasperFillManager.fillReport(competitionContribution, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Student contribution for competition");

            } catch (Exception e) {
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a competition to print.");
        }
    }

    public void insertCompetition(CompetitionDTO competitionDTO) {
        this.selectCompetition = competitionDTO;
        try {
            ObservableList<CompContributionDTO> contributionForThisCompetition =
                    compContributionBOImpl.getContributionForThisCompetition(selectCompetition.getCID());
            tblCompContribution.getItems().setAll(contributionForThisCompetition);
            Common.clearSortOrder(tblCompContribution);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    protected void init(CompetitionProfileController competitionProfileController) {
        this.competitionProfileController = competitionProfileController;


    }
}
