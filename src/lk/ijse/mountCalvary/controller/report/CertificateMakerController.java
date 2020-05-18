package lk.ijse.mountCalvary.controller.report;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.mountCalvary.controller.SuperController;
import lk.ijse.mountCalvary.tool.OptionPane;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/12/2018
 * Time: 8:42 PM
 */

public final class CertificateMakerController extends SuperController implements Initializable {


    @FXML
    private VBox acCertificateMaker;
    @FXML
    private JFXButton btLandScape;
    @FXML
    private JFXButton btPortrait;
    @FXML
    private JFXButton btBrowse;
    @FXML
    private Text textStudentID;
    @FXML
    private Text textStudentName;
    @FXML
    private Text textStudentHouse;
    @FXML
    private Text textStudentClass;
    @FXML
    private Text textResult;
    @FXML
    private Text textPerformance;
    @FXML
    private Text textActivityName;
    @FXML
    private Text textEventName;
    @FXML
    private Text textEventGender;
    @FXML
    private Text textAgeGroup;
    @FXML
    private Text textCompetitionName;
    @FXML
    private Text textCompetition_Location;
    @FXML
    private Text textCompetitionDate;
    @FXML
    private Text textYear;

    @FXML
    private AnchorPane acCertificate;
    @FXML
    private JFXButton btCancel;
    @FXML
    private JFXButton btSubmit;

    private ContextMenu contextMenu;

    private MenuItem delete;

    private Map<String, Text> parameterMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btLandScape.setDisable(true);
        parameterMap = new HashMap<>();

        contextMenu = new ContextMenu();
        delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(delete);
    }

    @FXML
    private void acCertificate_onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();

        boolean success = false;
        if (db.hasString()) {

            Text text = new Text(db.getString());

            text.setOnMouseClicked((mouseEvent -> performMouseClick(text)));
            text.setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case RIGHT:
                        text.setX(text.getX() + 1);
                        break;
                    case LEFT:
                        text.setX(text.getX() - 1);
                        break;
                    case UP:
                        text.setY(text.getY() - 1);
                        break;
                    case DOWN:
                        text.setY(text.getY() + 1);
                        break;
                    case DELETE:
                        acCertificate.getChildren().remove(text);
                }
                text.requestFocus();
                keyEvent.consume();
            });
            text.setOnMousePressed(mousePressed -> {
                text.requestFocus();
                if (mousePressed.isSecondaryButtonDown()) {
                    System.out.println("Pass right click");
                    contextMenu.show(acCertificate, mousePressed.getScreenX(), mousePressed.getScreenY());
                    contextMenu.getItems().get(0).setOnAction(deleteEvent -> acCertificate.getChildren().remove(text));
                }
            });

            text.setOnDragDetected(dragEvent -> {
                parameterDragDetected(dragEvent, TransferMode.MOVE);
                performMouseClick(text);
            });

            text.setOnDragDone(this::parameterDragDone);

            Point2D localPoint = acCertificate.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));


            text.setX((int) (localPoint.getX() - text.getBoundsInLocal().getWidth() / 2));
            text.setY((int) (localPoint.getY() - text.getBoundsInLocal().getHeight() / 2));

            performMouseClick(text);
            acCertificate.getChildren().add(text);
            success = true;
        }

        /* let the source know whether the string was successfully

         * transferred and used */

        event.setDropCompleted(success);

        event.consume();
    }

    private void performMouseClick(Text text) {
        text.setStyle("-fx-fill: red; -fx-underline: true");
        for (Node texts : acCertificate.getChildren())
            if (texts instanceof Text && !texts.equals(text))
                texts.setStyle("-fx-fill: black");
        text.requestFocus();
    }

    @FXML
    private void acCertificate_onDragEntered(DragEvent event) {

    }

    @FXML
    private void acCertificate_onDragExited(DragEvent event) {

    }

    @FXML
    private void acCertificate_onDragOver(DragEvent event) {
        if (!(acCertificate.getChildren().get(0) instanceof ImageView)) return;

        if (event.getGestureSource() != acCertificate
                && event.getDragboard().hasString()) {

            /* allow for both copying and moving, whatever user chooses */

            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @FXML
    private void parameter_onDragDetected(MouseEvent event) {
        parameterDragDetected(event, TransferMode.COPY);
    }

    @FXML
    private void parameter_onDragDone(DragEvent event) {
        parameterDragDone(event);
    }

    private void parameterDragDetected(MouseEvent event, TransferMode transferMode) {
        /* allow any transfer mode */
        Text source = ((Text) (event.getSource()));
        Dragboard db = source.startDragAndDrop(transferMode);

        /* put a string on dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        db.setContent(content);
        event.consume();
    }

    private void parameterDragDone(DragEvent event) {
        /* if the data was successfully moved, clear it */
        Text source = ((Text) (event.getSource()));

        if (event.getTransferMode() == TransferMode.MOVE) {
            source.setText("");
        }
        event.consume();
    }

    @FXML
    private void btBrowse_onAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your certificate");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Image file", "*.jpg", "*.png", "*.jpeg"));

        File recordsDir = new File(System.getProperty("user.home"), "Pictures");
        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        fileChooser.setInitialDirectory(recordsDir);

        File openFile = fileChooser.showOpenDialog(acCertificateMaker.getScene().getWindow());
        String absolutePath;
        try {
            absolutePath = openFile.toURI().toString();
        } catch (NullPointerException e) {
            absolutePath = null;
        }

        if (absolutePath == null) return;


        System.out.println(absolutePath);

        ImageView imageView = new ImageView(absolutePath);
        imageView.setFitWidth(842);
        imageView.setFitHeight(595);
        acCertificate.getChildren().setAll(imageView);
    }

    @FXML
    private void btLandScape_onAction(ActionEvent event) {
        btPortrait.setDisable(false);
        btLandScape.setDisable(true);
    }

    @FXML
    private void btPortrait_onAction(ActionEvent event) {
        btPortrait.setDisable(true);
        btLandScape.setDisable(false);
    }

    @FXML
    private void btSubmit_onAction(ActionEvent actionEvent) {
        if (!OptionPane.askQuestion("Do you want to create certificate according to this layout?"))
            return;
        parameterMap = new HashMap<>();
        for (Node node : acCertificate.getChildren()) {
            if (node instanceof Text) {
                parameterMap.put(((Text) node).getText(), (Text) node);
            }
        }
        ((Stage) acCertificateMaker.getScene().getWindow()).close();
    }

    @FXML
    private void btCancel_onAction(ActionEvent actionEvent) {

    }

    public Map<String, Text> getParameterMap() {
        return parameterMap;
    }
}
