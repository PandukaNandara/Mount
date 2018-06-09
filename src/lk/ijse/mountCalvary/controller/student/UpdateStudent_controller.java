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
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TelNoBO;
import lk.ijse.mountCalvary.controller.Common;
import lk.ijse.mountCalvary.controller.basic.ScreenLoader;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static lk.ijse.mountCalvary.controller.Common.isInteger;
import static lk.ijse.mountCalvary.controller.Common.showError;

public class UpdateStudent_controller implements Initializable {

    StudentBO studentBO;
    ArrayList<StudentDTO> allStudent;
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
    private TableColumn<TelNoDTO, Integer> colTelNo_tblTelNo;
    @FXML
    private JFXButton btUpdate_telNo;
    @FXML
    private JFXTextField txtNewTelNo;
    @FXML
    private JFXComboBox<String> cboxHouse;
    @FXML
    private JFXButton btRemove_telNo;
    @FXML
    private TableView<TelNoDTO> tblUpdatedTelNo;
    @FXML
    private TableColumn<TelNoDTO, Integer> colOdlTelNo_tblUpdatedTelNo;
    @FXML
    private TableColumn<TelNoDTO, Integer> colNewTelNo_tblUpdatedTelNo;
    private TelNoBO telNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        telNo = BOFactory.getInstance().getBO(BOFactory.BOType.TEL_NO);

        colTelNo_tblTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colOdlTelNo_tblUpdatedTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colNewTelNo_tblUpdatedTelNo.setCellValueFactory(new PropertyValueFactory<>("oldTelNo"));

