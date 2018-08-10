package lk.ijse.mountCalvary.controller.competition.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.CompetitionBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.OptionPane;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;
import lk.ijse.mountCalvary.model.CompetitionDTO;

import java.net.URL;
import java.util.ResourceBundle;

public final class CompetitionProfileController extends SuperController implements Initializable {

    @FXML
    private BorderPane bpCompetitionProfile;
    @FXML
    private JFXButton btSearch;
    @FXML
    private JFXComboBox<CompetitionDTO> cboxCompetitionName;
    @FXML
    private VBox competitionDetail;
    @FXML
    private VBox eventAndActivity;
    @FXML
    private VBox competitionContributionViewer;
    @FXML
    private CompetitionDetailController competitionDetailController;
    @FXML
    private VBox acCompetitionRoot;
    @FXML
    private EventAndActivityController eventAndActivityController;
    @FXML
    private CompetitionContributionViewerController competitionContributionViewerController;

    private CompetitionBO competitionBOImpl;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(bpCompetitionProfile);
        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);

        competitionDetailController.init(this);
        eventAndActivityController.init(this);
        competitionContributionViewerController.init(this);
        try {
            loadCompetition();
        } catch (Exception e) {
            callLogger(e);
        }
    }

    private void loadCompetition() throws Exception {
        ObservableList<CompetitionDTO> allCompetition = competitionBOImpl.getAllCompetition();
        cboxCompetitionName.getItems().setAll(allCompetition);
    }

    @FXML
    void btSearch_onAction(ActionEvent event) {
        CompetitionDTO competitionDTO = cboxCompetitionName.getSelectionModel().getSelectedItem();
        if (competitionDTO != null) {
            showOnTabs(competitionDTO);
        } else {
            OptionPane.showErrorAtSide("Please select a competition.");
        }
    }

    private void showOnTabs(CompetitionDTO competitionDTO) {
        competitionDetailController.insertCompetition(competitionDTO);
        eventAndActivityController.insertCompetition(competitionDTO);
        competitionContributionViewerController.insertCompetition(competitionDTO);
    }

    @FXML
    void cboxCompetitionName_onAction(ActionEvent event) {
        btSearch.fire();
    }

    @FXML
    void btCompetitionDetails_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/profile/CompetitionDetail.fxml", this.acCompetitionRoot, this);
    }

    @FXML
    void btEventDetail_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/profile/EventAndActivity.fxml", this.acCompetitionRoot, this);
    }

}
