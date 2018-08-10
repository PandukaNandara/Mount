package lk.ijse.mountCalvary.model;

import javafx.beans.property.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 2:00 AM
 */

public class PhysicalTestDTO {

    private IntegerProperty PTID = new SimpleIntegerProperty();
    private IntegerProperty SID = new SimpleIntegerProperty();
    private IntegerProperty TERM_ID = new SimpleIntegerProperty();
    private StringProperty class_ = new SimpleStringProperty();
    private IntegerProperty attendance = new SimpleIntegerProperty();
    private IntegerProperty skill = new SimpleIntegerProperty();
    private IntegerProperty progressEffort = new SimpleIntegerProperty();
    private IntegerProperty attitudes = new SimpleIntegerProperty();
    private IntegerProperty performance = new SimpleIntegerProperty();
    private IntegerProperty total = new SimpleIntegerProperty();
    private String studentName;
    private BooleanProperty newOne = new SimpleBooleanProperty();

    private TermDTO termDTO;
    private String termName;

    public PhysicalTestDTO() {
    }

    public PhysicalTestDTO(int SID, int TERM_ID, String class_, int attendance,
                           int skill, int progressEffort,
                           int attitudes, int performance, int total) {
        this.SID.setValue(SID);
        this.TERM_ID.setValue(TERM_ID);
        this.class_.setValue(class_);
        this.attendance.setValue(attendance);
        this.skill.setValue(skill);
        this.progressEffort.setValue(progressEffort);
        this.attitudes.setValue(attitudes);
        this.performance.setValue(performance);
        this.total.setValue(total);
    }

    public PhysicalTestDTO(int PTID, int SID, int TERM_ID, String class_,
                           int attendance, int skill,
                           int progressEffort,
                           int attitudes, int performance, int total) {
        this(SID, TERM_ID, class_, attendance, skill, progressEffort, attitudes, performance, total);
        this.PTID.setValue(PTID);
    }

    public PhysicalTestDTO(int PTID, int SID, String studentName, int TERM_ID, String class_,
                           int attendance, int skill,
                           int progressEffort, int attitudes, int performance, int total, boolean newOne) {
        this(PTID, SID, TERM_ID, class_, attendance, skill, progressEffort, attitudes, performance, total);
        setStudentName(studentName);
        this.newOne.setValue(newOne);
    }

    public PhysicalTestDTO(int ptid, int SID, TermDTO termDTO, String class_, int attendance, int skill, int progressEffort, int attitudes, int performance, int total) {
        setPTID(ptid);
        setSID(SID);
        setTermDTO(termDTO);
        setClass_(class_);
        setAttendance(attendance);
        setSkill(skill);
        setProgressEffort(progressEffort);
        setAttitudes(attitudes);
        setPerformance(performance);
        setTotal(total);
    }

    public final int getPTID() {
        return PTID.get();
    }

    public final void setPTID(int PTID) {
        this.PTID.set(PTID);
    }

    public IntegerProperty PTIDProperty() {
        return PTID;
    }

    public final int getSID() {
        return SID.get();
    }

    public final void setSID(int SID) {
        this.SID.set(SID);
    }

    public IntegerProperty SIDProperty() {
        return SID;
    }

    public final int getTERM_ID() {
        return TERM_ID.get();
    }

    public final void setTERM_ID(int TERM_ID) {
        this.TERM_ID.set(TERM_ID);
    }

    public IntegerProperty TERM_IDProperty() {
        return TERM_ID;
    }

    public final int getAttendance() {
        return attendance.get();
    }

    public final void setAttendance(int attendance) {
        this.attendance.set(attendance);
    }

    public IntegerProperty attendanceProperty() {
        return attendance;
    }

    public final int getSkill() {
        return skill.get();
    }

    public final void setSkill(int skill) {
        this.skill.set(skill);
    }

    public IntegerProperty skillProperty() {
        return skill;
    }

    public final int getProgressEffort() {
        return progressEffort.get();
    }

    public final void setProgressEffort(int progressEffort) {
        this.progressEffort.set(progressEffort);
    }

    public IntegerProperty progressEffortProperty() {
        return progressEffort;
    }

    public final int getAttitudes() {
        return attitudes.get();
    }

    public final void setAttitudes(int attitudes) {
        this.attitudes.set(attitudes);
    }

    public IntegerProperty attitudesProperty() {
        return attitudes;
    }

    public final int getPerformance() {
        return performance.get();
    }

    public final void setPerformance(int performance) {
        this.performance.set(performance);
    }

    public IntegerProperty performanceProperty() {
        return performance;
    }

    public final int getTotal() {
        return total.get();
    }

    public final void setTotal(int total) {
        this.total.set(total);
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public final String getClass_() {
        return class_.get();
    }

    public final void setClass_(String class_) {
        this.class_.set(class_);
    }

    public StringProperty class_Property() {
        return class_;
    }

    public final String getStudentName() {
        return studentName;
    }

    public final void setStudentName(String studentName) {
        this.studentName = studentName;

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

    public final boolean isNewOne() {
        return newOne.get();
    }

    public final void setNewOne(boolean newOne) {
        this.newOne.set(newOne);
    }

    public BooleanProperty newOneProperty() {
        return newOne;
    }

    public TermDTO getTermDTO() {
        return termDTO;
    }

    public void setTermDTO(TermDTO termDTO) {
        this.termDTO = termDTO;
        this.TERM_ID.set(termDTO.getTERM_ID());
        this.termName = termDTO.getTermName();
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
