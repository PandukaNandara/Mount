package lk.ijse.mountCalvary.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class Common {

    public static void showError(String message){
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.showAndWait();
    }
    public static void showWarning(String message){
        Alert a = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        a.showAndWait();
    }
    public static boolean askQuestion(String message){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> btSet = a.showAndWait();
        if(btSet.get() == ButtonType.YES)
            return true;
        else if(btSet.get() == ButtonType.NO)
            return false;
        else
            return false;
    }

    public static boolean askWarning(String message){
        Alert a = new Alert(Alert.AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> btSet = a.showAndWait();
        if(btSet.get() == ButtonType.YES)
            return true;
        else if(btSet.get() == ButtonType.NO)
            return false;
        else
            return false;
    }
    public static void showMessage(String message){
        JFXDialog d = new JFXDialog();

        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        a.showAndWait();
    }
    public static Object removeItemFromTable(TableView table){
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        try {
            return selectedItem;
        }finally {
            table.getItems().remove(selectedItem);
        }
    }
    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Date getDate(JFXDatePicker dt){
        try {
            return java.sql.Date.valueOf(dt.getValue());
        }catch (Exception e){
            return null;
        }
    }

    public static RegistrationDTO searchRegistration(String name, ObservableList<RegistrationDTO> list){
        for(RegistrationDTO oneRegi : list){
            if(oneRegi.getStudentName().toLowerCase().equals(name.trim().toLowerCase().replaceAll(" +", " ")))
                return oneRegi;
        }
        return null;
    }

    public static RegistrationDTO searchRegistration(int SID, ObservableList<RegistrationDTO> list){
        for(RegistrationDTO oneRegi : list){
            if(oneRegi.getSID() == SID)
                return oneRegi;
        }
        return null;
    }

    public static String[] getResultSet(){
        return new String[]{
                "1st Place",
                "2nd Place",
                "3rd Place",
                "Participation"
        };
    }

    public static void loadClass(JFXComboBox cboxClass) {
        cboxClass.getItems().addAll("A", "B", "C", "D");
    }

    public static void loadGrade(JFXComboBox cboxGrade) {
        cboxGrade.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "Left");
    }

    public static void loadHouse(JFXComboBox cboxHouse) {
        cboxHouse.getItems().addAll(
                "House 1",
                "House 2",
                "House 3",
                "House 4"
        );
    }
    public static Integer[] loadYear(){
        final int year = LocalDate.now().getYear();
        return new Integer[]{year - 3, year - 2, year - 1, year};
    }

    public static LocalDate DateToLocalDate(Date dt){
        try{
            return new java.sql.Date( dt.getTime()).toLocalDate();
        }catch (Exception e){
            return null;
        }
    }
    public static Date LocalDateToDate(LocalDate dt){
        try{
            return java.sql.Date.valueOf(dt);
        }catch (Exception e){
            return null;
        }
    }
    public static StudentDTO searchStudent(int id, ArrayList<StudentDTO> list) {
        for (StudentDTO one : list) {
            if (one.getSID() == id) {
                return one;
            }
        }
        return null;
    }
    public static StudentDTO searchStudent(int id, ObservableList<StudentDTO> list) {
        for (StudentDTO one : list) {
            if (one.getSID() == id) {
                return one;
            }
        }
        return null;
    }

    public static StudentDTO searchStudent(String text, ArrayList<StudentDTO> list) {
        for (StudentDTO one : list) {
            if (one.getsName().toLowerCase().equals(text.trim().toLowerCase())) {
                return one;
            }
        }
        return null;
    }
    public static StudentDTO searchStudent(String text, ObservableList<StudentDTO> list) {
        for (StudentDTO one : list) {
            if (one.getsName().toLowerCase().equals(text.trim().toLowerCase())) {
                return one;
            }
        }
        return null;
    }

}
