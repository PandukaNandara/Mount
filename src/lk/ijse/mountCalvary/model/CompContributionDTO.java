package lk.ijse.mountCalvary.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/28/2018
 * Time: 11:12 AM
 */

public class CompContributionDTO {
    private int CCID;
    private int SID;
    private int CID;
    private String contribution;
    private String studentName;
    private String competitionName;
    private boolean newOne;

    public CompContributionDTO() {
    }

    public CompContributionDTO(int SID, int CID, String contribution) {
        this.SID = SID;
        this.CID = CID;
        this.contribution = contribution;
    }

    public CompContributionDTO(int SID, int CID, String contribution, String studentName) {
        this(SID, CID, contribution);
        this.studentName = studentName;
    }

    public CompContributionDTO(int CCID, int SID, int CID, String contribution) {
        this(SID, CID, contribution);
        this.CCID = CCID;
    }

    public CompContributionDTO(int CCID, int SID, int CID, String contribution, String studentName) {
        this(CCID, SID, CID, contribution);
        this.studentName = studentName;
    }

    public CompContributionDTO(int CCID, int SID, String competitionName, int CID, String contribution) {
        this(CCID, SID, CID, contribution);
        this.competitionName = competitionName;
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

    public boolean isNewOne() {
        return newOne;
    }

    public void setNewOne(boolean newOne) {
        this.newOne = newOne;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    @Override
    public String toString() {
        return "CompContributionDTO{" +
                "CCID=" + CCID +
                ", SID=" + SID +
                ", CID=" + CID +
                ", contribution='" + contribution + '\'' +
                ", studentName='" + studentName + '\'' +
                ", competitionName='" + competitionName + '\'' +
                ", newOne=" + newOne +
                '}';
    }
}
