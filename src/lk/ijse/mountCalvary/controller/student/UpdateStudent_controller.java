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
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TelNoBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.controller.tool.*;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static lk.ijse.mountCalvary.controller.tool.Common.isInteger;
import static lk.ijse.mountCalvary.controller.tool.OptionPane.showErrorAtSide;

public final class UpdateStudent_controller extends SuperController implements Initializable {

    @FXML
    private VBox acUpdateStudent;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private JFXRadioButton rbMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton rbFemale;
    @FXML
    private JFXComboBox<String> cboxClass;
    @FXML
    private JFXComboBox<String> cboxGrade;
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
    private JFXTextField txtAddress;
    @FXML
    private JFXButton btUpdateStudent;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btSearch;
    @FXML
    private TableView<TelNoDTO> tblTelNo;
    @FXML
    private TableColumn<TelNoDTO, String> colTelNo_tblTelNo;
    @FXML
    private JFXButton btUpdate_telNo;
    @FXML
    private JFXTextField txtNewTelNo;
    @FXML
    private JFXComboBox<House> cboxHouse;
    @FXML
    private JFXButton btRemove_telNo;
    @FXML
    private TableView<TelNoDTO> tblUpdatedTelNo;
    @FXML
    private TableColumn<TelNoDTO, String> colOdlTelNo_tblUpdatedTelNo;
    @FXML
    private TableColumn<TelNoDTO, String> colNewTelNo_tblUpdatedTelNo;
    @FXML
    private JFXTextField txtStudentBCID;
    @FXML
    private JFXCheckBox cbxQuit;

    private TelNoBO telNo;
    private StudentBO studentBO;
    private ObservableList<StudentDTO> allStudent;
    private AutoComplete<StudentDTO> autoCompleteStudent;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    private StudentDTO selectedStudent;
    private TelNoDTO selectTel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GlobalBoolean.setLock(true);
        ButtonFireForEnterSetter.setGlobalEventHandler(acUpdateStudent);

