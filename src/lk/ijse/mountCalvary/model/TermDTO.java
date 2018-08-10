package lk.ijse.mountCalvary.model;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 1:58 AM
 */
public class TermDTO {
    private int TERM_ID;
    private String termName;
    private int year;
    private int termNo;

    public TermDTO() {
    }

    public TermDTO(String termName, int termNo) {
        this.termName = termName;
        this.termNo = termNo;
    }

    public TermDTO(String termName, int year, int termNo) {
        this(termName, termNo);
        this.year = year;
    }

    public TermDTO(int TERM_ID, String termName, int year, int termNo) {
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
        return termName;
    }
}
