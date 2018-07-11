package lk.ijse.mountCalvary.controller.competition;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.CompetitionBO;
import lk.ijse.mountCalvary.business.custom.TeacherBO;
import lk.ijse.mountCalvary.controller.tool.Common;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.OptionPane;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;
import lk.ijse.mountCalvary.model.CompetitionDTO;
import lk.ijse.mountCalvary.model.TeacherDTO;
import lk.ijse.mountCalvary.model.TeacherInChargeListDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewCompetition_controller implements Initializable {

    TeacherBO teacherBOImpl;
    @FXML
    private VBox acNewCompetition;
    @FXML
    private JFXButton btNext_newCompetition;
    @FXML
    private JFXTextField txtCompetitionName;
    @FXML
    private JFXTextField txtLocation;
    @FXML
    private JFXTextArea txtaDesc;
    @FXML
    private JFXComboBox<TeacherDTO> txtTeacherInCharge;
    @FXML
    private JFXButton btAdd_teacherInCharge;
    @FXML
    private TableView<TeacherDTO> tblTeacherInChargeList;
    @FXML
    private TableColumn<TeacherDTO, String> colTeacherInCharge;
    @FXML
    private JFXButton btRemove_tblTeacherInChargeList;
    @FXML
    private JFXDatePicker dtDate;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXComboBox cboxTeacherInCharge;
    private CompetitionBO competitionBOImpl;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);

        colTeacherInCharge.setCellValueFactory(new PropertyValueFactory<>("tName"));
        teacherBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);
        competitionBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.COMPETITION);
        try {
            loadTeacher();
        } catch (Exception e) {
            Logger.getLogger(NewCompetition_controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void loadTeacher() throws Exception {
        cboxTeacherInCharge.getItems().setAll(teacherBOImpl.getAllTeacher());
    }

    @FXML
    void btAdd_teacherInCharge_onAction(ActionEvent event) {
        TeacherDTO selectTeacher = (TeacherDTO) cboxTeacherInCharge.getSelectionModel().getSelectedItem();
        if (selectTeacher == null) {
            OptionPane.showErrorAtSide("Please select a teacher from the list");
        } else {
            tblTeacherInChargeList.getItems().add(selectTeacher);
            cboxTeacherInCharge.getItems().remove(selectTeacher);
        }
    }

    @FXML
    void btNext_newCompetition_onAction(ActionEvent event) {
        String compName = txtCompetitionName.getText().trim();
        String location = txtLocation.getText().trim();
        Date date = Common.localDateToDate(dtDate.getValue());
        String desc = txtaDesc.getText();
        ObservableList<TeacherDTO> teacherList = tblTeacherInChargeList.getItems();
        ArrayList<TeacherInChargeListDTO> teacherInChargeList = new ArrayList<>();
        for (TeacherDTO oneTeacher : teacherList) {
            teacherInChargeList.add(new TeacherInChargeListDTO(oneTeacher));
        }
        CompetitionDTO competitionDTO = new CompetitionDTO(compName, location, date, desc, FXCollections.observableArrayList(teacherInChargeList));
        if (compName.length() < 2) {
            OptionPane.showErrorAtSide("Please enter the Competition name");
        } else if (location.length() < 2) {
            OptionPane.showErrorAtSide("Please enter the location of the competition where it is held.");
        } else if (teacherList.size() == 0) {
            OptionPane.showErrorAtSide("Please add teacher for the competition");
        } else if (date == null) {
            OptionPane.showErrorAtSide("Please select the date that the competition was held.");
        } else if (txtaDesc.getText().length() > 700) {
            OptionPane.showErrorAtSide("Competition description can contain only 700 characters. You have entered " + txtaDesc.getText().length() + " characters (with spaces).");
            try {
                txtaDesc.selectRange(700, txtaDesc.getText().length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (desc.length() < 1) {
            boolean ask = OptionPane.askWarning("Are you sure want to continue without adding description for the competition?");
            if (!ask) {
                txtaDesc.requestFocus();
            } else {
                addCompetition(competitionDTO);
            }
        } else if (OptionPane.askQuestion("Do you want to create this competition?")) {
            addCompetition(competitionDTO);
        }
        //
//        try {
//            Parent root = screenLoader.loadOnCenterOfBorderPane("/sample/test/EventForCompetition.fxml", this.acNewCompetition, this);
//            System.out.println("Pass ____");
//
//            Label lblData = (Label) root.lookup("#lblHead");
//
//        //    FXMLLoader fxmlLoader = new FXMLLoader();
//        //    Pane p = fxmlLoader.load(getClass().getResource("/lk/ijse/mountCalvary/z_junk/view/competition/EventForCompetition.fxml").openStream());
        //    EventForCompetition_controller fooController = (EventForCompetition_controller) fxmlLoader.getController();
//
//        //    fooController.setLblHead("Pass");
//
//            if (lblData!=null) lblData.setText("Pass");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void addCompetition(CompetitionDTO competitionDTO) {
        try {
            if (competitionBOImpl.addCompetitionWithTeacherInCharge(competitionDTO)) {
                boolean next = OptionPane.askQuestion("Competition has successfully created. Do you want add Event for the competition? You can add them later.");
                if (next) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Pane p = fxmlLoader.load(getClass().getResource("/lk/ijse/mountCalvary/view/competition/EventForCompetition.fxml").openStream());
                        EventForCompetition_controller fooController = fxmlLoader.getController();
                        this.acNewCompetition.getChildren().setAll(p);
                        fooController.setSelectedItem(0);
                    } catch (Exception e) {
                        Logger.getLogger(NewCompetition_controller.class.getName()).log(Level.SEVERE, null, e);
                        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/competition/StudentForCompetition.fxml", this.acNewCompetition, this);

                    }
                } else {
                    screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/CompetitionMenu.fxml", this.acNewCompetition, this);
                }
            } else {
                OptionPane.showErrorAtSide("Something's wrong we can't do your request now");
            }
        } catch (Exception e) {
            Logger.getLogger(NewCompetition_controller.class.getName()).log(Level.SEVERE, null, e);
            OptionPane.showErrorAtSide("Something's wrong we can't do your request now");

        }
    }

    @FXML
    void btRemove_tblTeacherInChargeList_onAction(ActionEvent event) {
        TeacherDTO selectTeacher = (TeacherDTO) Common.removeItemFromTable(tblTeacherInChargeList);
        if (selectTeacher == null) {
            OptionPane.showErrorAtSide("Please select the teacher to remove");
        } else {
            cboxTeacherInCharge.getItems().add(selectTeacher);
        }
    }

    @FXML
    void txtCompetitionName_onAction(ActionEvent event) {
        txtLocation.requestFocus();
    }

    @FXML
    void txtLocation_onAction(ActionEvent event) {
        dtDate.requestFocus();
    }

    @FXML
    void txtaDesc_onAction(MouseEvent event) {

    }

    @FXML
    private void dtDate_onAction(ActionEvent actionEvent) {
        txtaDesc.requestFocus();
    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        boolean answer = OptionPane.askWarning("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewCompetition, this);
        }
    }

    @FXML
    private void cboxTeacherInCharge_onAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btAddAll_onAction(ActionEvent actionEvent) {
        ObservableList<TeacherDTO> allTeacher = cboxTeacherInCharge.getItems();
        tblTeacherInChargeList.getItems().addAll(allTeacher);
        cboxTeacherInCharge.getItems().removeAll(allTeacher);
    }
}
