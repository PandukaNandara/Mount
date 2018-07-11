package lk.ijse.mountCalvary.controller.tool;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/11/2018
 * Time: 11:12 AM
 */
public class ButtonFireForEnterSetter {
    public static void setGlobalEventHandler(Pane root) {
        for (Node node : root.getChildren()) {
            if (node instanceof Pane)
                setGlobalEventHandler((Pane) node);
            if (node instanceof Button) {
                node.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
                    if (ev.getCode() == KeyCode.ENTER) {
                        ((Button) node).fire();
                        ev.consume();
                    }
                });
            }
        }
    }
}