        colTelNo_tblTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colOdlTelNo_tblUpdatedTelNo.setCellValueFactory(new PropertyValueFactory<>("oldTelNo"));
        colNewTelNo_tblUpdatedTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));

        studentBO = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        telNo = BOFactory.getInstance().getBO(BOFactory.BOType.TEL_NO);

        cboxHouse.getItems().setAll(House.getAllHouses());
        Common.loadClass(cboxClass);
        Common.loadGrade(cboxGrade);
        rbMale.setDisable(true);
        rbFemale.setDisable(true);

        try {
            allStudent = studentBO.getAllStudentNameAndNumber();
            autoCompleteStudent = new AutoComplete<>(txtStudentName, allStudent);
            autoCompleteStudent.setAutoCompletionsAction(event -> searchStudentByName());
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    void btSearch_onAction(ActionEvent event) {
        if (selectedStudent == null) OptionPane.showErrorAtSide("Please select the student.");
        else try {
            loadStudentData(studentBO.getStudent(selectedStudent.getSID()));
            txtStudentID.setDisable(true);
        } catch (Exception e) {
            callLogger(e);
        }
    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        if (checkStudentID()) {
            selectedStudent = autoCompleteStudent.searchByID(txtStudentID.getText());
            if (selectedStudent == null)
                OptionPane.showErrorAtSide("There is no student who has student id " + txtStudentID.getId());
            else btSearch.fire();
        } else
            OptionPane.showErrorAtSide("Please input valid student ID as number");
    }

    private void loadStudentData(StudentDTO i) {
        txtStudentBCID.setText(String.valueOf(i.getBCID()));
        txtStudentID.setText(String.valueOf(i.getSID()));
        txtStudentName.setText(i.getSName());
        txtFatherName.setText(i.getFatherName());
        txtMotherName.setText(i.getMotherName());
        cbxQuit.setSelected(i.isQuit());
        String[] class_ = i.getSClass().split("-");
        if (class_.length == 2) {
            cboxGrade.setValue(class_[0]);
            cboxClass.setValue(class_[1]);
        } else {
            cboxGrade.setValue(class_[0]);
        }
        cboxHouse.setValue(new House(i.getHouse()));
        if (!i.isGender())
            rbFemale.fire();
        dtDOB.setValue(Common.dateToLocalDate(i.getDOB()));
        txtAddress.setText(i.getAddress());
        tblTelNo.setItems(i.getTelNoList());
        txtaDesc.setText(i.getNote());
    }

    @FXML
    void btUpdateStudent_onAction(ActionEvent event) {
        updateStudent();
    }

    private boolean checkStudentID() {
        return isInteger(txtStudentID.getText());
    }

    private int getStudentID() {
        return Integer.parseInt(txtStudentID.getText());
    }

    private void searchStudentByName() {
        selectedStudent = autoCompleteStudent.getSelectedItemByName();
        btSearch.fire();
    }

    @FXML
    void btUpdate_telNo_onAction(ActionEvent event) {
        String newTel = txtNewTelNo.getText().trim();
        if (!(newTel.matches("[0][0-9]{9}"))) {
            OptionPane.showErrorAtSide("Please enter a valid phone number");
        } else if (!checkStudentID()) {
            OptionPane.showErrorAtSide("Please enter the student ID");
        } else {
            try {
                tblUpdatedTelNo.getItems().add(new TelNoDTO(selectTel.getTelID(), newTel,
                        selectTel.getSID(), selectTel.getTelNo()));
                tblTelNo.getItems().remove(tblTelNo.getSelectionModel().getSelectedIndex());
                txtNewTelNo.clear();
            } catch (Exception e) {
                callLogger(e);
                showErrorAtSide("Please select the old telephone number");
            }
        }
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

    @FXML
    private void btCancel_onAction(ActionEvent event) {
        boolean answer = OptionPane.askQuestion("Do you want to cancel?");
        if (answer) {
            screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml",
                    this.acUpdateStudent, this);
        }
    }

    private boolean checkClass() {
        String grade = cboxGrade.getSelectionModel().getSelectedItem();
        String clz = cboxClass.getSelectionModel().getSelectedItem();
        if (grade.equals("Left"))
            return true;
        else return clz.length() > 0 && grade.length() > 0;
    }

    private boolean checkStudentName() {
        return txtStudentName.getText().length() != 0;
    }

    @FXML
    private void cboxClass_onAction(ActionEvent event) {
        boolean isLastSelect = cboxClass.getSelectionModel().isSelected(cboxClass.getItems().size() - 1);
        cboxClass.setDisable(isLastSelect);
        cbxQuit.setSelected(isLastSelect);
        cboxGrade.setDisable(isLastSelect);
    }

    @FXML
    private void cboxGrade_onAction(ActionEvent event) {
        boolean isLastSelect = cboxGrade.getSelectionModel().isSelected(cboxGrade.getItems().size() - 1);
        cboxClass.setDisable(isLastSelect);
        cbxQuit.setSelected(isLastSelect);
        cboxGrade.setDisable(isLastSelect);
    }

    @FXML
    private void cboxHouse_onAction(ActionEvent actionEvent) {
    }

    @FXML
    private void tblTelNoClicked_onAction(MouseEvent event) {
        try {
            selectTel = tblTelNo.getSelectionModel().getSelectedItem();
            txtNewTelNo.setText(String.valueOf(selectTel.getTelNo()));
        } catch (Exception e) {
            callLogger(e);
        }

    }

    @FXML
    private void btRemove_telNo_onAction(ActionEvent actionEvent) {
        try {
            TelNoDTO selectTel = tblUpdatedTelNo.getSelectionModel().getSelectedItem();
            tblTelNo.getItems().add(new TelNoDTO(selectTel.getTelID(), selectTel.getOldTelNo(), selectTel.getSID()));
            Common.removeItemFromTable(tblUpdatedTelNo);
        } catch (NullPointerException e) {
            OptionPane.showErrorAtSide("Please select a telephone number.");
        }
    }

    @FXML
    private void cbxQuit_onAction(ActionEvent actionEvent) {
        if (cbxQuit.isSelected()) {
            cboxClass.getSelectionModel().selectLast();
            cboxClass.setDisable(true);
            cboxGrade.setDisable(true);
        } else {
            cboxClass.getSelectionModel().selectFirst();
            cboxClass.setDisable(false);
            cboxGrade.setDisable(false);
        }
    }

    private void updateStudent() {
        if (checkStudentID()) {
            if (checkStudentName()) {
                if (checkClass()) {
                    if (checkHouse()) {
                        if (checkDOB()) {
                            if (checkParents()) {
                                int stId = getStudentID();
                                String stName = txtStudentName.getText();
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
                                ObservableList<TelNoDTO> allTelNum = tblUpdatedTelNo.getItems();
                                boolean quit = cbxQuit.isSelected();
                                int BCID = Integer.parseInt(txtStudentBCID.getText());
                                try {
                                    boolean b = studentBO.updateStudent(new StudentDTO(stId, stName, gender,
                                            DOB, grade_class, fatherName, motherName, note, house, address,
                                            allTelNum, quit, BCID));
                                    if (b) {
                                        OptionPane.showDoneAtSide("Student has successfully updated");
                                        screenLoader.loadOnCenterOfBorderPane(
                                                "/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml",
                                                this.acUpdateStudent, this);
                                    } else
                                        OptionPane.showWarningAtSide("Student has not successfully updated");
                                } catch (Exception e) {
                                    callLogger(e);
                                    showErrorAtSide("Something's wrong we can't do your request.");
                                }


                            } else {
                                showErrorAtSide("Please enter the parent details");
                            }
                        } else {
                            showErrorAtSide("Please enter the birth day");
                        }
                    } else {
                        showErrorAtSide("Please enter the house");
                    }
                } else {
                    showErrorAtSide("Please select the Grade and the class");
                }
            } else {
                showErrorAtSide("Please enter Student name");
            }
        } else {
            showErrorAtSide("Please enter the student ID");
        }
    }

    @FXML
    private void txtStudentBCID_onAction(ActionEvent actionEvent) {
    }

    @FXML
    private void dtDOB_onAction(ActionEvent event) {
    }

    @FXML
    private void rbFemale(ActionEvent event) {
    }

    @FXML
    private void txtAddress_onAction(ActionEvent event) {
    }

    @FXML
    private void txtFatherName_onAction(ActionEvent event) {
    }

    @FXML
    private void txtMotherName_onACtion(ActionEvent event) {
    }

    @FXML
    private void txtNewTelNo_onAction(ActionEvent event) {
        btUpdate_telNo.fire();
    }

    @FXML
    private void txtStudentName_onAction(ActionEvent event) {
        searchStudentByName();
    }

    @FXML
    private void txtaDesc_onAction(MouseEvent event) {
    }

}
