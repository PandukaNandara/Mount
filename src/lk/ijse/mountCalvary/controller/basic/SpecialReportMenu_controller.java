package lk.ijse.mountCalvary.controller.basic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lk.ijse.mountCalvary.controller.tool.ButtonFireForEnterSetter;
import lk.ijse.mountCalvary.controller.tool.GlobalBoolean;
import lk.ijse.mountCalvary.controller.tool.Reporter;
import lk.ijse.mountCalvary.controller.tool.ScreenLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SpecialReportMenu_controller implements Initializable {
    @FXML
    private VBox acSpecialReport;
    @FXML
    private JFXButton btBack;
    @FXML
    private JFXButton btStudentNotFollowActivity;
    private ScreenLoader screenLoader = ScreenLoader.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GlobalBoolean.setLock(false);
        ButtonFireForEnterSetter.setGlobalEventHandler(acSpecialReport);
    }

    @FXML
    void btBack_onAction(ActionEvent event) {
        screenLoader.loadOnCenterOfBorderPane("/lk/ijse/mountCalvary/view/basic/MainMenu.fxml", this.acSpecialReport, this);
    }

    @FXML
    void btStudentNotFollowActivity_onAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException, JRException {
        Class.forName("com.mysql.jdbc.Driver");
        String jdbcURL = String.format("jdbc:mysql://%s:%s/%s", "localhost", "3306", "Thogakade");
        Connection connection = DriverManager.getConnection(jdbcURL, "root", "1998");

        InputStream ras = getClass().getResourceAsStream("/sample/report/Chart.jasper");
        HashMap map = new HashMap();
        JasperPrint jasperPrint = JasperFillManager.fillReport(ras, map,connection);
        Reporter.showReport(jasperPrint, "Special report");
    }

}
