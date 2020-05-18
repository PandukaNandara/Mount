package lk.ijse.mountCalvary.model;

import lk.ijse.mountCalvary.tool.Month;

import java.math.BigDecimal;

public class PaymentDTO {
    private int PAYID;
    private int RID;
    private int AID;
    private int SID;
    private BigDecimal fee = new BigDecimal(0);
    private Month month = new Month(Month.NONE);
    private String month_name;
    private int year;
    private RegistrationDTO registrationDTO;
    private String activityName;
    private String studentName;

    private boolean isNewOne;

    public PaymentDTO(RegistrationDTO registrationDTO, BigDecimal fee, Month month, int year) {
        setRegistrationDTO(registrationDTO);
        this.fee = fee;
        setMonth(month);
        this.year = year;
    }

    public PaymentDTO() {
    }

    public PaymentDTO(String studentName) {
        this.studentName = studentName;
    }

    public PaymentDTO(int RID, BigDecimal fee, Month month, int year) {
        this.RID = RID;
        this.fee = fee;
        setMonth(month);
        this.year = year;
    }

    public PaymentDTO(RegistrationDTO registrationDTO, Month month, int year) {
        this.month = month;
        this.year = year;
        this.registrationDTO = registrationDTO;
    }

    public PaymentDTO(int PAYID, int RID, BigDecimal fee, Month month, int year) {
        this.PAYID = PAYID;
        this.RID = RID;
        this.fee = fee;
        setMonth(month);
        this.year = year;
    }

    public PaymentDTO(int PAYID, int RID, BigDecimal fee, Month month, int year, int AID, String activityName) {
        this.PAYID = PAYID;
        this.RID = RID;
        this.AID = AID;
        this.fee = fee;
        setMonth(month);
        this.year = year;
        this.activityName = activityName;
    }

    public PaymentDTO(int payid, int rid, int sid, String sName, BigDecimal fee, Month month, int year) {
        setPAYID(payid);
        setRID(rid);
        setSID(sid);
        setStudentName(sName);
        setFee(fee);
        setMonth(month);
        setYear(year);
    }

    public PaymentDTO(int payid, int rid, int sid, String sName, BigDecimal fee, Month month, int year, String activityName) {
        this(payid, rid, sid, sName, fee, month, year);
        setActivityName(activityName);
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getPAYID() {
        return PAYID;
    }

    public void setPAYID(int PAYID) {
        this.PAYID = PAYID;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
        month_name = month.toString();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "PAYID=" + PAYID +
                ", RID=" + RID +
                ", fee=" + fee +
                ", month=" + month +
                ", year=" + year +
                '}';
    }

    public RegistrationDTO getRegistrationDTO() {
        return registrationDTO;
    }

    public void setRegistrationDTO(RegistrationDTO registrationDTO) {
        this.registrationDTO = registrationDTO;
        this.RID = registrationDTO.getRID();
        this.activityName = registrationDTO.getActivityName();
        this.studentName = registrationDTO.getStudentName();
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public boolean isNewOne() {
        return isNewOne;
    }

    public void setNewOne(boolean newOne) {
        isNewOne = newOne;
    }
}
