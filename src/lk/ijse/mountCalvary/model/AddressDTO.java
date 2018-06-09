package lk.ijse.mountCalvary.model;

public class AddressDTO {
    private int SID;
    private String address;

    public AddressDTO() {
    }

    public AddressDTO(String address) {
        this.address = address;
    }

    public AddressDTO(int SID, String address) {
        this.SID = SID;
        this.address = address;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "SID=" + SID +
                ", address='" + address + '\'' +
                '}';
    }
}
