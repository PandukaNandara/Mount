package lk.ijse.mountCalvary.model;

public class LogInDTO {
    private int logInID;
    private String userName;
    private String password;

    public LogInDTO() {
    }

    public LogInDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LogInDTO(int logInID, String userName, String password) {
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
