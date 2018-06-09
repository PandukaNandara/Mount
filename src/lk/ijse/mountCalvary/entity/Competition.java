package lk.ijse.mountCalvary.entity;

import java.util.Date;

public class Competition {
    private int CID;
    private String comName;
    private String location;
    private Date date;
    private String desc;

    public Competition() {
    }

    public Competition(String comName, String location, Date date, String desc) {
        this.comName = comName;
        this.location = location;
        this.date = date;
        this.desc = desc;
    }

    public Competition(int CID, String comName, String location, Date date, String desc) {
        this.setCID(CID);
        this.setComName(comName);
        this.setLocation(location);
        this.setDate(date);
        this.setDesc(desc);
    }

    public Competition(int cid, String cName) {
        setCID(cid);
        setComName(cName);
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "CID=" + CID +
                ", comName='" + comName + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", desc='" + desc + '\'' +
                '}';
    }
}
