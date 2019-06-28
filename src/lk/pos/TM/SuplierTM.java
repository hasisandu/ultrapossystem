package lk.pos.TM;

public class SuplierTM {
    private String suplierid;
    private String firstname;
    private String latname;
    private String city;
    private String contact;
    private String address;
    private String company;

    public SuplierTM(String suplierid, String firstname, String latname, String city, String contact, String address, String company) {
        this.suplierid = suplierid;
        this.firstname = firstname;
        this.latname = latname;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLatname() {
        return latname;
    }

    public void setLatname(String latname) {
        this.latname = latname;
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
