package lk.ijse.mountCalvary.tool;

import javafx.scene.image.Image;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/8/2018
 * Time: 11:41 PM
 */
public final class ApplicationIcons {

    private final static Image errorIcon = new Image("/lk/ijse/mountCalvary/assets/error.png");
    private final static Image defaultIcon = new Image("/lk/ijse/mountCalvary/assets/defaultIcon.png");
    private final static Image warningIcon = new Image("/lk/ijse/mountCalvary/assets/warning.png");
    private final static Image infoIcon = new Image("/lk/ijse/mountCalvary/assets/info.png");
    private final static Image questionIcon = new Image("/lk/ijse/mountCalvary/assets/question.png");
    private final static Image doneIcon = new Image("/lk/ijse/mountCalvary/assets/done.png");

    public static Image getDefaultIcon() {
        return defaultIcon;
    }

    public static Image getErrorIcon() {
        return errorIcon;
    }

    public static Image getWarningIcon() {
        return warningIcon;
    }

    public static Image getInfoIcon() {
        return infoIcon;
    }

    public static Image getQuestionIcon() {
        return questionIcon;
    }

    public static Image getDoneIcon() {
        return doneIcon;
    }

}
