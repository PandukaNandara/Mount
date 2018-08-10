package lk.ijse.mountCalvary.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 1:58 AM
 */
public class Term {
    private int TERM_ID;
    private String termName;
    private int year;
    private int termNo;

    public Term() {
    }

    public Term(String termName, int year, int termNo) {
        this.termName = termName;
        this.year = year;
        this.termNo = termNo;
    }

    public Term(int TERM_ID, String termName, int year, int termNo) {
        this(termName, year, termNo);
        this.TERM_ID = TERM_ID;
    }

    public int getTERM_ID() {
        return TERM_ID;
    }

    public void setTERM_ID(int TERM_ID) {
        this.TERM_ID = TERM_ID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTermNo() {
        return termNo;
    }

    public void setTermNo(int termNo) {
        this.termNo = termNo;
    }

    @Override
    public String toString() {
        return "Term{" +
                "TERM_ID=" + TERM_ID +
                ", termName='" + termName + '\'' +
                ", year=" + year +
                ", termNo=" + termNo +
                '}';
    }
}
