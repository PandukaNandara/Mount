package lk.ijse.mountCalvary.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 2:00 AM
 */

public class PhysicalTest {
    private int PTID;
    private int SID;
    private String class_;
    private int TERM_ID;
    private int attendance;
    private int skill;
    private int progressEffort;
    private int attitudes;
    private int performance;
    private int total;
    //For Extra usage
    private boolean newOne;

    public PhysicalTest() {
    }

    public PhysicalTest(boolean newOne, int TERM_ID) {
        this.newOne = newOne;
        this.TERM_ID = TERM_ID;
    }

    public PhysicalTest(int PTID, int SID, int TERM_ID, String class_, int attendance, int skill,
                        int progressEffort, int attitudes, int performance, int total) {
        this(SID, TERM_ID, class_, attendance, skill, progressEffort, attitudes, performance, total);
        this.PTID = PTID;
    }

    public PhysicalTest(int SID, int TERM_ID, String class_, int attendance, int skill, int progressEffort,
                        int attitudes, int performance, int total) {
        this.SID = SID;
        this.class_ = class_;
        this.TERM_ID = TERM_ID;
        this.attendance = attendance;
        this.skill = skill;
        this.progressEffort = progressEffort;
        this.attitudes = attitudes;
        this.performance = performance;
        this.total = total;
    }


    public boolean isNewOne() {
        return newOne;
    }

    public void setNewOne(boolean newOne) {
        this.newOne = newOne;
    }

    public int getPTID() {
        return PTID;
    }

    public void setPTID(int PTID) {
        this.PTID = PTID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public int getTERM_ID() {
        return TERM_ID;
    }

    public void setTERM_ID(int TERM_ID) {
        this.TERM_ID = TERM_ID;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getProgressEffort() {
        return progressEffort;
    }

    public void setProgressEffort(int progressEffort) {
        this.progressEffort = progressEffort;
    }

    public int getAttitudes() {
        return attitudes;
    }

    public void setAttitudes(int attitudes) {
        this.attitudes = attitudes;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PhysicalTest{" +
                "PTID=" + PTID +
                ", SID=" + SID +
                ", TERM_ID=" + TERM_ID +
                ", attendance=" + attendance +
                ", skill=" + skill +
                ", progressEffort=" + progressEffort +
                ", attitudes=" + attitudes +
                ", performance=" + performance +
                ", total=" + total +
                '}';
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }
}
