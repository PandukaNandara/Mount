package lk.ijse.mountCalvary.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:12 AM
 */
public class CompContribution {
    private int CCID;
    private int SID;
    private int CID;
    private String contribution;

    public CompContribution() {
    }

    public CompContribution(int SID, int CID, String contribution) {
        this.SID = SID;
        this.CID = CID;
        this.contribution = contribution;
    }

    public CompContribution(int CCID, int SID, int CID, String contribution) {
        this.CCID = CCID;
        this.SID = SID;
        this.CID = CID;
        this.contribution = contribution;
    }

    public int getCCID() {
        return CCID;
    }

    public void setCCID(int CCID) {
        this.CCID = CCID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    @Override
    public String toString() {
        return "CompContribution{" +
                "CCID=" + CCID +
                ", SID=" + SID +
                ", CID=" + CID +
                ", contribution='" + contribution + '\'' +
                '}';
    }
}
