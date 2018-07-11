package lk.ijse.mountCalvary.controller.tool;

public class GlobalBoolean {

    private static boolean lock;

    public static boolean isLocked() {
        return lock;
    }

    public static void setLock(boolean lock) {
        GlobalBoolean.lock = lock;
    }

//    private static BooleanProperty discardProperty = new SimpleBooleanProperty();
//    public static boolean getDiscardProperty() {
//        return discardProperty.get();
//    }
//
//    public static void setDiscardProperty(boolean value) {
//        discardProperty.set(value);
//    }
//
//    public static BooleanProperty discardProperty() {
//        return discardProperty;
//    }
}
