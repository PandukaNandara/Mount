package lk.ijse.mountCalvary.controller.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.TermBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.TermDTO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 1:17 AM
 */

public final class NewTerm_controller extends SuperController implements Initializable {

    @FXML
    private VBox acNewTerm;
    @FXML
    private JFXComboBox<Year> cboxYear;
    @FXML
    private JFXComboBox<TermDTO> cboxTerm;
    @FXML
    private JFXTextField txtTernName;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btSubmit;

    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    private TermBO termBOImpl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acNewTerm);

        termBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TERM);
        cboxTerm.getItems().setAll(Terms.getInstance().getTerms());
        cboxYear.getItems().setAll(Year.getAllYear());

        cboxYear.focusedProperty().addListener((observable, oldValue, newValue) -> makeTermName());
    }

    private void makeTermName() {
        try {
            String yearName = cboxYear.getSelectionModel().getSelectedItem().toString();
            String termName = cboxTerm.getSelectionModel().getSelectedItem().getTermName();
            txtTernName.setText(String.format("%s - %s", yearName, termName));
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    void cboxTerm_onAction(ActionEvent event) {
        makeTermName();
    }

    @FXML
    void cboxYear_onAction(ActionEvent event) {
        makeTermName();
    }

    @FXML
    void txtTermName_onAction(ActionEvent event) {
    }

    @FXML
    private void btSubmit_onAction(ActionEvent actionEvent) {
        if (checkTermName()) {
            String termName = txtTernName.getText().trim();
            Year selectedYear = cboxYear.getSelectionModel().getSelectedItem();
            TermDTO selectedTerm = cboxTerm.getSelectionModel().getSelectedItem();
            if (selectedYear != null)
                if (selectedTerm != null)
                    if (OptionPane.askQuestion("Do you want to create new term?"))
                        try {
                            if (termBOImpl.saveTerm(new TermDTO(termName, selectedYear.getYear(), selectedTerm.getTermNo()))) {
                                OptionPane.showDoneAtSide("New term has successfully created.");
                                screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/TestMenu.fxml", this.acNewTerm, this);
                            } else
                                OptionPane.showDoneAtSide("Something's wrong we can't do your request.");
                        } catch (Exception e) {
                            OptionPane.showDoneAtSide("Something's wrong we can't do your request.");
                            callLogger(e);
                        }
                    else OptionPane.showErrorAtSide("Please select a term.");
                else OptionPane.showErrorAtSide("Please select a year.");
        } else {
            OptionPane.showErrorAtSide("Please put a name for term name.");
        }
    }

    private boolean checkTermName() {
        return txtTernName.getText().length() > 2;
    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        if (OptionPane.askQuestion("Do you want to cancel?"))
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acNewTerm, this);
    }

}
