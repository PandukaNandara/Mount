package lk.ijse.mountCalvary.model;

public class TeacherInChargeListDTO {
    private int TINCID;
    private int CID;
    private int TID;
    private String teacherName;
    private TeacherDTO teacherDTO;
    public TeacherInChargeListDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
        this.teacherName = teacherDTO.getTName();
        this.TID = teacherDTO.getTID();
    }
    public TeacherInChargeListDTO(int TINCID, int CID, TeacherDTO teacherDTO) {
        this.TINCID = TINCID;
        this.CID = CID;
        this.teacherDTO = teacherDTO;
        this.TID = teacherDTO.getTID();
        this.teacherName= teacherDTO.getTName();
    }

    public TeacherInChargeListDTO() {
    }

    public TeacherInChargeListDTO(int CID, int TID) {
        this.CID = CID;
        this.TID = TID;
    }

    public TeacherInChargeListDTO(int tincid, int CID, int TID) {
        TINCID = tincid;
        this.CID = CID;
        this.TID = TID;
    }

    public TeacherInChargeListDTO(int TINCID, int TID, String teacherName) {
        this.TINCID = TINCID;
        this.TID = TID;
        this.teacherName = teacherName;
    }

    public int getTINCID() {
        return TINCID;
    }

    public void setTINCID(int TINCID) {
        this.TINCID = TINCID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    @Override
    public String toString() {
        return "TeacherInChargeList{" +
                "TINCID=" + TINCID +
                ", CID=" + CID +
                ", TID=" + TID +
                '}';
    }

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
