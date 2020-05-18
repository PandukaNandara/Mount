package lk.ijse.mountCalvary.main;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/14/2018
 * Time: 7:36 PM
 */
public interface B {
    String name = "Nandara";

    default boolean kill1() {
        System.out.println(name);
        return true;
    }

    static void main(String[] args) {

    }
}
