package lk.ijse.mountCalvary.controller.tool;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Progress implements Runnable {
    private static Progress progress;
    private String title = "";
    private String text = "";
    private Pane root;
    private Label label;
    private VBox vBox;
    private Stage stage;
    private JFXProgressBar progressBar;
    private Thread thread;

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
        stage.initStyle(StageStyle.UNDECORATED);
    }

    private Progress(Pane root, String title) {
        this(root);
        this.title = title;
        label.setText(text);
    }

    private Progress(Pane root, String title, String text) {
        this(root);
        this.title = title;
        this.text = text;
        label.setText(text);
    }

    public static void showMessage(Pane root, String title, String text) {
        progress = new Progress(root, title, text);
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
//                for (int i = 0; i < 10; i++) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        Thread.interrupted();
//                        break;
//                    }
//                    System.out.println(i + 1);
//                    updateProgress(i + 1, 10);
//                }
//                return null;
                progress.getStage().show();
                return null;
            }
        };
        Thread thread = new Thread(task);
        progress.setThread(thread);
        thread.setDaemon(true);
        thread.start();
    }

    public static void hide() {
        progress.getStage().close();

    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Stage getStage() {
        return stage;
    }

    private void show() {

        stage.show();

    }

    private void close() {
        root.setDisable(false);
        System.out.println("Close");
    }

    private void setProgressValue(LoadingValue value) {
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

    @Override
    public void run() {
        stage.show();
    }

    public enum LoadingValue {
        EMPTY, QUARTER, HALF, THREE_QUARTER, FULL
    }
}
