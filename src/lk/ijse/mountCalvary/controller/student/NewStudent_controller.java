package lk.ijse.mountCalvary.controller.student;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TelNoBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static lk.ijse.mountCalvary.tool.Common.isInteger;
import static lk.ijse.mountCalvary.tool.OptionPane.showErrorAtSide;

public final class NewStudent_controller extends SuperController implements Initializable {

    @FXML
    private VBox acNewStudent;

    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private JFXRadioButton rbMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton rbFemale;

    @FXML
    private JFXDatePicker dtDOB;

    @FXML
    private JFXTextField txtMotherName;

    @FXML
    private JFXTextField txtFatherName;

    @FXML
    private JFXTextField txtStudentID;

    @FXML
    private JFXTextArea txtaDesc;

    @FXML
    private JFXButton btRemove_tblTelNo;

    @FXML
    private JFXButton btAddTelNo;

    @FXML
    private JFXTextField txtTelNo;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private TableView<RegistrationDTO> tblActivity;

    @FXML
    private TableColumn<RegistrationDTO, ActivityDTO> colActivity;

    @FXML
    private TableColumn<RegistrationDTO, Date> colJoinedDate;

    @FXML
    private JFXComboBox<String> cboxClass;

    @FXML
    private JFXComboBox<String> cboxGrade;

    @FXML
    private JFXButton btRemove_tblActivity;

    @FXML
    private JFXComboBox<ActivityDTO> cboxActivityName;

    @FXML
    private TableView<TelNoDTO> tblTelNo;

    @FXML
    private TableColumn<TelNoDTO, Integer> colTelNo_tblTelNo;

    @FXML
    private JFXDatePicker dtJoinedDate;

    @FXML
    private JFXButton btAddActivity;

    @FXML
    private JFXButton btFinish_activityForStudent;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXComboBox<House> cboxHouse;

    @FXML
    private JFXTextField txtCountryCallingCode;

    @FXML
    private JFXTextField txtStudentBCID;

    @FXML
    private JFXCheckBox cbxQuit;

