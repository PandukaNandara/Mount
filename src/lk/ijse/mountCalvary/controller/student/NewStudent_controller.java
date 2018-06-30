package lk.ijse.mountCalvary.controller.student;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.ActivityBO;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TelNoBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.GlobalBoolean;
import lk.ijse.mountCalvary.controller.OptionPane;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.ActivityDTO;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lk.ijse.mountCalvary.controller.Common.isInteger;
import static lk.ijse.mountCalvary.controller.OptionPane.showError;

public class NewStudent_controller implements Initializable {

    @FXML
    private AnchorPane acNewStudent;

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
    private JFXComboBox<String> cboxHouse;

    private ActivityBO activityBOImpl;
    private TelNoBO telNoBOImpl;
    private StudentBO studentBOImpl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(true);
        colTelNo_tblTelNo.setCellValueFactory(new PropertyValueFactory<>("telNoBOImpl"));
        colActivity.setCellValueFactory(new PropertyValueFactory<>("activityBOImpl"));
        colJoinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        activityBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.ACTIVITY);
        telNoBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEL_NO);

        txtStudentID.requestFocus();
        Common.loadHouse(cboxHouse);
        Common.loadGrade(cboxGrade);
        Common.loadClass(cboxClass);

        try {
            loadActivity();
            int newIndex = studentBOImpl.getNewIndex() + 1;
            txtStudentID.setText(String.valueOf(newIndex));
            txtStudentID.setEditable(false);
        } catch (Exception e) {
            Logger.getLogger(NewStudent_controller.class.getName()).log(Level.SEVERE, null, e);

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
        Date date = null;
        if (selectedItem == null) {
            OptionPane.showError("Please select an activityBOImpl");
        } else if (Common.getDate(dtJoinedDate) == null) {
            OptionPane.showError("Please select the joined date");
        } else {
            date = Common.getDate(dtJoinedDate);
            if (checkStudentID()) {
                int id = getStudentID();
                tblActivity.getItems().add(new RegistrationDTO(id, selectedItem, date));
            } else
                OptionPane.showError("Please enter Student ID");
        }
    }

    @FXML
    void btAddTelNo_onAction(ActionEvent event) {

        String tel = txtTelNo.getText().trim();

        if (!(tel.length() == 10 && tel.matches("[0][0-9]{9}"))) {

            OptionPane.showError("Please enter a valid phone number");

        } else if (!checkStudentID()) {

            OptionPane.showError("Please enter the studentBOImpl ID");

        } else {
            int stNum = getStudentID();
            tblTelNo.getItems().add(new TelNoDTO(Integer.parseInt(tel), stNum));
            txtTelNo.setText("");
            txtTelNo.requestFocus();
        }
    }

    @FXML
    void btFinish_activityForStudent_onAction(ActionEvent event) throws Exception {
        addStudent();
    }

    private void addStudent() throws Exception {
        if (checkStudentID()) {
            if (checkStudentName()) {
                if (checkClass()) {
                    if (checkHouse()) {
                        if (checkDOB()) {
                            if (checkParents()) {
                                int stId = getStudentID();
                                String stName = txtStudentName.getText().trim().replaceAll(" +", " ");
                                String house = cboxHouse.getSelectionModel().getSelectedItem();
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
                                try {
                                    boolean b = studentBOImpl.addStudent(new StudentDTO(stId, stName, gender, DOB, grade_class, fatherName, motherName, note, house, address, allTelNum, allInitialActivity));
                                    String text = "";
                                    if (b)
                                        text = "Student has successfully added";
                                    else
                                        text = "Student has not successfully added";
                                    Alert a = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
                                    a.showAndWait();
                                } catch (Exception e) {
                                    showError(e.getMessage());
                                }
                                if (OptionPane.askQuestion("Do you want to add more studentBOImpl?")) {
                                    try {
                                        ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/student/NewStudent.fxml", this.acNewStudent, this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewStudent, this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                showError("Please enter the parent details");
                            }
                        } else {
                            showError("Please enter the birth day");
                        }
                    } else {
                        showError("Please enter the house");
                    }
                } else {
                    showError("Please select the Grade and the class");
                }
            } else {
                showError("Please enter Student name");
            }
        } else {
            showError("Please enter the studentBOImpl ID");
        }

    }

    private boolean checkParents() {
        if (txtMotherName.getText().length() == 0 && txtFatherName.getText().length() == 0)
            return false;
        return true;
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
        String grade = cboxGrade.getSelectionModel().getSelectedItem();
        String clz = cboxClass.getSelectionModel().getSelectedItem();
        if (grade.equals("Left"))
            return true;
        else if (clz.length() > 0 && grade.length() > 0)
            return true;
        else
            return false;
    }

    private boolean checkStudentName() {
        if (txtStudentName.getText().length() == 0)
            return false;
        return true;
    }

    private int getStudentID() {
        return Integer.parseInt(txtStudentID.getText());
    }

    @FXML
    void btRemove_tblActivity_onAction(ActionEvent event) {
        Common.removeItemFromTable(tblActivity);
    }

    @FXML
    void btRemove_tblTelNo_onAction(ActionEvent event) {
        Common.removeItemFromTable(tblTelNo);
    }

    @FXML
    void cboxClass_onAction(ActionEvent event) {

    }

    @FXML
    void cboxGrade_onAction(ActionEvent event) {
    }

    @FXML
    void dtDOB_onAction(ActionEvent event) {
    }

    @FXML
    void dtJoinedDate_onAction(ActionEvent event) {

        cboxActivityName.requestFocus();
    }

    @FXML
    void rbFemale(ActionEvent event) {

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
        if (checkStudentID()) {
            int house = getStudentID() % 4;
            System.out.println(house);
            switch (house) {
                case 1:
                    cboxHouse.getSelectionModel().select(0);
                    break;
                case 2:
                    cboxHouse.getSelectionModel().select(1);
                    break;
                case 3:
                    cboxHouse.getSelectionModel().select(2);
                    break;
                case 0:
                    cboxHouse.getSelectionModel().select(3);
                    break;
                default:
                    break;
            }
            txtStudentName.requestFocus();
        } else {
            OptionPane.showError("Please input valid studentBOImpl ID as number");
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
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewStudent, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
