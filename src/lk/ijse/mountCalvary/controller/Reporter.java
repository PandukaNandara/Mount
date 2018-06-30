package lk.ijse.mountCalvary.controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
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

    public static void showReport(JasperPrint jasperPrint, String title) throws IOException {
        JasperViewer jv = new JasperViewer(jasperPrint, false);

        ImageIcon img = new ImageIcon("/lk/ijse/mountCalvary/assets/print.ico");
        jv.setIconImage(img.getImage());

        jv.setAlwaysOnTop(true);
        jv.setTitle(title);



        jv.setVisible(true);

    }
    private static enum FileType {
        PDF, DOCX, HTML
    }
}