    private ActivityBO activityBOImpl;
    private TelNoBO telNoBOImpl;
    private StudentBO studentBOImpl;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acNewStudent);
        colTelNo_tblTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));

        colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        telNoBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEL_NO);

        txtStudentID.requestFocus();
        cboxHouse.getItems().setAll(House.getAllHouses());
        Common.loadGrade(cboxGrade);
        Common.loadClass(cboxClass);
        txtStudentID.focusedProperty().addListener((observable, oldValue, newValue) -> selectStudentHouse());
        try {
            loadActivity();
            int newIndex = studentBOImpl.getNewIndex() + 1;
            txtStudentID.setText(String.valueOf(newIndex));
            selectStudentHouse();
        } catch (Exception e) {
            callLogger(e);
        }

    }

    private void loadActivity() throws Exception {
        for (ActivityDTO act : activityBOImpl.getAllActivity()) {
            cboxActivityName.getItems().addAll(act);
        }
    }


    @FXML
    void btAddActivity_onAction(ActionEvent event) {

        ActivityDTO selectedItem = cboxActivityName.getSelectionModel().getSelectedItem();
        Date date;
        if (selectedItem == null) {
            OptionPane.showErrorAtSide("Please select an activityBOImpl");
        } else if (Common.getDate(dtJoinedDate) == null) {
            OptionPane.showErrorAtSide("Please select the joined date");
        } else {
            date = Common.getDate(dtJoinedDate);
            if (checkStudentID()) {
                int id = getStudentID();
                tblActivity.getItems().add(new RegistrationDTO(id, selectedItem, date));
                cboxActivityName.getItems().remove(cboxActivityName.getValue());
            } else
                OptionPane.showErrorAtSide("Please enter Student ID");
        }
    }

    @FXML
    void btAddTelNo_onAction(ActionEvent event) {

        String tel = txtTelNo.getText().trim();

        if (!(tel.length() == 10 && tel.matches("[0][0-9]{9}"))) {

            OptionPane.showErrorAtSide("Please enter a valid phone number");

        } else if (!checkStudentID()) {

            OptionPane.showErrorAtSide("Please enter the studentBOImpl ID");

        } else {
            int stNum = getStudentID();
            tblTelNo.getItems().add(new TelNoDTO(tel, stNum));
            txtTelNo.clear();
            txtTelNo.requestFocus();
        }
    }

    @FXML
    private void txtCountryCallingCode_onAction(ActionEvent actionEvent) {
    }

    @FXML
    void btFinish_activityForStudent_onAction(ActionEvent event) {
        addStudent();
    }

    private void addStudent() {
        if (checkStudentID()) {
            try {
                if (!studentBOImpl.isUniqueStudentID(Integer.parseInt(txtStudentID.getText()))) {
                    OptionPane.showErrorAtSide("The student ID must be unique");
                    return;
                }
            } catch (Exception e) {
                callLogger(e);
            }
            if (checkBCID()) {
                try {
                    if (studentBOImpl.isUniqueBCID(Integer.valueOf(txtStudentBCID.getText())))
                        if (checkStudentName())
                            if (checkClass())
                                if (checkHouse())
                                    if (checkDOB())
                                        if (checkParents()) {
                                            int stId = getStudentID();
                                            String stName = txtStudentName.getText().trim().replaceAll(" +", " ");
                                            int house = cboxHouse.getSelectionModel().getSelectedItem().getHouseValue();
                                            boolean gender = rbMale.isSelected();
                                            String grade = cboxGrade.getSelectionModel().getSelectedItem();
                                            String class_ = cboxClass.getSelectionModel().getSelectedItem();
                                            if (!grade.equals("Left"))
                                                grade += "-";
                                            else
                                                class_ = "";
                                            String grade_class = grade + class_;
                                            Date DOB = Common.getDate(dtDOB);
                                            String motherName = txtMotherName.getText();
                                            String fatherName = txtFatherName.getText();
                                            String address = txtAddress.getText();
                                            String note = txtaDesc.getText();
                                            ObservableList<TelNoDTO> allTelNum = tblTelNo.getItems();
                                            ObservableList<RegistrationDTO> allInitialActivity = tblActivity.getItems();
                                            boolean quit = cbxQuit.isSelected();
                                            int bcid = Integer.valueOf(txtStudentBCID.getText());
                                            try {
                                                boolean b = studentBOImpl.addStudentWithActivity(new StudentDTO(
                                                        stId,
                                                        stName,
                                                        gender,
                                                        DOB,
                                                        grade_class,
                                                        fatherName,
                                                        motherName,
                                                        note,
                                                        house,
                                                        address,
                                                        allTelNum,
                                                        allInitialActivity,
                                                        quit,
                                                        bcid)
                                                );
                                                if (b) {
                                                    OptionPane.showDoneAtSide("Student has successfully added");
                                                    if (OptionPane.askQuestion("Do you want to add more student?")) {
                                                        screenLoader.loadOnCenterOfBorderPane(
                                                                "/lk/ijse/mountCalvary/view/student/NewStudent.fxml",
                                                                this.acNewStudent, this);
                                                    } else {
                                                        screenLoader.loadOnCenterOfBorderPane(
                                                                "/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml",
                                                                this.acNewStudent, this);
                                                    }
                                                } else OptionPane.showErrorAtSide("Student has not added");
                                            } catch (Exception e) {
                                                callLogger(e);
                                                showErrorAtSide("Somethings wrong we can't do your request now.");
                                            }
                                        } else showErrorAtSide("Please enter the parent details.");
                                    else showErrorAtSide("Please enter the birth day.");
                                else showErrorAtSide("Please enter the house.");
                            else showErrorAtSide("Please select the Grade and the class.");
                        else showErrorAtSide("Please enter Student name.");
                    else showErrorAtSide("The birth certificate ID must be unique.");
                } catch (Exception e) {
                    callLogger(e);
                }
            } else showErrorAtSide("Please enter valid birth certificate id.");
        } else showErrorAtSide("Please enter the studentBOImpl ID.");

    }

    private boolean checkParents() {
        return txtMotherName.getText().length() != 0 || txtFatherName.getText().length() != 0;
    }

    private boolean checkDOB() {
        return Common.getDate(dtDOB) != null;
    }

    private boolean checkHouse() {
        try {
            cboxHouse.getSelectionModel().getSelectedItem();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkClass() {
        try {
            String grade = cboxGrade.getSelectionModel().getSelectedItem();
            String clz = cboxClass.getSelectionModel().getSelectedItem();
            if (grade.equals("Left"))
                return true;
            else return clz.length() > 0 && grade.length() > 0;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkStudentName() {
        return txtStudentName.getText().length() > 0;
    }

    private boolean checkBCID() {
        return Common.isInteger(txtStudentBCID.getText());
    }

    private int getStudentID() {
        return Integer.parseInt(txtStudentID.getText());
    }

    @FXML
    void btRemove_tblActivity_onAction(ActionEvent event) {
        try {
            Object reg = Common.removeItemFromTable(tblActivity);
            cboxActivityName.getItems().add(((RegistrationDTO) reg).getActivity());
        } catch (NullPointerException e) {
            OptionPane.showErrorAtSide("Please select an item from the table.");
        }
    }

    @FXML
    void btRemove_tblTelNo_onAction(ActionEvent event) {
        try {
            Common.removeItemFromTable(tblTelNo);
        } catch (NullPointerException e) {
            OptionPane.showErrorAtSide("Please select an item from the table.");
        }
    }

    @FXML
    void cboxClass_onAction(ActionEvent event) {
    }

    @FXML
    void cboxGrade_onAction(ActionEvent event) {
        boolean isLastSelect = cboxGrade.getSelectionModel().isSelected(cboxGrade.getItems().size() - 1);
        cboxClass.setDisable(isLastSelect);
        cbxQuit.setSelected(isLastSelect);
        cboxGrade.setDisable(isLastSelect);
    }

    @FXML
    void dtDOB_onAction(ActionEvent event) {
    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {
        cboxActivityName.requestFocus();
    }

    @FXML
    void cboxActivityName_onAction(ActionEvent event) {
        dtJoinedDate.requestFocus();
    }

    @FXML
    void txtAddress_onAction(ActionEvent event) {
        txtTelNo.requestFocus();
    }

    @FXML
    void txtFatherName_onAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtMotherName_onAction(ActionEvent event) {
        txtFatherName.requestFocus();
    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        selectStudentHouse();
        txtStudentName.requestFocus();
    }

    private void selectStudentHouse() {
        if (checkStudentID()) {
            int house = getStudentID() % 4;
            switch (house) {
                case 0:
                    cboxHouse.getSelectionModel().select(house);
                    break;
                case 1:
                    cboxHouse.getSelectionModel().select(house);
                    break;
                case 2:
                    cboxHouse.getSelectionModel().select(house);
                    break;
                case 3:
                    cboxHouse.getSelectionModel().select(house);
                    break;
                default:
                    break;
            }
        } else {
            OptionPane.showErrorAtSide("Please input valid student ID as number");
        }

    }

    @FXML
    void txtStudentName(ActionEvent event) {
        rbMale.requestFocus();
    }

    @FXML
    void txtTelNo_onAction(ActionEvent event) {
        btAddTelNo.fire();
    }

    @FXML
    void txtaDesc_onAction(MouseEvent event) {
    }

    @FXML
    private void cboxHouse_onAction(ActionEvent actionEvent) {
    }

    private boolean checkStudentID() {
        return isInteger(txtStudentID.getText());
    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {
        boolean answer = OptionPane.askQuestion("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml",
                    this.acNewStudent, this);
        }
    }

    @FXML
    private void txtStudentBCID_onAction(ActionEvent actionEvent) {
        try {
            if (Common.isInteger(txtStudentBCID.getText()))
                if (studentBOImpl.isUniqueBCID(Integer.parseInt(txtStudentBCID.getText())))
                    txtStudentID.requestFocus();
                else showErrorAtSide("The birth certificate ID must be unique.");
            else
                OptionPane.showErrorAtSide("Please enter valid student birth certificate ID.");
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    private void bcxQuit_onAction(ActionEvent actionEvent) {
        if (cbxQuit.isSelected()) {
            cboxGrade.getSelectionModel().selectLast();
            cboxClass.setDisable(true);
            cboxGrade.setDisable(true);
        } else {
            cboxGrade.getSelectionModel().selectFirst();
            cboxClass.setDisable(false);
            cboxGrade.setDisable(false);
        }
    }

}