        studentBO = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        Common.loadHouse(cboxHouse);
        Common.loadClass(cboxClass);
        Common.loadGrade(cboxGrade);
        rbMale.setDisable(true);
        rbFemale.setDisable(true);
        try {
            allStudent = studentBO.getAllStudentNameAndNumber();
            System.out.println(allStudent);
            AutoCompletionBinding<StudentDTO> stList = TextFields.bindAutoCompletion(txtStudentName, allStudent);
            stList.setOnAutoCompleted(event -> {
                        btSearch.fire();
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btSearch_onAction(ActionEvent event) {
        StudentDTO i = searchStudent(txtStudentName.getText().trim(), allStudent);
        if (i == null) {
            Common.showError("Please select the student");
        } else {
            try {
                StudentDTO studentDTO = studentBO.getStudent(i.getSID());
                loadStudentData(studentDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void txtStudentID_onAction(ActionEvent event) {
        if (checkStudentID()) {
            StudentDTO i = searchStudent(Integer.parseInt(txtStudentID.getText()), allStudent);

            if (i == null) {
                Common.showError("There is no student who has student id " + txtStudentID.getId());
            } else {
                try {
                    StudentDTO studentDTO = studentBO.getStudent(i.getSID());
                    loadStudentData(studentDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Common.showError("Please input valid student ID as number");
        }

    }

    private void loadStudentData(StudentDTO i) {

        txtStudentID.setText("" + i.getSID());
        txtStudentName.setText(i.getsName());
        txtFatherName.setText(i.getFatherName());
        txtMotherName.setText(i.getMotherName());
        String[] class_ = i.getsClass().split("-");
        if (class_.length == 2) {
            cboxGrade.setValue(class_[0]);
            cboxClass.setValue(class_[1]);
        } else {
            cboxGrade.setValue(class_[0]);
        }
        cboxHouse.setValue(i.getHouse());
        if (!i.isGender())
            rbFemale.fire();
        dtDOB.setValue(Common.DateToLocalDate(i.getDOB()));
        txtAddress.setText(i.getAddress());
        tblTelNo.setItems(i.getTelNoList());
        txtaDesc.setText(i.getNote());
    }

    private StudentDTO searchStudent(int id, ArrayList<StudentDTO> list) {
        for (StudentDTO one : list) {
            if (one.getSID() == id) {
                return one;
            }
        }
        return null;
    }

    private StudentDTO searchStudent(String text, ArrayList<StudentDTO> list) {
        for (StudentDTO one : list) {
            if (one.getsName().equals(text.trim())) {
                return one;
            }
        }
        return null;
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

    @FXML
    void btUpdate_telNo_onAction(ActionEvent event) {
        String tel = txtNewTelNo.getText().trim();
        System.out.println("");
        if (!(tel.length() == 10 && tel.matches("[0][0-9]{9}"))) {

            Common.showError("Please enter a valid phone number");

        } else if (!checkStudentID()) {

            Common.showError("Please enter the student ID");

        } else {
            int newNumber = Integer.parseInt(tel);
            try {
                TelNoDTO selectedItem = tblTelNo.getSelectionModel().getSelectedItem();
                tblUpdatedTelNo.getItems().add(new TelNoDTO(selectedItem.getTelID(), selectedItem.getTelNo(), selectedItem.getSID(), newNumber));
                tblTelNo.getItems().remove(tblTelNo.getSelectionModel().getSelectedIndex());
                txtNewTelNo.setText("");
            } catch (Exception e) {
                showError("Please select the old telephone number");
            }

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

                                String house = cboxHouse.getSelectionModel().getSelectedItem();

                                boolean gender = rbMale.isSelected() ? true : false;
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
                                try {
                                    boolean b = studentBO.updateStudent(new StudentDTO(stId, stName, gender, DOB, grade_class, fatherName, motherName, note, house, address, allTelNum));
                                    String text = "";
                                    if (b)
                                        text = "Student has successfully updated";
                                    else
                                        text = "Student has not successfully updated";
                                    Alert a = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
                                    a.showAndWait();
                                } catch (Exception e) {
                                    showError(e.getMessage());
                                }

                                try {
                                    ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewStudent, this);
                                } catch (IOException e) {
                                    e.printStackTrace();
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
            showError("Please enter the student ID");
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

    @FXML
    void btCancel_onAction(ActionEvent event) {
        boolean answer = Common.askQuestion("Do you want to cancel?");
        if (answer) {
            try {
                ScreenLoader.loadPanel("/lk/ijse/mountCalvary/view/basic/StudentMenu.fxml", this.acNewStudent, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @FXML
    void cboxClass_onAction(ActionEvent event) {}

    @FXML
    void cboxGrade_onAction(ActionEvent event) {}

    @FXML
    void dtDOB_onAction(ActionEvent event) {}

    @FXML
    void rbFemale(ActionEvent event) {}

    @FXML
    void txtAddress_onAction(ActionEvent event) {}

    @FXML
    void txtFatherName_onAction(ActionEvent event) {}

    @FXML
    void txtMotherName_onACtion(ActionEvent event) {}

    @FXML
    void txtNewTelNo_onAction(ActionEvent event) {
        btUpdate_telNo.fire();
    }

    @FXML
    void txtStudentName(ActionEvent event) {
        btSearch.fire();
    }

    @FXML
    void txtaDesc_onAction(MouseEvent event) {

    }

    @FXML
    private void cboxHouse_onAction(ActionEvent actionEvent) {

    }

    @FXML
    private void tblTelNoClicked_onAction(MouseEvent event) {
        try {
            TelNoDTO selectedItem = tblTelNo.getSelectionModel().getSelectedItem();
            txtNewTelNo.setText(selectedItem.getTelNo() + "");
        } catch (Exception x) {
            System.out.println(x);
        }

    }

    @FXML
    private void btRemove_telNo_onAction(ActionEvent actionEvent) {
        TelNoDTO selectedItem = tblUpdatedTelNo.getSelectionModel().getSelectedItem();
        tblTelNo.getItems().add(new TelNoDTO(selectedItem.getOldTelNo(), selectedItem.getOldTelNo(), selectedItem.getSID()));
        Common.removeItemFromTable(tblUpdatedTelNo);
    }
}