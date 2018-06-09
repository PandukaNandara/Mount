package lk.ijse.mountCalvary.entity;

import java.math.BigDecimal;

public class Payment {
    private int PAYID;
    private int RID;
    private BigDecimal fee;
    private int month;
    private int year;

    public Payment() {
    }

    public Payment(int RID, BigDecimal fee, int month, int year) {
        this.RID = RID;
        this.fee = fee;
        this.month = month;
        this.year = year;
    }

    public Payment(int PID, int RID, BigDecimal fee, int month, int year) {
        this.PAYID = PID;
        this.RID = RID;
        this.fee = fee;
        this.month = month;
        this.year = year;
    }

    public int getPAYID() {
        return PAYID;
    }

    public void setPAYID(int PID) {
        this.PAYID = PID;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
}
