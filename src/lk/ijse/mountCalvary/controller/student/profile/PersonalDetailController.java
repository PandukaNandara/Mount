package lk.ijse.mountCalvary.controller.student.profile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.business.BOFactory;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.business.custom.TelNoBO;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.*;
import lk.ijse.mountCalvary.entity.Gender;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public final class PersonalDetailController extends SuperController implements Initializable {


    private static JasperReport studentPersonalDetail;
    @FXML
    private VBox personalDetail;
    @FXML
    private JFXTextField txtStudentName;
    @FXML
    private TableView<TelNoDTO> tblTelNo;
    @FXML
    private TableColumn<TelNoDTO, Integer> colTelNo;
    @FXML
    private JFXTextField txtMotherName;
    @FXML
    private JFXTextField txtFatherName;
    @FXML
    private JFXTextField txtStudentID;
    @FXML
    private JFXTextArea txtaDescStudent;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtBirthDay;
    @FXML
    private JFXTextField txtGrade;
    @FXML
    private JFXTextField txtHouse;
    @FXML
    private JFXTextField txtClass;
    @FXML
    private JFXCheckBox cbxQuit;
    @FXML
    private Label lblGender;
    @FXML
    private JFXTextField txtStudentBCID;
    @FXML
    private JFXButton btPrint;
    private TelNoBO telNoBOImpl;
    private StudentBO studentBOImpl;
    private ArrayList<StudentDTO> allStudentDetail;
    private StudentProfileController studentProfileController;
    private StudentDTO selectedStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(personalDetail);

        studentBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        telNoBOImpl = BOFactory.getInstance().getBO(BOFactory.BOType.TEL_NO);

    }

    public void init(StudentProfileController studentProfileController) {
        this.studentProfileController = studentProfileController;
    }

    protected void insertStudent(StudentDTO i) {
        loadStudentData(i);
    }

    private void loadStudentData(StudentDTO i) {
        try {
            i = studentBOImpl.getStudent(i.getSID());
        } catch (Exception e) {
            callLogger(e);
        }
        if (i != null) {
            selectedStudent = i;
            txtStudentID.setText(String.valueOf(i.getSID()));
            txtStudentName.setText(i.getSName());
            txtFatherName.setText(i.getFatherName());
            txtMotherName.setText(i.getMotherName());
            String[] class_ = i.getSClass().split("-");

            if (class_.length == 2) {
                txtGrade.setText(class_[0]);
                txtClass.setText(class_[1]);
            } else {
                txtGrade.setText(class_[0]);
            }
            txtStudentBCID.setText(String.valueOf(i.getBCID()));
            cbxQuit.setSelected(i.isQuit());
            txtHouse.setText(new House(i.getHouse()).toString());
            if (i.isGender())
                lblGender.setText("Male");
            else
                lblGender.setText("Female");
            try {
                txtBirthDay.setText(Objects.requireNonNull(Common.dateToLocalDate(i.getDOB())).toString());
            } catch (NullPointerException e) {
                txtBirthDay.setText("N/A");
            }
            cbxQuit.setSelected(i.isQuit());
            txtAddress.setText(i.getAddress());

            try {
                tblTelNo.setItems(telNoBOImpl.getTelNosForThisStudent(i.getSID()));
                Common.clearSortOrder(tblTelNo);
            } catch (Exception e) {
                callLogger(e);
            }
            txtaDescStudent.setText(i.getNote());
        } else {
            OptionPane.showErrorAtSide("Please select corresponding student");
        }
    }

    @FXML
    private void btPrint_onAction(ActionEvent actionEvent) {
        if (selectedStudent != null) {
            try {
                if (studentPersonalDetail == null) {
                    InputStream studentPersonalDetailFile = getClass().getResourceAsStream("/lk/ijse/mountCalvary/report/student/StudentPersonalDetailReport.jrxml");
                    studentPersonalDetail = JasperCompileManager.compileReport(studentPersonalDetailFile);
                }
                JRBeanCollectionDataSource telNos = new JRBeanCollectionDataSource(tblTelNo.getItems());
                HashMap map = new HashMap();
                map.put("StudentID", selectedStudent.getSID());
                map.put("StudentName", selectedStudent.getSName());
                map.put("DOB", selectedStudent.getDOB().toString());
                map.put("Gender", selectedStudent.isGender() == Gender.MALE ? "Male" : "Female");
                map.put("FatherName", selectedStudent.getFatherName());
                map.put("MotherName", selectedStudent.getMotherName());
                map.put("Address", selectedStudent.getAddress());
                map.put("Note", selectedStudent.getNote().length() < 2 ? " - " : selectedStudent.getNote());
                map.put("TelephoneNumbers", telNos);

                JasperPrint jasperPrint = JasperFillManager.fillReport(studentPersonalDetail, map, new JREmptyDataSource());

                Reporter.showReport(jasperPrint, "Personal details");

            } catch (Exception e) {
                callLogger(e);
            }
        } else {
            OptionPane.showErrorAtSide("Please select a student to print.");
        }
    }

//    @FXML
//    private void btSaveAs_onAction(ActionEvent actionEvent) {
//        if (selectedStudent != null) {
//            try {
//
//                //InputStream resourceAsStream = getClass().getResourceAsStream("");
//
//                JasperReport jasperReport = JasperCompileManager.compileReport("/lk/ijse/mountCalvary/certificate/StudentPersonalDetailReport.jasper");
//
//                HashMap map = new HashMap();
//                map.put("StudentID", Integer.parseInt(txtStudentID.getText()));
//                map.put("StudentName", txtStudentName.getText());
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DBConnection.getInstance().getConnection());
//
//                File saveLocationAndType = Reporter.getSaveLocationAndType(txtStudentName.getText(), (Stage) personalDetail.getScene().getWindow());
//
//                OutputStream out = new FileOutputStream(saveLocationAndType);
//                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//            } catch (Exception e) {
// Logger.getLogger(PersonalDetailController.class.getName()).log(Level.SEVERE, null, e);
//                e.printStackTrace();
//            }
//        } else {
//            Common.showErrorAtSide("Please select a student to print.");
//        }
//    }

    @FXML
    private void ptPdf_onAction(ActionEvent actionEvent) {

    }

}
