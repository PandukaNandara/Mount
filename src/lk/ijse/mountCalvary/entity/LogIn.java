package lk.ijse.mountCalvary.entity;

public class LogIn {
    private int logInID;
    private String userName;
    private String password;

    public LogIn() {
    }

    public LogIn(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LogIn(int logInID, String userName, String password) {
        this.logInID = logInID;
        this.userName = userName;
        this.password = password;
    }

    public int getLogInID() {
        return logInID;
    }

    public void setLogInID(int logInID) {
        this.logInID = logInID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
