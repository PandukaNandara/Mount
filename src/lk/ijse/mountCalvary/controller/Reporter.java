package lk.ijse.mountCalvary.controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Reporter {
    private static ArrayList<FileChooser.ExtensionFilter> getAllExtensionForSaveAs() {
        ArrayList<FileChooser.ExtensionFilter> a = new ArrayList<>();
        a.add(new FileChooser.ExtensionFilter("PDF file (.pdf)", "pdf"));
        a.add(new FileChooser.ExtensionFilter("Microsoft word (.docx)", "docx"));
        a.add(new FileChooser.ExtensionFilter("Hypertext Markup Language (.html)", "html"));
        return a;
    }
    public static File getSaveLocationAndType(String text, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(text);
        fileChooser.getExtensionFilters().setAll(getAllExtensionForSaveAs());
        //Show save file dialog
        return fileChooser.showSaveDialog(stage);
    }

    public static void showReport(JasperPrint jasperPrint, String title){
        JasperViewer jv = new JasperViewer(jasperPrint, false);
        jv.setAlwaysOnTop(true);
        jv.setTitle(title);
        jv.setIconImage(Toolkit.getDefaultToolkit().getImage(jv.getClass().getResource("/lk/ijse/mountCalvary/assets/report-icon.ico")));
        jv.setVisible(true);

    }
    private static enum FileType {
        PDF, DOCX, HTML
    }
}
