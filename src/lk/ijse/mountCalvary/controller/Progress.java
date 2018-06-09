package lk.ijse.mountCalvary.controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Progress {
    private String title = "";
    private String text = "";
    private Pane root;
    private Label label;
    private VBox vBox;
    private Stage stage;
    private JFXProgressBar progressBar;

    public Progress(Pane root) {
        stage = new Stage();
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setAlwaysOnTop(true);
        stage.setTitle(title);
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(5));

        this.root = root;
        root.setDisable(true);

        progressBar = new JFXProgressBar();

        label = new Label("Now loading");

        vBox.getChildren().setAll(label, progressBar);
        stage.setScene(new Scene(vBox, 300, 100));
    }
    public Progress(Pane root, String title) {
        this(root);
        this.title = title;
        label.setText(text);
    }

    public Progress(Pane root, String title, String text) {
        this(root);
        this.title = title;
        this.text = text;
        label.setText(text);
    }

    public void show() {
        stage.show();
        //setProgressValue(LoadingValue.EMPTY);
    }
    public void close() {
        root.setDisable(false);
        stage.close();

    }

    public void setProgressValue(LoadingValue value) {
        switch (value) {
            case EMPTY:
                progressBar.setProgress(0);
            case QUARTER:
                progressBar.setProgress(0.2);
            case HALF:
                progressBar.setProgress(0.5);
            case THREE_QUARTER:
                progressBar.setProgress(0.75);
            case FULL:
                progressBar.setProgress(1);
            default:
                progressBar.setProgress(0);
        }
    }

    private void setProgressValue(double value) {
        progressBar.setProgress(value);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum LoadingValue {
        EMPTY, QUARTER, HALF, THREE_QUARTER, FULL
    }
}
