package lk.ijse.mountCalvary.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/11/2018
 * Time: 7:46 PM
 */
public interface EventInterface {
    int FEMALE = 0;
    int MALE = 1;
    int MIXED = 2;

    boolean isMaleEvent();

    boolean isFemaleEvent();

    boolean isMixedEvent();
}
