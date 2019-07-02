package lk.pos.TM;

public class SupplierTM {
    private String suplierid;
    private String fname;
    private String lname;
    private String city;
    private String contact;
    private String address;
    private String company;

    public SupplierTM(String suplierid, String fname, String lname, String city, String contact, String address, String company) {
        this.suplierid = suplierid;
        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.contact = contact;
        this.address = address;
        this.company = company;
    }

    public String getSuplierid() {
        return suplierid;
    }

    public void setSuplierid(String suplierid) {
        this.suplierid = suplierid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
