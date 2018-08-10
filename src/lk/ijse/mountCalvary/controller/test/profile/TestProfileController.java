package lk.ijse.mountCalvary.controller.test.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.TermBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.model.TermDTO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/25/2018
 * Time: 3:43 PM
 */
public class TestProfileController extends SuperController implements Initializable {

    @FXML
    private BorderPane bpTestProfile;
    @FXML
    private JFXComboBox<TermDTO> cboxTerm;
    @FXML
    private JFXButton btSearch;
    @FXML
    private VBox physicalEvaluationTest;
    @FXML
    private PhysicalEvaluationTestController physicalEvaluationTestController;

    private TermBO termBOImpl;
    private TermDTO selectTerm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(bpTestProfile);

        termBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TERM);
        try {
            loadAllTerm();
        } catch (Exception e) {
            callLogger(e);
        }
        physicalEvaluationTestController.init(this);
    }

    private void loadAllTerm() throws Exception {
        cboxTerm.getItems().setAll(termBOImpl.getAllTerms());

    }

    @FXML
    private void btSearch_onAction(ActionEvent actionEvent) {
        selectTerm = cboxTerm.getSelectionModel().getSelectedItem();
        showDataOnTabs();
    }

    @FXML
    private void cboxTerm_onAction(ActionEvent actionEvent) {
        btSearch.fire();
    }

    private void showDataOnTabs() {
        physicalEvaluationTestController.insertTerm(selectTerm);
    }
}
